/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Data;

/**
 *
 * @author LENOVO
 */
public class User {
    // Encapsulation
    private String username;
    private String password;
    private String nama_lengkap;

    // Constructor kosong (Default Constructor)
    public User() {
    }

    public User(String username, String password, String nama_lengkap) {
        this.username = username;
        this.password = password;
        this.nama_lengkap = nama_lengkap;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }
}

