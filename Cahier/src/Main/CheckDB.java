/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Model.Connector;
import java.sql.*;

/**
 * File untuk mengecek isi database SQLite.
 * Jalankan file ini (Run File) untuk melihat semua data di console.
 */
public class CheckDB {

    public static void main(String[] args) {
        Connection conn = Connector.connect();

        if (conn == null) {
            System.out.println("Gagal koneksi ke database!");
            return;
        }

        try {
            Statement stmt = conn.createStatement();

            // 1. Cek Users
            System.out.println("\n=== DATA USERS ===");
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                System.out.println(rs.getInt("id_user") + " | " + rs.getString("username") + " | " + rs.getString("nama_lengkap"));
            }
            rs.close();

            // 2. Cek Kategori
            System.out.println("\n=== DATA KATEGORI ===");
            rs = stmt.executeQuery("SELECT * FROM kategori");
            while (rs.next()) {
                System.out.println(rs.getInt("id_kategori") + " | " + rs.getString("nama_kategori"));
            }
            rs.close();

            // 3. Cek Barang
            System.out.println("\n=== DATA BARANG ===");
            rs = stmt.executeQuery("SELECT * FROM barang");
            while (rs.next()) {
                System.out.println(rs.getInt("id_barang") + " | " + rs.getString("barcode") + " | " + rs.getString("nama_barang") + " | Harga: Rp " + rs.getDouble("harga_jual") + " | Stok: " + rs.getInt("stok"));
            }
            rs.close();

            // 4. Cek Transaksi
            System.out.println("\n=== DATA TRANSAKSI ===");
            rs = stmt.executeQuery("SELECT * FROM transaksi");
            while (rs.next()) {
                System.out.println(rs.getInt("id_transaksi") + " | " + rs.getString("no_faktur") + " | Tanggal: " + rs.getString("tanggal_transaksi") + " | Total: Rp " + rs.getDouble("total_bayar"));
            }
            rs.close();

            // 5. Cek Detail Transaksi
            System.out.println("\n=== DATA DETAIL TRANSAKSI ===");
            rs = stmt.executeQuery("SELECT * FROM detail_transaksi");
            while (rs.next()) {
                System.out.println("ID Detail: " + rs.getInt("id_detail") + " | ID Transaksi: " + rs.getInt("id_transaksi") + " | ID Barang: " + rs.getInt("id_barang") + " | Qty: " + rs.getInt("qty") + " | Subtotal: Rp " + rs.getDouble("subtotal"));
            }
            rs.close();

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error saat mengambil data: " + e.getMessage());
        }
    }
}
