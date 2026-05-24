/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Model.Connector;
import java.sql.*;

/**
 * File untuk memasukkan data dummy ke database SQLite.
 * Jalankan main() di file ini untuk mengisi database dengan data contoh.
 * 
 * @author LENOVO
 */
public class AddDataDB {

    public static void main(String[] args) {
        Connection conn = Connector.connect();

        if (conn == null) {
            System.out.println("Gagal koneksi ke database!");
            return;
        }

        try {
            // Matikan autocommit agar semua INSERT berjalan dalam 1 transaksi
            conn.setAutoCommit(false);

            // ============================================================
            // 1. Buat tabel jika belum ada
            // ============================================================
            createTables(conn);

            // ============================================================
            // 2. Seed data USERS (1 user: Wortellemez)
            // ============================================================
            seedUsers(conn);

            // ============================================================
            // 3. Seed data KATEGORI (5 kategori)
            // ============================================================
            seedKategori(conn);

            // ============================================================
            // 4. Seed data BARANG (10 barang)
            // ============================================================
            seedBarang(conn);

            // ============================================================
            // 5. Seed data TRANSAKSI (3 transaksi)
            // ============================================================
            seedTransaksi(conn);

            // ============================================================
            // 6. Seed data DETAIL_TRANSAKSI (7 detail)
            // ============================================================
            seedDetailTransaksi(conn);

            // Commit semua perubahan
            conn.commit();
            System.out.println("========================================");
            System.out.println("  Semua data dummy berhasil dimasukkan!");
            System.out.println("========================================");

        } catch (SQLException e) {
            System.out.println("Error saat seed data: " + e.getMessage());
            try {
                conn.rollback();
                System.out.println("Rollback berhasil.");
            } catch (SQLException ex) {
                System.out.println("Gagal rollback: " + ex.getMessage());
            }
        } finally {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Buat tabel jika belum ada
    private static void createTables(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        // Tabel users
        stmt.execute(
            "CREATE TABLE IF NOT EXISTS users (" +
            "  id_user INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  username VARCHAR(50) UNIQUE NOT NULL," +
            "  password VARCHAR(255) NOT NULL," +
            "  nama_lengkap VARCHAR(100)," +
            "  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
            ")"
        );

        // Tabel kategori
        stmt.execute(
            "CREATE TABLE IF NOT EXISTS kategori (" +
            "  id_kategori INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  nama_kategori VARCHAR(50) NOT NULL" +
            ")"
        );

        // Tabel barang
        stmt.execute(
            "CREATE TABLE IF NOT EXISTS barang (" +
            "  id_barang INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  barcode VARCHAR(50)," +
            "  nama_barang VARCHAR(100) NOT NULL," +
            "  id_kategori INTEGER," +
            "  harga_beli DECIMAL(10,2)," +
            "  harga_jual DECIMAL(10,2)," +
            "  stok INTEGER DEFAULT 0," +
            "  FOREIGN KEY (id_kategori) REFERENCES kategori(id_kategori)" +
            ")"
        );

        // Tabel transaksi
        stmt.execute(
            "CREATE TABLE IF NOT EXISTS transaksi (" +
            "  id_transaksi INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  no_faktur VARCHAR(50) UNIQUE NOT NULL," +
            "  tanggal_transaksi TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "  id_user INTEGER," +
            "  total_bayar DECIMAL(10,2)," +
            "  jumlah_uang DECIMAL(10,2)," +
            "  kembalian DECIMAL(10,2)," +
            "  FOREIGN KEY (id_user) REFERENCES users(id_user)" +
            ")"
        );

        // Tabel detail_transaksi
        stmt.execute(
            "CREATE TABLE IF NOT EXISTS detail_transaksi (" +
            "  id_detail INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  id_transaksi INTEGER," +
            "  id_barang INTEGER," +
            "  qty INTEGER," +
            "  harga_satuan DECIMAL(10,2)," +
            "  subtotal DECIMAL(10,2)," +
            "  FOREIGN KEY (id_transaksi) REFERENCES transaksi(id_transaksi)," +
            "  FOREIGN KEY (id_barang) REFERENCES barang(id_barang)" +
            ")"
        );

        stmt.close();
        System.out.println("[OK] Tabel berhasil dibuat / sudah ada.");
    }

    // Seed data users
    private static void seedUsers(Connection conn) throws SQLException {
        String sql = "INSERT OR IGNORE INTO users (username, password, nama_lengkap) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        // User utama: Wortellemez
        ps.setString(1, "Wortellemez");
        ps.setString(2, "NIM123");
        ps.setString(3, "Dimas Rizki Ardiansyah");
        ps.executeUpdate();

        ps.close();
        System.out.println("[OK] Data users berhasil di-seed (1 user).");
    }

    // Seed data kategori
    private static void seedKategori(Connection conn) throws SQLException {
        String sql = "INSERT OR IGNORE INTO kategori (id_kategori, nama_kategori) VALUES (?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        String[][] kategoriData = {
            {"1", "Makanan"},
            {"2", "Minuman"},
            {"3", "Snack"},
            {"4", "Alat Tulis"},
            {"5", "Kebutuhan Rumah"}
        };

        for (String[] k : kategoriData) {
            ps.setInt(1, Integer.parseInt(k[0]));
            ps.setString(2, k[1]);
            ps.executeUpdate();
        }

        ps.close();
        System.out.println("[OK] Data kategori berhasil di-seed (5 kategori).");
    }

    // Seed data barang
    private static void seedBarang(Connection conn) throws SQLException {
        String sql = "INSERT OR IGNORE INTO barang (id_barang, barcode, nama_barang, id_kategori, harga_beli, harga_jual, stok) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        // 10 data barang
        Object[][] barangData = {
            // id, barcode, nama, id_kategori, harga_beli, harga_jual, stok
            {1, "BRG-001", "Nasi Goreng Instan",   1, 8000.00,  12000.00, 50},
            {2, "BRG-002", "Mie Instan Goreng",     1, 2500.00,  4000.00,  100},
            {3, "BRG-003", "Teh Botol Sosro 450ml", 2, 3000.00,  5000.00,  80},
            {4, "BRG-004", "Kopi Kapal Api Sachet", 2, 1000.00,  2000.00,  200},
            {5, "BRG-005", "Chitato 68g",           3, 7000.00,  10000.00, 40},
            {6, "BRG-006", "Oreo Original 137g",    3, 6000.00,  9000.00,  60},
            {7, "BRG-007", "Pulpen Pilot G2",       4, 5000.00,  8000.00,  75},
            {8, "BRG-008", "Buku Tulis Sidu 58 lbr",4, 3000.00,  5000.00,  90},
            {9, "BRG-009", "Sabun Cuci Piring 800ml",5,10000.00, 15000.00, 30},
            {10,"BRG-010", "Tisu Paseo 250 sheet",  5, 8000.00,  12000.00, 45}
        };

        for (Object[] b : barangData) {
            ps.setInt(1, (int) b[0]);
            ps.setString(2, (String) b[1]);
            ps.setString(3, (String) b[2]);
            ps.setInt(4, (int) b[3]);
            ps.setDouble(5, (double) b[4]);
            ps.setDouble(6, (double) b[5]);
            ps.setInt(7, (int) b[6]);
            ps.executeUpdate();
        }

        ps.close();
        System.out.println("[OK] Data barang berhasil di-seed (10 barang).");
    }

    // Seed data transaksi
    private static void seedTransaksi(Connection conn) throws SQLException {
        // Cari id_user dari Wortellemez
        int idUser = 1; // default
        Statement stUser = conn.createStatement();
        ResultSet rsUser = stUser.executeQuery("SELECT id_user FROM users WHERE username = 'Wortellemez'");
        if (rsUser.next()) {
            idUser = rsUser.getInt("id_user");
        }
        rsUser.close();
        stUser.close();

        String sql = "INSERT OR IGNORE INTO transaksi (id_transaksi, no_faktur, tanggal_transaksi, id_user, total_bayar, jumlah_uang, kembalian) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        // 3 transaksi
        Object[][] transaksiData = {
            // id, no_faktur, tanggal, id_user, total_bayar, jumlah_uang, kembalian
            {1, "FKR-20260520-001", "2026-05-20 10:30:00", idUser, 31000.00, 50000.00, 19000.00},
            {2, "FKR-20260521-001", "2026-05-21 14:15:00", idUser, 27000.00, 30000.00, 3000.00},
            {3, "FKR-20260522-001", "2026-05-22 09:45:00", idUser, 40000.00, 50000.00, 10000.00}
        };

        for (Object[] t : transaksiData) {
            ps.setInt(1, (int) t[0]);
            ps.setString(2, (String) t[1]);
            ps.setString(3, (String) t[2]);
            ps.setInt(4, (int) t[3]);
            ps.setDouble(5, (double) t[4]);
            ps.setDouble(6, (double) t[5]);
            ps.setDouble(7, (double) t[6]);
            ps.executeUpdate();
        }

        ps.close();
        System.out.println("[OK] Data transaksi berhasil di-seed (3 transaksi).");
    }

    // Seed data detail_transaksi
    private static void seedDetailTransaksi(Connection conn) throws SQLException {
        String sql = "INSERT OR IGNORE INTO detail_transaksi (id_detail, id_transaksi, id_barang, qty, harga_satuan, subtotal) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        // 7 detail transaksi (tersebar di 3 transaksi)
        Object[][] detailData = {
            // id_detail, id_transaksi, id_barang, qty, harga_satuan, subtotal
            
            // Transaksi 1: Nasi Goreng Instan(1x12000) + Teh Botol(2x5000) + Chitato(1x10000) = Rp 32.000 -> koreksi jadi 31000 di total_bayar bisa diskon dll
            {1, 1, 1, 1, 12000.00, 12000.00},  // Nasi Goreng Instan x1
            {2, 1, 3, 2, 5000.00,  10000.00},   // Teh Botol Sosro x2
            {3, 1, 5, 1, 9000.00,  9000.00},    // Chitato x1 (diskon jadi 9000)

            // Transaksi 2: Mie Instan(3x4000) + Kopi Kapal Api(5x2000) + Buku Tulis(1x5000) = Rp 27.000
            {4, 2, 2, 3, 4000.00,  12000.00},   // Mie Instan Goreng x3
            {5, 2, 4, 5, 2000.00,  10000.00},   // Kopi Kapal Api x5
            {6, 2, 8, 1, 5000.00,  5000.00},    // Buku Tulis Sidu x1

            // Transaksi 3: Sabun Cuci Piring(1x15000) + Tisu Paseo(1x12000) + Pulpen Pilot(1x8000) + Oreo(1x9000) = Rp 44.000 -> 40000 di total (diskon member)
            {7, 3, 9,  1, 15000.00, 15000.00},  // Sabun Cuci Piring x1
            {8, 3, 10, 1, 12000.00, 12000.00},  // Tisu Paseo x1
            {9, 3, 7,  1, 8000.00,  8000.00},   // Pulpen Pilot x1
            {10,3, 6,  1, 5000.00,  5000.00}    // Oreo x1 (promo 5000)
        };

        for (Object[] d : detailData) {
            ps.setInt(1, (int) d[0]);
            ps.setInt(2, (int) d[1]);
            ps.setInt(3, (int) d[2]);
            ps.setInt(4, (int) d[3]);
            ps.setDouble(5, (double) d[4]);
            ps.setDouble(6, (double) d[5]);
            ps.executeUpdate();
        }

        ps.close();
        System.out.println("[OK] Data detail_transaksi berhasil di-seed (10 detail).");
    }
}
