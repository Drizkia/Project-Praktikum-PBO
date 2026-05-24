/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import View.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;

/**
 *
 * @author LENOVO
 */
public class ControllerMenuUtama {
    private ViewMenuUtama view;

    public ControllerMenuUtama(ViewMenuUtama view) {
        this.view = view;
        setupListeners();
    }

    private void setupListeners() {
        // Tombol Transaksi
        view.btnTransaksi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.setContentView(createTransaksiPanel());
            }
        });

        // Tombol Riwayat
        view.btnRiwayat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.setContentView(createRiwayatPanel());
            }
        });

        // Tombol Cek Stok
        view.btnCekStok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.setContentView(createCekStokPanel());
            }
        });

        // Tombol Logout
        view.btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewLogin();
                view.dispose();
            }
        });
    }

    /**
     * Mengembalikan ViewTransaksi untuk ditampilkan di sidebar
     */
    private JPanel createTransaksiPanel() {
        ViewTransaksi viewPanel = new ViewTransaksi();
        new ControllerTransaksi(viewPanel);
        return viewPanel;
    }

    /**
     * Mengembalikan ViewRiwayat untuk ditampilkan di sidebar
     */
    private JPanel createRiwayatPanel() {
        ViewRiwayat viewPanel = new ViewRiwayat();
        new ControllerRiwayat(viewPanel);
        return viewPanel;
    }

    /**
     * Mengembalikan ViewCekStok untuk ditampilkan di sidebar
     */
    private JPanel createCekStokPanel() {
        ViewCekStok viewPanel = new ViewCekStok();
        new ControllerCekStok(viewPanel);
        return viewPanel;
    }
}
