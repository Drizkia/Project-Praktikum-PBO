/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Connector;
import View.ViewTransaksi;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Controller for managing new sales transactions, shopping cart, and checking out.
 * 
 * @author Acer
 */
public class ControllerTransaksi {
    private ViewTransaksi view;
    private Connection conn;
    
    // Help structure to store cart details
    private static class CartItem {
        int idBarang;
        String namaBarang;
        double harga;
        int qty;
        double subtotal;

        public CartItem(int idBarang, String namaBarang, double harga, int qty, double subtotal) {
            this.idBarang = idBarang;
            this.namaBarang = namaBarang;
            this.harga = harga;
            this.qty = qty;
            this.subtotal = subtotal;
        }
    }
    
    private List<CartItem> cart = new ArrayList<>();

    public ControllerTransaksi(ViewTransaksi view) {
        this.view = view;
        this.conn = Connector.connect();
        
        loadCategories();
        loadProducts();
        setupListeners();
    }

    private void loadCategories() {
        view.cbKategori.removeAllItems();
        view.cbKategori.addItem("Semua Kategori");
        try {
            String sql = "SELECT nama_kategori FROM kategori ORDER BY nama_kategori ASC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                view.cbKategori.addItem(rs.getString("nama_kategori"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadProducts() {
        DefaultTableModel model = (DefaultTableModel) view.tblBarang.getModel();
        model.setRowCount(0);
        
        String search = view.tfCari.getText().trim();
        String selectedKategori = (String) view.cbKategori.getSelectedItem();
        if (selectedKategori == null) {
            selectedKategori = "Semua Kategori";
        }
        
        try {
            StringBuilder sb = new StringBuilder(
                "SELECT b.id_barang, b.nama_barang, b.harga_jual, b.stok " +
                "FROM barang b " +
                "JOIN kategori k ON b.id_kategori = k.id_kategori " +
                "WHERE 1=1 "
            );
            
            List<Object> params = new ArrayList<>();
            
            if (!selectedKategori.equals("Semua Kategori")) {
                sb.append("AND k.nama_kategori = ? ");
                params.add(selectedKategori);
            }
            
            if (!search.isEmpty()) {
                sb.append("AND (b.nama_barang LIKE ? OR b.id_barang LIKE ?) ");
                params.add("%" + search + "%");
                params.add("%" + search + "%");
            }
            
            PreparedStatement ps = conn.prepareStatement(sb.toString());
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_barang"),
                    rs.getString("nama_barang"),
                    rs.getDouble("harga_jual"),
                    rs.getInt("stok")
                });
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupListeners() {
        // Dropdown Category selection change
        view.cbKategori.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadProducts();
            }
        });

        // Real-time search in products table
        view.tfCari.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                loadProducts();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                loadProducts();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                loadProducts();
            }
        });

        // Add to Cart
        view.btnTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = view.tblBarang.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(view, "Pilih barang dari tabel terlebih dahulu!", "Validasi Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String qtyStr = view.tfQty.getText().trim();
                if (qtyStr.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Jumlah beli tidak boleh kosong!", "Validasi Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    int qty = Integer.parseInt(qtyStr);
                    if (qty <= 0) {
                        JOptionPane.showMessageDialog(view, "Jumlah beli harus lebih besar dari 0!", "Validasi Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    int idBarang = (int) view.tblBarang.getValueAt(selectedRow, 0);
                    String namaBarang = (String) view.tblBarang.getValueAt(selectedRow, 1);
                    double harga = (double) view.tblBarang.getValueAt(selectedRow, 2);
                    int stokAvailable = (int) view.tblBarang.getValueAt(selectedRow, 3);

                    // Check if already in cart
                    CartItem existingItem = null;
                    for (CartItem item : cart) {
                        if (item.idBarang == idBarang) {
                            existingItem = item;
                            break;
                        }
                    }

                    int currentInCart = (existingItem != null) ? existingItem.qty : 0;
                    if (currentInCart + qty > stokAvailable) {
                        JOptionPane.showMessageDialog(view, "Jumlah beli melebihi stok yang tersedia (" + stokAvailable + ")!", "Stok Kurang", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (existingItem != null) {
                        existingItem.qty += qty;
                        existingItem.subtotal = existingItem.qty * existingItem.harga;
                    } else {
                        cart.add(new CartItem(idBarang, namaBarang, harga, qty, harga * qty));
                    }

                    refreshCartTable();
                    view.tfQty.setText("1");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(view, "Jumlah beli harus berupa angka bulat!", "Validasi Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Hapus Item Terpilih
        view.btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = view.tblKeranjang.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(view, "Pilih item keranjang yang ingin dihapus terlebih dahulu!", "Validasi Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                cart.remove(selectedRow);
                refreshCartTable();
            }
        });

        // Proses Pembayaran / Checkout
        view.btnBayar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cart.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Keranjang belanja masih kosong!", "Validasi Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                double total = 0;
                for (CartItem item : cart) {
                    total += item.subtotal;
                }

                String input = JOptionPane.showInputDialog(view, "Total Belanja: " + formatRupiah(total) + "\nMasukkan Jumlah Uang Pembayaran (Rp):", "Proses Pembayaran", JOptionPane.PLAIN_MESSAGE);
                if (input == null) {
                    return; // User cancels dialog
                }

                try {
                    double jumlahUang = Double.parseDouble(input.trim());
                    if (jumlahUang < total) {
                        JOptionPane.showMessageDialog(view, "Uang pembayaran kurang! Dibutuhkan: " + formatRupiah(total), "Pembayaran Gagal", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    double kembalian = jumlahUang - total;

                    // Save to database inside a transaction block
                    conn.setAutoCommit(false);
                    
                    // Generate faktur number: FKR-YYYYMMDD-HHMMSS
                    String noFaktur = "FKR-" + new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());

                    // 1. Save Transaction Header
                    String sqlTx = "INSERT INTO transaksi (no_faktur, tanggal_transaksi, id_user, total_bayar, jumlah_uang, kembalian) VALUES (?, datetime('now', 'localtime'), ?, ?, ?, ?)";
                    PreparedStatement psTx = conn.prepareStatement(sqlTx, Statement.RETURN_GENERATED_KEYS);
                    psTx.setString(1, noFaktur);
                    psTx.setInt(2, Main.MainAPP.loggedInUserId);
                    psTx.setDouble(3, total);
                    psTx.setDouble(4, jumlahUang);
                    psTx.setDouble(5, kembalian);
                    psTx.executeUpdate();

                    ResultSet rsKeys = psTx.getGeneratedKeys();
                    int idTransaksi = -1;
                    if (rsKeys.next()) {
                        idTransaksi = rsKeys.getInt(1);
                    }
                    rsKeys.close();
                    psTx.close();

                    if (idTransaksi == -1) {
                        throw new SQLException("Gagal mendapatkan ID Transaksi.");
                    }

                    // 2. Save Details & Update Stock
                    String sqlDetail = "INSERT INTO detail_transaksi (id_transaksi, id_barang, qty, harga_satuan, subtotal) VALUES (?, ?, ?, ?, ?)";
                    String sqlStock = "UPDATE barang SET stok = stok - ? WHERE id_barang = ?";

                    try (PreparedStatement psDetail = conn.prepareStatement(sqlDetail);
                         PreparedStatement psStock = conn.prepareStatement(sqlStock)) {

                        for (CartItem item : cart) {
                            // Insert Detail
                            psDetail.setInt(1, idTransaksi);
                            psDetail.setInt(2, item.idBarang);
                            psDetail.setInt(3, item.qty);
                            psDetail.setDouble(4, item.harga);
                            psDetail.setDouble(5, item.subtotal);
                            psDetail.addBatch();

                            // Update Stock
                            psStock.setInt(1, item.qty);
                            psStock.setInt(2, item.idBarang);
                            psStock.addBatch();
                        }

                        psDetail.executeBatch();
                        psStock.executeBatch();
                    }

                    conn.commit();
                    conn.setAutoCommit(true);

                    JOptionPane.showMessageDialog(view, "Transaksi Berhasil!\nKembalian: " + formatRupiah(kembalian), "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Show receipt preview
                    cetakStruk(noFaktur, idTransaksi, total, jumlahUang, kembalian);

                    // Clear cart & refresh view
                    cart.clear();
                    refreshCartTable();
                    loadProducts();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(view, "Uang pembayaran harus berupa angka!", "Validasi Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    try {
                        conn.rollback();
                        conn.setAutoCommit(true);
                    } catch (SQLException rollbackEx) {
                        rollbackEx.printStackTrace();
                    }
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(view, "Gagal memproses transaksi di database: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void refreshCartTable() {
        DefaultTableModel model = (DefaultTableModel) view.tblKeranjang.getModel();
        model.setRowCount(0);
        
        double total = 0;
        for (CartItem item : cart) {
            model.addRow(new Object[]{
                item.namaBarang,
                item.harga,
                item.qty,
                item.subtotal
            });
            total += item.subtotal;
        }
        
        view.lblTotalHarga.setText(formatRupiah(total));
    }

    private void cetakStruk(String noFaktur, int idTransaksi, double total, double bayar, double kembalian) {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("                CAHIER                  \n");
        sb.append("        SISTEM KASIR TERPERCAYA         \n");
        sb.append("========================================\n");
        sb.append(String.format("Faktur   : %s\n", noFaktur));
        sb.append(String.format("Tanggal  : %s\n", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
        sb.append(String.format("Kasir    : %s\n", Main.MainAPP.loggedInUserNama));
        sb.append("========================================\n");

        for (CartItem item : cart) {
            sb.append(item.namaBarang).append("\n");
            sb.append(String.format("  %d x %-18s %12s\n", item.qty, formatRupiah(item.harga), formatRupiah(item.subtotal)));
        }

        sb.append("----------------------------------------\n");
        sb.append(String.format("Total    : %30s\n", formatRupiah(total)));
        sb.append(String.format("Bayar    : %30s\n", formatRupiah(bayar)));
        sb.append(String.format("Kembali  : %30s\n", formatRupiah(kembalian)));
        sb.append("========================================\n");
        sb.append("   Terima Kasih atas Kunjungan Anda!   \n");
        sb.append("========================================\n");

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        JOptionPane.showMessageDialog(view, scrollPane, "Struk Pembayaran: " + noFaktur, JOptionPane.PLAIN_MESSAGE);
    }

    private String formatRupiah(double number) {
        return String.format("Rp %,.0f", number).replace(',', '.');
    }
}
