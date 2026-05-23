package Controller;

import View.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;

/**
 * Controller untuk Menu Utama - Handle panel switching
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
     * Mengembalikan ViewTransaksi sebagai JPanel untuk ditampilkan di sidebar
     */
    private JPanel createTransaksiPanel() {
        return new ViewTransaksi();
    }

    /**
     * Placeholder untuk Riwayat Panel
     */
    private JPanel createRiwayatPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(249, 250, 251));

        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        headerPanel.setBackground(new Color(240, 241, 245));
        JLabel title = new JLabel("Riwayat Penjualan");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(17, 24, 39));
        headerPanel.add(title);
        panel.add(headerPanel, BorderLayout.NORTH);

        // Content placeholder
        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(new Color(249, 250, 251));
        JLabel placeholder = new JLabel("Halaman Riwayat Penjualan");
        placeholder.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        placeholder.setForeground(new Color(107, 114, 128));
        content.add(placeholder);
        panel.add(content, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Placeholder untuk Cek Stok Panel
     */
    private JPanel createCekStokPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(249, 250, 251));

        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        headerPanel.setBackground(new Color(240, 241, 245));
        JLabel title = new JLabel("Cek Stok Barang");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(17, 24, 39));
        headerPanel.add(title);
        panel.add(headerPanel, BorderLayout.NORTH);

        // Content placeholder
        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(new Color(249, 250, 251));
        JLabel placeholder = new JLabel("Halaman Cek Stok Barang");
        placeholder.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        placeholder.setForeground(new Color(107, 114, 128));
        content.add(placeholder);
        panel.add(content, BorderLayout.CENTER);

        return panel;
    }
}
