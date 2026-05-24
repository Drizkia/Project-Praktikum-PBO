/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author LENOVO
 */
public class ViewMenuUtama extends JFrame {
    public JButton btnTransaksi;
    public JButton btnRiwayat;
    public JButton btnCekStok;
    public JButton btnLogout;

    private JPanel mainContentPanel;
    private JPanel currentView;

    public ViewMenuUtama() {
        setTitle("CAHIER - Sistem Kasir");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // HEADER
        JPanel headerPanel = new GradientPanel();
        headerPanel.setPreferredSize(new Dimension(0, 60));
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));

        JLabel titleLabel = new JLabel("CAHIER");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        add(headerPanel, BorderLayout.NORTH);

        // MAIN CONTAINER
        JPanel mainContainer = new JPanel(new BorderLayout());

        // SIDEBAR
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(245, 247, 250));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(220, 224, 235)));

        // Sidebar header
        JLabel sidebarTitle = new JLabel("MENU");
        sidebarTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        sidebarTitle.setForeground(new Color(79, 70, 229));
        sidebarTitle.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        sidebarTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(sidebarTitle);

        // Sidebar button
        btnTransaksi = createSidebarButton("Transaksi Baru", new Color(79, 70, 229));
        btnTransaksi.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(btnTransaksi);

        btnRiwayat = createSidebarButton("Riwayat Penjualan", new Color(59, 130, 246));
        btnRiwayat.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(btnRiwayat);

        btnCekStok = createSidebarButton("Cek Stok Barang", new Color(16, 185, 129));
        btnCekStok.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(btnCekStok);

        sidebar.add(Box.createVerticalGlue());

        btnLogout = createSidebarButton("Logout", new Color(239, 68, 68));
        btnLogout.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(btnLogout);

        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));

        mainContainer.add(sidebar, BorderLayout.WEST);

        // MAIN CONTENT
        mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setBackground(new Color(249, 250, 251));

        // welcome view
        currentView = createWelcomePanel();
        mainContentPanel.add(currentView, BorderLayout.CENTER);

        mainContainer.add(mainContentPanel, BorderLayout.CENTER);

        add(mainContainer, BorderLayout.CENTER);

        // FOOTER
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(243, 244, 246));
        footerPanel.setLayout(new BorderLayout());
        footerPanel.setPreferredSize(new Dimension(0, 40));

        JLabel footerLabel = new JLabel("  © 2024 Sistem Kasir Terpercaya");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        footerLabel.setForeground(new Color(107, 114, 128));
        footerPanel.add(footerLabel, BorderLayout.WEST);

        add(footerPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Switch Content
    public void setContentView(JPanel newView) {
        if (currentView != null) {
            mainContentPanel.remove(currentView);
        }
        currentView = newView;
        mainContentPanel.add(currentView, BorderLayout.CENTER);
        mainContentPanel.revalidate();
        mainContentPanel.repaint();
    }

    // Welcome
    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(249, 250, 251));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);

        JLabel welcomeTitle = new JLabel("Selamat Datang di Menu Utama");
        welcomeTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcomeTitle.setForeground(new Color(17, 24, 39));
        panel.add(welcomeTitle, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 50, 0);

        JLabel welcomeDesc = new JLabel("Pilih menu di sidebar untuk memulai");
        welcomeDesc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        welcomeDesc.setForeground(new Color(107, 114, 128));
        panel.add(welcomeDesc, gbc);

        return panel;
    }

    // Side Bar Button
    private JButton createSidebarButton(String text, Color themeColor) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(180, 45));
        btn.setPreferredSize(new Dimension(180, 45));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setForeground(new Color(17, 24, 39));
        btn.setBackground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setMargin(new Insets(10, 15, 10, 15));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new javax.swing.event.MouseInputAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(240, 241, 245));
                btn.setBorder(BorderFactory.createMatteBorder(0, 4, 0, 0, themeColor));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(Color.WHITE);
                btn.setBorder(null);
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(230, 232, 245));
                btn.setBorder(BorderFactory.createMatteBorder(0, 4, 0, 0, themeColor));
            }
        });

        btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        return btn;
    }

    private static class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            Color start = new Color(79, 70, 229);
            Color end = new Color(59, 130, 246);
            GradientPaint gp = new GradientPaint(0, 0, start, w, h, end);
            g2.setPaint(gp);
            g2.fillRect(0, 0, w, h);

            g2.dispose();
        }
    }
}
