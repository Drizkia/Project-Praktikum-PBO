package View;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.RenderingHints;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Component;
import java.awt.geom.Path2D;

/**
 *
 * 
 * @author LENOVO
 */
public class ViewLogin extends JFrame {
    // Pertahankan nama variabel public agar kompatibel dengan ControllerLogin
    public JTextField tfUsername;
    public JPasswordField pfPassword;
    public JButton btnLogin;

    // Komponen Notifikasi Toast Kustom
    private RoundPanel pnlMessage;
    private JLabel lblMessage;
    private javax.swing.Timer messageTimer;

    public enum MessageType {
        SUCCESS, ERROR, WARNING
    }

    public ViewLogin() {
        int xOffset = 320;

        // Pengaturan Frame
        setTitle("Login");
        setSize(750, 480);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0)); // Jendela transparan untuk efek sudut melengkung

        // Kontainer utama dengan sudut melengkung
        RoundPanel mainContainer = new RoundPanel(20);
        mainContainer.setBackground(Color.WHITE);
        mainContainer.setBounds(0, 0, 750, 480);
        add(mainContainer);

        // Panel Banner Pesan (Tersembunyi secara default)
        pnlMessage = new RoundPanel(10);
        pnlMessage.setBounds(xOffset + 50, 15, 330, 42);
        pnlMessage.setBackground(new Color(254, 242, 242));
        pnlMessage.setVisible(false);
        mainContainer.add(pnlMessage);

        lblMessage = new JLabel("", SwingConstants.CENTER);
        lblMessage.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblMessage.setBounds(10, 0, 310, 42);
        pnlMessage.add(lblMessage);

        // Panel Branding Kiri (Gradasi)
        GradientPanel gradientPanel = new GradientPanel();
        gradientPanel.setBounds(0, 0, 320, 480);
        mainContainer.add(gradientPanel);

        // Teks Brand & Logo di dalam Panel Kiri
        JLabel title = new JLabel("CAHIER");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        title.setBounds(40, 230, 240, 40);
        gradientPanel.add(title);

        JLabel slogan = new JLabel("Mudah. Cepat. Terpercaya.");
        slogan.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        slogan.setForeground(new Color(224, 231, 255)); // Warna Indigo-100
        slogan.setBounds(40, 270, 240, 25);
        gradientPanel.add(slogan);

        JLabel desc = new JLabel(
                "<html>Kelola transaksi dan pantau penjualan Anda dengan mudah dalam satu tempat.</html>");
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        desc.setForeground(new Color(199, 210, 254)); // Warna Indigo-200
        desc.setBounds(40, 310, 240, 50);
        gradientPanel.add(desc);

        // Elemen Panel Form Kanan
        xOffset = 320;

        // Tombol Tutup (Close Button)
        JButton btnClose = new JButton("✕");
        btnClose.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnClose.setForeground(new Color(156, 163, 175)); // Warna Abu-abu-400
        btnClose.setContentAreaFilled(false);
        btnClose.setBorderPainted(false);
        btnClose.setFocusPainted(false);
        btnClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClose.setBounds(xOffset + 430 - 45, 15, 30, 30);
        btnClose.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btnClose.setForeground(new Color(239, 68, 68)); // Warna Merah-500
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btnClose.setForeground(new Color(156, 163, 175));
            }
        });
        btnClose.addActionListener(e -> System.exit(0));
        mainContainer.add(btnClose);

        // Judul Selamat Datang
        JLabel lbWelcome = new JLabel("Selamat Datang");
        lbWelcome.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lbWelcome.setForeground(new Color(17, 24, 39)); // Warna Gelap Gray-900
        lbWelcome.setBounds(xOffset + 50, 75, 330, 35);
        mainContainer.add(lbWelcome);

        JLabel lbSubtitle = new JLabel("Silakan masuk ke akun Anda");
        lbSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lbSubtitle.setForeground(new Color(107, 114, 128)); // Warna Abu-abu Gray-500
        lbSubtitle.setBounds(xOffset + 50, 113, 330, 20);
        mainContainer.add(lbSubtitle);

        // Input Username
        JLabel lbUser = new JLabel("Username");
        lbUser.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbUser.setForeground(new Color(55, 65, 81)); // Warna Gray-700
        lbUser.setBounds(xOffset + 50, 165, 330, 20);
        mainContainer.add(lbUser);

        tfUsername = new RoundTextField(10, "Masukkan username");
        tfUsername.setBounds(xOffset + 50, 190, 330, 42);
        mainContainer.add(tfUsername);

        // Input Password
        JLabel lbPass = new JLabel("Password");
        lbPass.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbPass.setForeground(new Color(55, 65, 81)); // Warna Gray-700
        lbPass.setBounds(xOffset + 50, 250, 330, 20);
        mainContainer.add(lbPass);

        pfPassword = new RoundPasswordField(10, "Masukkan password");
        pfPassword.setBounds(xOffset + 50, 275, 330, 42);
        mainContainer.add(pfPassword);

        // Tombol Masuk
        btnLogin = new RoundButton("MASUK", 10);
        btnLogin.setBounds(xOffset + 50, 350, 330, 45);
        mainContainer.add(btnLogin);

        // Catatan Kaki (Footer)
        JLabel lbFooter = new JLabel("© 2026 Cahier Inc. Hak Cipta Dilindungi.");
        lbFooter.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lbFooter.setForeground(new Color(156, 163, 175)); // Warna Gray-400
        lbFooter.setBounds(xOffset + 50, 435, 330, 20);
        lbFooter.setHorizontalAlignment(SwingConstants.CENTER);
        mainContainer.add(lbFooter);

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
        gradientPanel.addMouseListener(dragListener);
        gradientPanel.addMouseMotionListener(dragListener);

        setVisible(true);
    }

    /**
     * Tampilkan banner notifikasi kustom di bagian atas panel form.
     */
    public void showMessage(String text, MessageType type) {
        if (messageTimer != null && messageTimer.isRunning()) {
            messageTimer.stop();
        }

        lblMessage.setText(text);

        if (type == MessageType.SUCCESS) {
            pnlMessage.setBackground(new Color(240, 253, 250)); // Teal-50 lembut
            lblMessage.setForeground(new Color(13, 148, 136)); // Teal-600
        } else if (type == MessageType.ERROR) {
            pnlMessage.setBackground(new Color(254, 242, 242)); // Merah-50 lembut
            lblMessage.setForeground(new Color(220, 38, 38)); // Red-600
        } else {
            pnlMessage.setBackground(new Color(254, 253, 237)); // Kuning-50 lembut
            lblMessage.setForeground(new Color(202, 138, 4)); // Yellow-600
        }

        pnlMessage.setVisible(true);
        pnlMessage.repaint();

        messageTimer = new javax.swing.Timer(3000, e -> {
            pnlMessage.setVisible(false);
        });
        messageTimer.setRepeats(false);
        messageTimer.start();
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

            // Lengkungkan sudut kiri-atas dan kiri-bawah pada panel gradasi
            int r = 20;
            int w = getWidth();
            int h = getHeight();

            Path2D.Double path = new Path2D.Double();
            path.moveTo(w, 0);
            path.lineTo(r, 0);
            path.quadTo(0, 0, 0, r);
            path.lineTo(0, h - r);
            path.quadTo(0, h, r, h);
            path.lineTo(w, h);
            path.closePath();

            g2.setClip(path);

            // Gambar latar belakang gradasi
            GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
            g2.setPaint(gp);
            g2.fillRect(0, 0, w, h);

            // Gambar hamparan geometris halus
            g2.setColor(new Color(255, 255, 255, 30));
            g2.fillOval(-50, -50, 220, 220);
            g2.fillOval(w - 120, h - 120, 200, 200);

            // Gambar lambang mesin kasir yang elegan
            int cx = w / 2;
            int cy = 130;
            g2.setColor(new Color(255, 255, 255, 200));
            g2.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            // Layar / Badan Terminal
            g2.drawRoundRect(cx - 35, cy - 25, 70, 50, 8, 8);
            // Laci mesin kasir
            g2.drawRoundRect(cx - 45, cy + 25, 90, 10, 4, 4);
            // Sambungan dudukan
            g2.drawLine(cx - 10, cy + 25, cx - 15, cy + 30);
            g2.drawLine(cx + 10, cy + 25, cx + 15, cy + 30);
            // Garis tampilan layar
            g2.drawLine(cx - 20, cy - 10, cx + 20, cy - 10);
            g2.drawLine(cx - 15, cy, cx + 5, cy);

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
            setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
            setFont(new Font("Segoe UI", Font.PLAIN, 14));
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
                g2.setStroke(new BasicStroke(2f));
            } else {
                g2.setColor(borderColor);
                g2.setStroke(new BasicStroke(1.5f));
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
                g2.drawString(placeholder, 16, y - 1);
                g2.dispose();
            }
        }
    }

    class RoundPasswordField extends JPasswordField {
        private int radius;
        private Color borderColor = new Color(229, 231, 235); // Gray-200
        private Color focusColor = new Color(79, 70, 229); // Indigo-600
        private boolean isFocused = false;
        private String placeholder;

        public RoundPasswordField(int radius, String placeholder) {
            this.radius = radius;
            this.placeholder = placeholder;
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
            setFont(new Font("Segoe UI", Font.PLAIN, 14));
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
                g2.setStroke(new BasicStroke(2f));
            } else {
                g2.setColor(borderColor);
                g2.setStroke(new BasicStroke(1.5f));
            }
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();

            super.paintComponent(g);
        }

        @Override
        protected void paintChildren(Graphics g) {
            super.paintChildren(g);
            if (getPassword().length == 0 && placeholder != null && !isFocused) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(156, 163, 175)); // Gray-400
                g2.setFont(getFont().deriveFont(Font.PLAIN));
                FontMetrics fm = g2.getFontMetrics();
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(placeholder, 16, y - 1);
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
            setFont(new Font("Segoe UI", Font.BOLD, 14));
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

            if (isPressed) {
                g2.setColor(colorPressed);
            } else if (isHovered) {
                g2.setColor(colorHover);
            } else {
                g2.setColor(colorNormal);
            }

            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();

            super.paintComponent(g);
        }
    }
}
