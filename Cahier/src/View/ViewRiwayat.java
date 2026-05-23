/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import java.awt.*;
import java.awt.geom.Path2D;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 * Panel Riwayat Penjualan - Embeddable untuk Sidebar Menu
 *
 * @author LENOVO
 */
public class ViewRiwayat extends JPanel {

    // Komponen publik agar dapat dihubungkan ke Controller eksternal
    public JTable tblRiwayat;
    public JTextField tfCariRiwayat;
    public JComboBox<String> cbFilterBulan;
    public JComboBox<String> cbFilterTahun;
    public JButton btnCari;
    public JButton btnDetail;
    public JButton btnExport;
    public JLabel lblTotalPendapatan;

    public ViewRiwayat() {
        setBackground(new Color(249, 250, 251));
        setLayout(new BorderLayout());

        JPanel mainContent = new JPanel(null);
        mainContent.setBackground(new Color(249, 250, 251));
        add(mainContent, BorderLayout.CENTER);

        // --- PANEL HEADER BRANDING (Gradasi Indigo-Blue) ---
        GradientPanel headerPanel = new GradientPanel(
                new Color(79, 70, 229), new Color(59, 130, 246));
        headerPanel.setBounds(0, 0, 950, 70);
        mainContent.add(headerPanel);

        JLabel lblTitle = new JLabel("Riwayat Penjualan");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(20, 15, 300, 25);
        headerPanel.add(lblTitle);

        JLabel lblSubtitle = new JLabel("Lihat dan kelola semua transaksi yang telah selesai");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitle.setForeground(new Color(255, 255, 255, 180));
        lblSubtitle.setBounds(20, 43, 400, 16);
        headerPanel.add(lblSubtitle);

        // --- PANEL FILTER ---
        RoundPanel pnlFilter = new RoundPanel(16);
        pnlFilter.setBackground(Color.WHITE);
        pnlFilter.setBounds(15, 85, 905, 70);
        pnlFilter.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(243, 144, 246), 1),
                BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        mainContent.add(pnlFilter);

        JLabel lblCariLabel = new JLabel("Cari:");
        lblCariLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblCariLabel.setForeground(new Color(107, 114, 128));
        lblCariLabel.setBounds(15, 20, 40, 20);
        pnlFilter.add(lblCariLabel);

        tfCariRiwayat = new RoundTextField(8, "Cari nama barang atau ID transaksi...");
        tfCariRiwayat.setBounds(55, 15, 280, 34);
        pnlFilter.add(tfCariRiwayat);

        JLabel lblBulan = new JLabel("Bulan:");
        lblBulan.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblBulan.setForeground(new Color(107, 114, 128));
        lblBulan.setBounds(355, 20, 45, 20);
        pnlFilter.add(lblBulan);

        cbFilterBulan = new JComboBox<>(new String[]{
            "Semua Bulan", "Januari", "Februari", "Maret", "April", "Mei", "Juni",
            "Juli", "Agustus", "September", "Oktober", "November", "Desember"
        });
        cbFilterBulan.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cbFilterBulan.setBounds(400, 15, 130, 34);
        cbFilterBulan.setBackground(Color.WHITE);
        pnlFilter.add(cbFilterBulan);

        JLabel lblTahun = new JLabel("Tahun:");
        lblTahun.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTahun.setForeground(new Color(107, 114, 128));
        lblTahun.setBounds(545, 20, 45, 20);
        pnlFilter.add(lblTahun);

        cbFilterTahun = new JComboBox<>(new String[]{"2024", "2025", "2026"});
        cbFilterTahun.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cbFilterTahun.setBounds(590, 15, 90, 34);
        cbFilterTahun.setBackground(Color.WHITE);
        pnlFilter.add(cbFilterTahun);

        btnCari = new RoundButton("Cari", 8);
        btnCari.setBounds(695, 15, 90, 34);
        pnlFilter.add(btnCari);

