/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Connector;
import View.ViewCekStok;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Controller for managing product stock and CRUD operations.
 * 
 * @author Acer
 */
public class ControllerCekStok {
    private ViewCekStok view;
    private Connection conn;

    public ControllerCekStok(ViewCekStok view) {
        this.view = view;
        this.conn = Connector.connect();
        
        loadCategories();
        loadTableData();
        setupListeners();
        clearForm();
    }

    private void loadCategories() {
        view.cbKategori.removeAllItems();
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

    private void loadTableData() {
        DefaultTableModel model = (DefaultTableModel) view.tblBarang.getModel();
        model.setRowCount(0);
        
        String search = view.tfCari.getText().trim();
        try {
            String sql = "SELECT b.id_barang, b.nama_barang, k.nama_kategori, b.harga_beli, b.harga_jual, b.stok " +
                         "FROM barang b " +
                         "JOIN kategori k ON b.id_kategori = k.id_kategori ";
            
            PreparedStatement ps;
            if (!search.isEmpty()) {
                sql += "WHERE b.nama_barang LIKE ? OR b.id_barang LIKE ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, "%" + search + "%");
                ps.setString(2, "%" + search + "%");
            } else {
                ps = conn.prepareStatement(sql);
            }
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_barang"),
                    rs.getString("nama_barang"),
                    rs.getString("nama_kategori"),
                    rs.getDouble("harga_beli"),
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
        // Real-time search
        view.tfCari.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                loadTableData();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                loadTableData();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                loadTableData();
            }
        });

        // Table Row Selection
        view.tblBarang.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = view.tblBarang.getSelectedRow();
                if (selectedRow != -1) {
                    view.tfIdBarang.setText(view.tblBarang.getValueAt(selectedRow, 0).toString());
                    view.tfNamaBarang.setText(view.tblBarang.getValueAt(selectedRow, 1).toString());
                    view.cbKategori.setSelectedItem(view.tblBarang.getValueAt(selectedRow, 2).toString());
                    view.tfHargaBeli.setText(view.tblBarang.getValueAt(selectedRow, 3).toString());
                    view.tfHargaJual.setText(view.tblBarang.getValueAt(selectedRow, 4).toString());
                    view.tfStok.setText(view.tblBarang.getValueAt(selectedRow, 5).toString());
                    // ID barang cannot be edited (managed by DB)
                    view.tfIdBarang.setEditable(false);
                }
            }
        });

        // TAMBAH Button
        view.btnTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nama = view.tfNamaBarang.getText().trim();
                String kategori = (String) view.cbKategori.getSelectedItem();
                String hBeliStr = view.tfHargaBeli.getText().trim();
                String hJualStr = view.tfHargaJual.getText().trim();
                String stokStr = view.tfStok.getText().trim();

                if (nama.isEmpty() || kategori == null || hBeliStr.isEmpty() || hJualStr.isEmpty() || stokStr.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Semua kolom form harus diisi!", "Validasi Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    double hargaBeli = Double.parseDouble(hBeliStr);
                    double hargaJual = Double.parseDouble(hJualStr);
                    int stok = Integer.parseInt(stokStr);

                    int idKategori = getKategoriIdByName(kategori);

                    String sql = "INSERT INTO barang (nama_barang, id_kategori, harga_beli, harga_jual, stok) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, nama);
                    ps.setInt(2, idKategori);
                    ps.setDouble(3, hargaBeli);
                    ps.setDouble(4, hargaJual);
                    ps.setInt(5, stok);
                    ps.executeUpdate();
                    ps.close();

                    JOptionPane.showMessageDialog(view, "Barang berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    clearForm();
                    loadTableData();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(view, "Harga dan stok harus berupa angka!", "Validasi Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(view, "Gagal menambahkan barang: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // SIMPAN / EDIT Button
        view.btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = view.tblBarang.getSelectedRow();
                if (selectedRow == -1 && view.tfIdBarang.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Pilih barang yang ingin diubah terlebih dahulu!", "Validasi Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String idStr = view.tfIdBarang.getText().trim();
                String nama = view.tfNamaBarang.getText().trim();
                String kategori = (String) view.cbKategori.getSelectedItem();
                String hBeliStr = view.tfHargaBeli.getText().trim();
                String hJualStr = view.tfHargaJual.getText().trim();
                String stokStr = view.tfStok.getText().trim();

                if (idStr.isEmpty() || nama.isEmpty() || kategori == null || hBeliStr.isEmpty() || hJualStr.isEmpty() || stokStr.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Semua kolom form harus diisi!", "Validasi Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int id = Integer.parseInt(idStr);
                    double hargaBeli = Double.parseDouble(hBeliStr);
                    double hargaJual = Double.parseDouble(hJualStr);
                    int stok = Integer.parseInt(stokStr);

                    int idKategori = getKategoriIdByName(kategori);

                    String sql = "UPDATE barang SET nama_barang = ?, id_kategori = ?, harga_beli = ?, harga_jual = ?, stok = ? WHERE id_barang = ?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, nama);
                    ps.setInt(2, idKategori);
                    ps.setDouble(3, hargaBeli);
                    ps.setDouble(4, hargaJual);
                    ps.setInt(5, stok);
                    ps.setInt(6, id);
                    int rows = ps.executeUpdate();
                    ps.close();

                    if (rows > 0) {
                        JOptionPane.showMessageDialog(view, "Data barang berhasil diperbarui!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                        clearForm();
                        loadTableData();
                    } else {
                        JOptionPane.showMessageDialog(view, "Barang tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(view, "Harga dan stok harus berupa angka!", "Validasi Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(view, "Gagal memperbarui barang: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // HAPUS Button
        view.btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = view.tblBarang.getSelectedRow();
                if (selectedRow == -1 && view.tfIdBarang.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Pilih barang yang ingin dihapus terlebih dahulu!", "Validasi Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String idStr = view.tfIdBarang.getText().trim();
                if (idStr.isEmpty()) return;

                int confirm = JOptionPane.showConfirmDialog(view, "Apakah Anda yakin ingin menghapus barang ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        int id = Integer.parseInt(idStr);
                        String sql = "DELETE FROM barang WHERE id_barang = ?";
                        PreparedStatement ps = conn.prepareStatement(sql);
                        ps.setInt(1, id);
                        ps.executeUpdate();
                        ps.close();

                        JOptionPane.showMessageDialog(view, "Barang berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                        clearForm();
                        loadTableData();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(view, "Gagal menghapus barang: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // RESET Button
        view.btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
    }

    private int getKategoriIdByName(String name) throws SQLException {
        String sql = "SELECT id_kategori FROM kategori WHERE nama_kategori = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        int id = 1;
        if (rs.next()) {
            id = rs.getInt("id_kategori");
        }
        rs.close();
        ps.close();
        return id;
    }

    private void clearForm() {
        view.tfIdBarang.setText("");
        view.tfNamaBarang.setText("");
        if (view.cbKategori.getItemCount() > 0) {
            view.cbKategori.setSelectedIndex(0);
        }
        view.tfHargaBeli.setText("");
        view.tfHargaJual.setText("");
        view.tfStok.setText("");
        view.tfIdBarang.setEditable(false);
        view.tblBarang.clearSelection();
    }
}
