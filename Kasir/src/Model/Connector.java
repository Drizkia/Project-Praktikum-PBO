/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.*;

/**
 *
 * @author LENOVO
 */
public class Connector {
    // private static String jdbc_driver = "com.mysql.cj.jdbc.Driver";
    // private static String nama_db = "-";
    // private static String url_db = "jdbc:mysql://localhost:3306/" + nama_db;
    // private static String username_db = "root";
    // private static String password_db = "";

    private static final String jdbc_driver = "org.sqlite.JDBC";
    private static final String nama_db = "cashier_ppbo.sqlite";
    private static final String url_db = "jdbc:sqlite:" + nama_db;

    private static Connection conn = null;

    /**
     * Mengembalikan satu koneksi bersama (singleton) ke database SQLite.
     * Menggunakan WAL mode agar operasi baca dan tulis tidak saling mengunci.
     */
    public static Connection connect() {
        try {
            if (conn == null || conn.isClosed()) {
                Class.forName(jdbc_driver);
                conn = DriverManager.getConnection(url_db);

                // Aktifkan WAL mode agar baca/tulis bisa berjalan bersamaan
                try (Statement pragma = conn.createStatement()) {
                    pragma.execute("PRAGMA journal_mode=WAL");
                    // Tunggu hingga 5 detik sebelum menyerah jika database sibuk
                    pragma.execute("PRAGMA busy_timeout=5000");
                }

                System.out.println("SQLite Connected (WAL mode aktif)");
            }
        } catch (ClassNotFoundException | SQLException exception) {
            System.out.println("Connection Failed: " + exception.getLocalizedMessage());
        }
        return conn;
    }
}