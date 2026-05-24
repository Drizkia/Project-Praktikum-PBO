/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Connector;
import View.ViewRiwayat;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Controller for tracking transaction history, item details, and printing receipts.
 * 
 * @author Acer
 */
public class ControllerRiwayat {
    private ViewRiwayat view;
    private Connection conn;

    public ControllerRiwayat(ViewRiwayat view) {
        this.view = view;
        this.conn = Connector.connect();
        
        loadTransactions();
        setupListeners();
    }

    private void loadTransactions() {
        DefaultTableModel model = (DefaultTableModel) view.tblTransaksi.getModel();
        model.setRowCount(0);
        
        String search = view.tfCari.getText().trim();
        try {
            String sql = "SELECT t.id_transaksi, t.no_faktur, t.tanggal_transaksi, u.nama_lengkap AS nama_kasir, t.total_bayar, t.jumlah_uang, t.kembalian " +
                         "FROM transaksi t " +
                         "LEFT JOIN users u ON t.id_user = u.id_user ";
            
            PreparedStatement ps;
            if (!search.isEmpty()) {
                sql += "WHERE t.no_faktur LIKE ? OR t.tanggal_transaksi LIKE ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, "%" + search + "%");
                ps.setString(2, "%" + search + "%");
            } else {
                ps = conn.prepareStatement(sql);
            }
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("no_faktur"),
                    rs.getString("tanggal_transaksi"),
                    rs.getString("nama_kasir") != null ? rs.getString("nama_kasir") : "Kasir Utama",
                    rs.getDouble("total_bayar"),
                    rs.getDouble("jumlah_uang"),
                    rs.getDouble("kembalian")
                });
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupListeners() {
        // Real-time search
        view.tfCari.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                loadTransactions();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                loadTransactions();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                loadTransactions();
            }
        });

        // Table selection listener
        view.tblTransaksi.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = view.tblTransaksi.getSelectedRow();
                if (selectedRow != -1) {
                    String noFaktur = view.tblTransaksi.getValueAt(selectedRow, 0).toString();
                    loadTransactionDetail(noFaktur);
                } else {
                    // Clear detail table
                    DefaultTableModel modelDetail = (DefaultTableModel) view.tblDetail.getModel();
                    modelDetail.setRowCount(0);
                    view.lblTotalDetail.setText("Rp 0");
                }
            }
        });

        // Cetak Struk Button
        view.btnCetakStruk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = view.tblTransaksi.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(view, "Pilih transaksi terlebih dahulu untuk mencetak struk!", "Validasi Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                String noFaktur = view.tblTransaksi.getValueAt(selectedRow, 0).toString();
                cetakStruk(noFaktur);
            }
        });
    }

    private void loadTransactionDetail(String noFaktur) {
        DefaultTableModel modelDetail = (DefaultTableModel) view.tblDetail.getModel();
        modelDetail.setRowCount(0);
        
        try {
            int idTransaksi = getTransaksiIdByFaktur(noFaktur);
            if (idTransaksi == -1) return;

            String sql = "SELECT id_transaksi, id_barang, qty, harga_satuan, subtotal FROM detail_transaksi WHERE id_transaksi = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idTransaksi);
            
            ResultSet rs = ps.executeQuery();
            double totalBelanja = 0;
            while (rs.next()) {
                double subtotal = rs.getDouble("subtotal");
                totalBelanja += subtotal;
                modelDetail.addRow(new Object[]{
                    rs.getInt("id_transaksi"),
                    rs.getInt("id_barang"),
                    rs.getInt("qty"),
                    rs.getDouble("harga_satuan"),
                    subtotal
                });
            }
            rs.close();
            ps.close();
            
            view.lblTotalDetail.setText(formatRupiah(totalBelanja));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cetakStruk(String noFaktur) {
        try {
            int idTransaksi = getTransaksiIdByFaktur(noFaktur);
            if (idTransaksi == -1) return;

            // Fetch transaction header info
            String sqlHeader = "SELECT t.no_faktur, t.tanggal_transaksi, u.nama_lengkap AS nama_kasir, t.total_bayar, t.jumlah_uang, t.kembalian " +
                               "FROM transaksi t " +
                               "LEFT JOIN users u ON t.id_user = u.id_user " +
                               "WHERE t.id_transaksi = ?";
            PreparedStatement psHeader = conn.prepareStatement(sqlHeader);
            psHeader.setInt(1, idTransaksi);
            ResultSet rsHeader = psHeader.executeQuery();
            
            if (!rsHeader.next()) {
                rsHeader.close();
                psHeader.close();
                return;
            }

            String tanggal = rsHeader.getString("tanggal_transaksi");
            String kasir = rsHeader.getString("nama_kasir") != null ? rsHeader.getString("nama_kasir") : "Kasir Utama";
            double total = rsHeader.getDouble("total_bayar");
            double bayar = rsHeader.getDouble("jumlah_uang");
            double kembalian = rsHeader.getDouble("kembalian");
            
            rsHeader.close();
            psHeader.close();

            // Fetch transaction items
            String sqlItems = "SELECT b.nama_barang, dt.qty, dt.harga_satuan, dt.subtotal " +
                              "FROM detail_transaksi dt " +
                              "JOIN barang b ON dt.id_barang = b.id_barang " +
                              "WHERE dt.id_transaksi = ?";
            PreparedStatement psItems = conn.prepareStatement(sqlItems);
            psItems.setInt(1, idTransaksi);
            ResultSet rsItems = psItems.executeQuery();

            StringBuilder sb = new StringBuilder();
            sb.append("========================================\n");
            sb.append("                CAHIER                  \n");
            sb.append("        SISTEM KASIR TERPERCAYA         \n");
            sb.append("========================================\n");
            sb.append(String.format("Faktur   : %s\n", noFaktur));
            sb.append(String.format("Tanggal  : %s\n", tanggal));
            sb.append(String.format("Kasir    : %s\n", kasir));
            sb.append("========================================\n");

            while (rsItems.next()) {
                String nama = rsItems.getString("nama_barang");
                int qty = rsItems.getInt("qty");
                double harga = rsItems.getDouble("harga_satuan");
                double sub = rsItems.getDouble("subtotal");

                sb.append(nama).append("\n");
                sb.append(String.format("  %d x %-18s %12s\n", qty, formatRupiah(harga), formatRupiah(sub)));
            }
            rsItems.close();
            psItems.close();

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
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Gagal mencetak struk: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int getTransaksiIdByFaktur(String noFaktur) throws SQLException {
        String sql = "SELECT id_transaksi FROM transaksi WHERE no_faktur = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, noFaktur);
        ResultSet rs = ps.executeQuery();
        int id = -1;
        if (rs.next()) {
            id = rs.getInt("id_transaksi");
        }
        rs.close();
        ps.close();
        return id;
    }

    private String formatRupiah(double number) {
        return String.format("Rp %,.0f", number).replace(',', '.');
    }
}
