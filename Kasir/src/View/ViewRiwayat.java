/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.*;
import java.awt.geom.Path2D;

/**
 *
 * @author LENOVO
 */
public class ViewRiwayat extends JPanel {
    // Komponen publik agar dapat dihubungkan ke database oleh Controller eksternal
    public JTextField tfCari;
    public JTable tblTransaksi;
    public JTable tblDetail;
    public JButton btnCetakStruk;
    public JLabel lblTotalDetail;

    public ViewRiwayat() {
        setBackground(new Color(249, 250, 251));
        setLayout(new BorderLayout());

        // Panel wrapper untuk konten
        JPanel mainContent = new JPanel(null);
        mainContent.setBackground(new Color(249, 250, 251));
        mainContent.setPreferredSize(new Dimension(950, 560));
        add(mainContent, BorderLayout.CENTER);

        // PANEL HEADER BRANDING
        GradientPanel headerPanel = new GradientPanel();
        headerPanel.setBounds(0, 0, 950, 70);
        mainContent.add(headerPanel);

        JLabel lblTitle = new JLabel("Riwayat Penjualan");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(20, 10, 300, 25);
        headerPanel.add(lblTitle);

        JLabel lblDesc = new JLabel("Lihat rekapitulasi transaksi penjualan dan detail item yang terjual");
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDesc.setForeground(new Color(224, 231, 255)); // Indigo-100
        lblDesc.setBounds(20, 38, 550, 20);
        headerPanel.add(lblDesc);

        // DAFTAR TRANSAKSI (kiri)
        RoundPanel pnlDaftarTransaksi = new RoundPanel(16);
        pnlDaftarTransaksi.setBackground(Color.WHITE);
        pnlDaftarTransaksi.setBounds(15, 85, 500, 450);
        pnlDaftarTransaksi.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(243, 244, 246), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        mainContent.add(pnlDaftarTransaksi);

        JLabel lblCari = new JLabel("Cari ID Transaksi / Tanggal");
        lblCari.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblCari.setForeground(new Color(107, 114, 128)); // Gray-500
        lblCari.setBounds(20, 20, 250, 15);
        pnlDaftarTransaksi.add(lblCari);

        // Input Cari Transaksi
        tfCari = new RoundTextField(8, "Ketik ID atau tanggal (YYYY-MM-DD)...");
        tfCari.setBounds(20, 42, 460, 36);
        pnlDaftarTransaksi.add(tfCari);

        // Tabel Transaksi 
        String[] colTransaksi = {"No Faktur", "Tanggal Transaksi", "Nama Kasir", "Total Bayar", "Jumlah Uang", "Kembalian"};
        DefaultTableModel modelTransaksi = new DefaultTableModel(colTransaksi, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tblTransaksi = new JTable(modelTransaksi);
        styleTable(tblTransaksi);

        JScrollPane spTransaksi = new JScrollPane(tblTransaksi);
        spTransaksi.setBounds(20, 95, 460, 335);
        spTransaksi.setBackground(Color.WHITE);
        spTransaksi.getViewport().setBackground(Color.WHITE);
        spTransaksi.setBorder(BorderFactory.createLineBorder(new Color(243, 244, 246)));
        pnlDaftarTransaksi.add(spTransaksi);

        // DETAIL TRANSAKSI (Kanan)
        RoundPanel pnlDetail = new RoundPanel(16);
        pnlDetail.setBackground(Color.WHITE);
        pnlDetail.setBounds(535, 85, 370, 450);
        pnlDetail.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(243, 244, 246), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        mainContent.add(pnlDetail);

        JLabel lblDetailTitle = new JLabel("Detail Transaksi");
        lblDetailTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblDetailTitle.setForeground(new Color(17, 24, 39)); // Gray-900
        lblDetailTitle.setBounds(20, 15, 200, 25);
        pnlDetail.add(lblDetailTitle);

        // Tabel Detail
        String[] colDetail = {"ID Transaksi", "ID Barang", "QTY", "Harga /pcs", "Sub Total"};
        DefaultTableModel modelDetail = new DefaultTableModel(colDetail, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tblDetail = new JTable(modelDetail);
        styleTable(tblDetail);

        JScrollPane spDetail = new JScrollPane(tblDetail);
        spDetail.setBounds(20, 50, 330, 260);
        spDetail.setBackground(Color.WHITE);
        spDetail.getViewport().setBackground(Color.WHITE);
        spDetail.setBorder(BorderFactory.createLineBorder(new Color(243, 244, 246)));
        pnlDetail.add(spDetail);

        // Informasi total harga detail transaksi
        JLabel lblTotalTitle = new JLabel("TOTAL BELANJA");
        lblTotalTitle.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblTotalTitle.setForeground(new Color(156, 163, 175)); // Gray-400
        lblTotalTitle.setBounds(20, 335, 150, 15);
        pnlDetail.add(lblTotalTitle);

        lblTotalDetail = new JLabel("Rp 0", SwingConstants.RIGHT);
        lblTotalDetail.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTotalDetail.setForeground(new Color(79, 70, 229)); // Indigo-600
        lblTotalDetail.setBounds(150, 325, 200, 30);
        pnlDetail.add(lblTotalDetail);

        // Tombol Cetak Struk Ulang
        btnCetakStruk = new RoundButton("CETAK ULANG STRUK", 10);
        btnCetakStruk.setBounds(20, 385, 330, 45);
        pnlDetail.add(btnCetakStruk);
    }

    private void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setForeground(new Color(55, 65, 81)); // Gray-700
        table.setRowHeight(32);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(243, 244, 246)); // Gray-100
        table.setSelectionForeground(new Color(17, 24, 39)); // Gray-900

        // Styling Header Tabel
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBackground(new Color(249, 250, 251)); // Gray-50
        header.setForeground(new Color(107, 114, 128)); // Gray-500
        header.setPreferredSize(new Dimension(100, 36));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(229, 231, 235)));

        // Penyelarasan isi sel
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val, boolean isSel, boolean hasF, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, val, isSel, hasF, row, col);
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                return c;
            }
        });
    }

    class GradientPanel extends JPanel {
        private Color color1 = new Color(79, 70, 229); // Indigo-600
        private Color color2 = new Color(59, 130, 246); // Blue-500

        public GradientPanel() {
            setLayout(null);
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Lengkungkan sudut
            int r = 20; 
            int w = getWidth();
            int h = getHeight();
            
            Path2D.Double path = new Path2D.Double();
            path.moveTo(0, h); 
            path.lineTo(0, r); 
            path.quadTo(0, 0, r, 0); 
            path.lineTo(w - r, 0); 
            path.quadTo(w, 0, w, r); 
            path.lineTo(w, h); 
            path.closePath();
            
            g2.setClip(path);

            // Latar Belakang
            GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
            g2.setPaint(gp);
            g2.fillRect(0, 0, w, h);
            
            g2.setColor(new Color(255, 255, 255, 20));
            g2.fillOval(w - 180, -80, 260, 260);
            g2.fillOval(-100, -100, 220, 220);
            
            g2.dispose();
        }
    }

    class RoundPanel extends JPanel {
        private int radius;

        public RoundPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
            setLayout(null);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
        }
    }

    class RoundTextField extends JTextField {
        private int radius;
        private Color borderColor = new Color(229, 231, 235); // Gray-200
        private Color focusColor = new Color(79, 70, 229); // Indigo-600
        private boolean isFocused = false;
        private String placeholder;

        public RoundTextField(int radius, String placeholder) {
            this.radius = radius;
            this.placeholder = placeholder;
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
            setFont(new Font("Segoe UI", Font.PLAIN, 13));
            setForeground(new Color(31, 41, 55)); // Gray-800
            setCaretColor(focusColor);

            addFocusListener(new java.awt.event.FocusListener() {
                @Override
                public void focusGained(java.awt.event.FocusEvent e) {
                    isFocused = true;
                    repaint();
                }
                @Override
                public void focusLost(java.awt.event.FocusEvent e) {
                    isFocused = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            
            if (isFocused) {
                g2.setColor(focusColor);
                g2.setStroke(new BasicStroke(1.8f));
            } else {
                g2.setColor(borderColor);
                g2.setStroke(new BasicStroke(1.2f));
            }
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
            
            super.paintComponent(g);
        }

        @Override
        protected void paintChildren(Graphics g) {
            super.paintChildren(g);
            if (getText().isEmpty() && placeholder != null && !isFocused) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(156, 163, 175)); // Gray-400
                g2.setFont(getFont().deriveFont(Font.PLAIN));
                FontMetrics fm = g2.getFontMetrics();
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(placeholder, 12, y - 1);
                g2.dispose();
            }
        }
    }

    class RoundButton extends JButton {
        private int radius;
        private Color colorNormal = new Color(79, 70, 229); // Indigo-600
        private Color colorHover = new Color(67, 56, 202); // Indigo-700
        private Color colorPressed = new Color(55, 48, 163); // Indigo-800
        private boolean isHovered = false;
        private boolean isPressed = false;

        public RoundButton(String text, int radius) {
            super(text);
            this.radius = radius;
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.BOLD, 13));
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    isHovered = true;
                    repaint();
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    isHovered = false;
                    repaint();
                }

                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {
                    isPressed = true;
                    repaint();
                }

                @Override
                public void mouseReleased(java.awt.event.MouseEvent e) {
                    isPressed = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            Color bg = getBackground();
            if (bg != null && !bg.equals(new JButton().getBackground())) {
                if (isPressed) {
                    g2.setColor(bg.darker());
                } else if (isHovered) {
                    g2.setColor(bg.brighter());
                } else {
                    g2.setColor(bg);
                }
            } else {
                if (isPressed) {
                    g2.setColor(colorPressed);
                } else if (isHovered) {
                    g2.setColor(colorHover);
                } else {
                    g2.setColor(colorNormal);
                }
            }
            
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
            
            super.paintComponent(g);
        }
    }
}
