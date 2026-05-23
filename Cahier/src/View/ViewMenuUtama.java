/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import javax.swing.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

import java.awt.*;
import java.awt.geom.Path2D;

/**
 *
 * @author LENOVO
 */
public class ViewMenuUtama extends JFrame {
    // Tombol public agar bisa dihubungkan ke Controller eksternal nantinya
    public JButton btnTransaksi;
    public JButton btnRiwayat;
    public JButton btnCekStok;
    public JButton btnLogout;

    public ViewMenuUtama() {
        // Pengaturan Frame
        setTitle("Menu Utama - Cahier");
        setSize(750, 480);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Tombol Minimize
        JButton btnMinimize = new JButton("—");
        btnMinimize.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnMinimize.setForeground(new Color(224, 231, 255));
        btnMinimize.setContentAreaFilled(false);
        btnMinimize.setBorderPainted(false);
        btnMinimize.setFocusPainted(false);
        btnMinimize.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnMinimize.setBounds(750 - 85, 15, 35, 30);
        btnMinimize.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btnMinimize.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btnMinimize.setForeground(new Color(224, 231, 255));
            }
        });
        btnMinimize.addActionListener(e -> setExtendedState(JFrame.ICONIFIED));
        headerPanel.add(btnMinimize);

        // Tombol Close
        JButton btnClose = new JButton("✕");
        btnClose.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnClose.setForeground(new Color(224, 231, 255));
        btnClose.setContentAreaFilled(false);
        btnClose.setBorderPainted(false);
        btnClose.setFocusPainted(false);
        btnClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClose.setBounds(750 - 45, 15, 35, 30);
        btnClose.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btnClose.setForeground(new Color(254, 226, 226)); // Red-100
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btnClose.setForeground(new Color(224, 231, 255));
            }
        });
        btnClose.addActionListener(e -> System.exit(0));
        headerPanel.add(btnClose);

        // --- GRID PILIHAN MENU (2x2 Cards) ---

        // Card Transaksi
        btnTransaksi = new CardButton("Transaksi", "Mulai kasir & catat penjualan baru", new Color(79, 70, 229));
        btnTransaksi.setBounds(50, 165, 300, 115);
        mainContainer.add(btnTransaksi);

        // Card Riwayat
        btnRiwayat = new CardButton("Riwayat Penjualan", "Lihat rekap & transaksi sebelumnya", new Color(59, 130, 246));
        btnRiwayat.setBounds(400, 165, 300, 115);
        mainContainer.add(btnRiwayat);

        // Card Cek Stok
        btnCekStok = new CardButton("Cek Stok Barang", "Pantau ketersediaan barang di gudang", new Color(16, 185, 129));
        btnCekStok.setBounds(50, 310, 300, 115);
        mainContainer.add(btnCekStok);

        // Card Logout
        btnLogout = new CardButton("Keluar (Logout)", "Keluar dari sesi kasir aktif saat ini", new Color(239, 68, 68));
        btnLogout.setBounds(400, 310, 300, 115);
        mainContainer.add(btnLogout);

        // Action default untuk navigasi
        btnTransaksi.addActionListener(e -> {
            new ViewTransaksi();
            dispose();
        });

        btnLogout.addActionListener(e -> {
            new ViewLogin();
            dispose();
        });

        // Mekanisme Geser Jendela (Window Dragging)
        java.awt.event.MouseAdapter dragListener = new java.awt.event.MouseAdapter() {
            private Point pressedPoint;

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                pressedPoint = e.getPoint();
            }

            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {
                Point curr = e.getLocationOnScreen();
                Component src = (Component) e.getSource();
                Point compOnScreen = src.getLocationOnScreen();
                int frameX = compOnScreen.x - getX();
                int frameY = compOnScreen.y - getY();
                setLocation(curr.x - pressedPoint.x - frameX, curr.y - pressedPoint.y - frameY);
            }
        };

        mainContainer.addMouseListener(dragListener);
        mainContainer.addMouseMotionListener(dragListener);
        headerPanel.addMouseListener(dragListener);
        headerPanel.addMouseMotionListener(dragListener);

        setVisible(true);
    }

    // --- Komponen Desain Kustom Internal ---

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

            // Lengkungkan sudut kiri-atas dan kanan-atas pada panel gradasi
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

            // Gambar latar belakang gradasi
            GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
            g2.setPaint(gp);
            g2.fillRect(0, 0, w, h);

            // Gambar hamparan geometris halus
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

    class CardButton extends JButton {
        private String title;
        private String description;
        private Color themeColor;
        private boolean isHovered = false;
        private boolean isPressed = false;

        public CardButton(String title, String description, Color themeColor) {
            this.title = title;
            this.description = description;
            this.themeColor = themeColor;

            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
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

            int w = getWidth();
            int h = getHeight();

            // Gambar latar belakang kartu
            if (isPressed) {
                g2.setColor(new Color(243, 244, 246)); // Abu-abu terang saat ditekan
            } else if (isHovered) {
                g2.setColor(Color.WHITE);
            } else {
                g2.setColor(new Color(255, 255, 255, 220));
            }
            g2.fillRoundRect(0, 0, w, h, 16, 16);

            // Gambar border kartu
            if (isHovered) {
                g2.setColor(themeColor);
                g2.setStroke(new BasicStroke(2f));
            } else {
                g2.setColor(new Color(229, 231, 235)); // Gray-200
                g2.setStroke(new BasicStroke(1f));
            }
            g2.drawRoundRect(0, 0, w - 1, h - 1, 16, 16);

            // Gambar aksen warna di tepi kiri
            g2.setColor(themeColor);
            g2.fillRoundRect(0, 0, 8, h, 16, 16);
            g2.fillRect(4, 0, 4, h); // Menjaga bagian dalam tetap bersiku di samping aksen

            // Gambar Teks Judul Menu
            g2.setColor(new Color(17, 24, 39)); // Gray-900
            g2.setFont(new Font("Segoe UI", Font.BOLD, 16));
            g2.drawString(title, 28, 42);

            // Gambar Teks Deskripsi Menu
            g2.setColor(new Color(107, 114, 128)); // Gray-500
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));

            // Wrap text sederhana untuk deskripsi jika terlalu panjang
            FontMetrics fm = g2.getFontMetrics();
            int limit = w - 45;
            String text = description;
            if (fm.stringWidth(text) > limit) {
                int splitIndex = text.lastIndexOf(" ", text.length() / 2 + 10);
                if (splitIndex != -1) {
                    g2.drawString(text.substring(0, splitIndex), 28, 70);
                    g2.drawString(text.substring(splitIndex + 1), 28, 88);
                } else {
                    g2.drawString(text, 28, 70);
                }
            } else {
                g2.drawString(text, 28, 70);
            }

            g2.dispose();
        }
    }
}
