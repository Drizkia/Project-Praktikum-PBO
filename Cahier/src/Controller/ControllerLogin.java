/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Connector;
import View.ViewLogin;
import View.ViewMenuUtama;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

/**
 *
 * @author LENOVO
 */
public class ControllerLogin {
    Connection conn;
    
    public ControllerLogin(ViewLogin view) {
        conn = Connector.connect();
        
        view.btnLogin.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = view.tfUsername.getText();
                String password = view.pfPassword.getText();
                
                // Cek kosong atau isi
                if(username.isEmpty() || password.isEmpty()) {
                    view.showMessage("Username/Password gaboleh kosong", ViewLogin.MessageType.ERROR);
                    
                    return;
                } 
                
                try {
                    String query = "SELECT * FROM users WHERE username=? AND password=?";
                    PreparedStatement ps = conn.prepareStatement(query);
                    
                    ps.setString(1, username);
                    ps.setString(2, password);
                    
                    ResultSet rs = ps.executeQuery();
                    
                    if(rs.next()) {
                        view.showMessage("Login Berhasil", ViewLogin.MessageType.SUCCESS);
                        
                        // buat menu add disini @dimas
                        
                        // Tunda 1 detik sebelum dispose agar user sempat melihat banner sukses
                        javax.swing.Timer delayTimer = new javax.swing.Timer(1000, evt -> {
                            view.dispose();
                            new ViewMenuUtama(); // Buka Menu Utama kustom setelah login sukses
                        });
                        delayTimer.setRepeats(false);
                        delayTimer.start();
                    } else {
                        view.showMessage("Username/Password Salah", ViewLogin.MessageType.ERROR);
                    }
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