        btnExport = new RoundButton("Export", 8);
        btnExport.setBackground(new Color(240, 253, 244));
        btnExport.setForeground(new Color(22, 163, 74));
        btnExport.setBounds(795, 15, 90, 34);
        pnlFilter.add(btnExport);

        // --- PANEL TABEL RIWAYAT ---
        RoundPanel pnlTabel = new RoundPanel(16);
        pnlTabel.setBackground(Color.WHITE);
        pnlTabel.setBounds(15, 170, 905, 360);
        pnlTabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(243, 144, 246), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        mainContent.add(pnlTabel);

        JLabel lblTableTitle = new JLabel("Daftar Transaksi");
        lblTableTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTableTitle.setForeground(new Color(17, 24, 39));
        lblTableTitle.setBounds(15, 12, 200, 20);
        pnlTabel.add(lblTableTitle);

        String[] colRiwayat = {"ID Transaksi", "Tanggal", "Nama Kasir", "Total Item", "Total Harga"};
        DefaultTableModel modelRiwayat = new DefaultTableModel(colRiwayat, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tblRiwayat = new JTable(modelRiwayat);
        styleTable(tblRiwayat);

        JScrollPane spRiwayat = new JScrollPane(tblRiwayat);
        spRiwayat.setBounds(15, 40, 875, 255);
        spRiwayat.setBackground(Color.WHITE);
        spRiwayat.getViewport().setBackground(Color.WHITE);
        spRiwayat.setBorder(BorderFactory.createLineBorder(new Color(243, 144, 246)));
        pnlTabel.add(spRiwayat);

        btnDetail = new RoundButton("Lihat Detail Transaksi", 8);
        btnDetail.setBounds(15, 305, 220, 36);
        pnlTabel.add(btnDetail);

        // --- PANEL TOTAL PENDAPATAN ---
        RoundPanel pnlTotal = new RoundPanel(16);
        pnlTotal.setBackground(new Color(238, 242, 255));
        pnlTotal.setBounds(15, 545, 905, 60);
        pnlTotal.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        mainContent.add(pnlTotal);

        JLabel lblTotalLabel = new JLabel("Total Pendapatan:");
        lblTotalLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTotalLabel.setForeground(new Color(79, 70, 229));
        lblTotalLabel.setBounds(20, 15, 160, 25);
        pnlTotal.add(lblTotalLabel);

        lblTotalPendapatan = new JLabel("Rp 0", SwingConstants.LEFT);
        lblTotalPendapatan.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTotalPendapatan.setForeground(new Color(79, 70, 229));
        lblTotalPendapatan.setBounds(180, 10, 300, 35);
        pnlTotal.add(lblTotalPendapatan);
    }

    private void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setForeground(new Color(55, 65, 81));
        table.setRowHeight(32);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(243, 144, 246));
        table.setSelectionForeground(new Color(17, 24, 39));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBackground(new Color(249, 250, 251));
        header.setForeground(new Color(107, 114, 128));
        header.setPreferredSize(new Dimension(100, 36));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(229, 231, 235)));

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val, boolean isSel, boolean hasF, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, val, isSel, hasF, row, col);
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                return c;
            }
        });
    }

    // --- Komponen Desain Kustom Internal ---

    class GradientPanel extends JPanel {
        private Color color1;
        private Color color2;

        public GradientPanel(Color c1, Color c2) {
            this.color1 = c1;
            this.color2 = c2;
            setLayout(null);
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

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
        private Color borderColor = new Color(229, 231, 235);
        private Color focusColor = new Color(79, 70, 229);
        private boolean isFocused = false;
        private String placeholder;

        public RoundTextField(int radius, String placeholder) {
            this.radius = radius;
            this.placeholder = placeholder;
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
            setFont(new Font("Segoe UI", Font.PLAIN, 13));
            setForeground(new Color(31, 41, 55));
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
                g2.setColor(new Color(156, 163, 175));
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
        private Color colorNormal = new Color(79, 70, 229);
        private Color colorHover = new Color(67, 56, 202);
        private Color colorPressed = new Color(55, 48, 163);
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
