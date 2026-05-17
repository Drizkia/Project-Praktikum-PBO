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

    private static String jdbc_driver = "org.sqlite.JDBC";
    private static String nama_db = "cashier_ppbo.sqlite";
    private static String url_db = "jdbc:sqlite:" + nama_db;

    static Connection conn;

    // Mencoba menghubungkan program kita dengan database SQLite.
    public static Connection connect() {
        try {
            Class.forName(jdbc_driver);

            conn = DriverManager.getConnection(url_db);

            
            System.out.println("SQLite Connected");
                    // try (Statement stmt = conn.createStatement()) {
                    //     stmt.execute("INSERT OR IGNORE INTO users (username, password, nama_lengkap) "
                    //                + "VALUES ('Wortellemez', 'NIM123', 'Dimas Rizki Ardiansyah')");
                    //     System.out.println("User Baru Berhasil Ditambahkan!");
                    // } catch (Exception e) {
                    //     System.out.println("Gagal menambahkan user: " + e.getMessage());
                    // }
        } catch (ClassNotFoundException | SQLException exception) {
            System.out.println("Connection Failed: " + exception.getLocalizedMessage());
        }
        return conn;
    }
}