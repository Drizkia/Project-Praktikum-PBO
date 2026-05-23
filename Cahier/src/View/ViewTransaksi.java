package View;

import java.awt.*;
import java.awt.geom.Path2D;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 * Panel Transaksi Penjualan - Embeddable untuk Sidebar Menu
 * @author LENOVO
 */
public class ViewTransaksi extends JPanel {
    // Komponen publik agar dapat dihubungkan ke database oleh Controller eksternal
    public JComboBox<String> cbKategori;
    public JTextField tfCari;
    public JTable tblBarang;
    public JTable tblKeranjang;
    public JTextField tfQty;
    public JButton btnTambah;
    public JButton btnHapus;
    public JButton btnBayar;
    public JLabel lblTotalHarga;

    public ViewTransaksi() {
        setBackground(new Color(249, 250, 251));
        setLayout(new BorderLayout());

        // Panel wrapper untuk konten (menggantikan mainContainer yang sebelumnya)
        JPanel mainContent = new JPanel(null);
        mainContent.setBackground(new Color(249, 250, 251));
        add(mainContent, BorderLayout.CENTER);

        // --- PANEL HEADER BRANDING (Gradasi Indigo-Blue) ---
        GradientPanel headerPanel = new GradientPanel();
        headerPanel.setBounds(0, 0, 950, 70);
        mainContent.add(headerPanel);

        JLabel lblTitle = new JLabel("Transaksi Baru");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(20, 15, 250, 25);
        headerPanel.add(lblTitle);

        // --- PANEL KIRI: DAFTAR BARANG & FILTER ---
        RoundPanel pnlDaftarBarang = new RoundPanel(16);
        pnlDaftarBarang.setBackground(Color.WHITE);
        pnlDaftarBarang.setBounds(15, 85, 500, 450);
        pnlDaftarBarang.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(243, 244, 246), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        mainContent.add(pnlDaftarBarang);

        JLabel lblKategori = new JLabel("Kategori Barang");
        lblKategori.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblKategori.setForeground(new Color(107, 114, 128)); // Gray-500
        lblKategori.setBounds(20, 20, 150, 15);
        pnlDaftarBarang.add(lblKategori);

        // Dropdown Kategori Barang
        cbKategori = new JComboBox<>();
        cbKategori.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cbKategori.setBounds(20, 42, 200, 36);
        cbKategori.setBackground(Color.WHITE);
        pnlDaftarBarang.add(cbKategori);

        JLabel lblCari = new JLabel("Cari Nama Barang");
        lblCari.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblCari.setForeground(new Color(107, 114, 128));
        lblCari.setBounds(240, 20, 150, 15);
        pnlDaftarBarang.add(lblCari);

        // Input Cari Barang
        tfCari = new RoundTextField(8, "Ketik untuk mencari...");
        tfCari.setBounds(240, 42, 240, 36);
        pnlDaftarBarang.add(tfCari);

        // Tabel Barang kustom estetik
        String[] colBarang = {"ID Barang", "Nama Barang", "Harga", "Stok"};
        DefaultTableModel modelBarang = new DefaultTableModel(colBarang, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tblBarang = new JTable(modelBarang);
        styleTable(tblBarang);

        JScrollPane spBarang = new JScrollPane(tblBarang);
        spBarang.setBounds(20, 95, 460, 275);
        spBarang.setBackground(Color.WHITE);
        spBarang.getViewport().setBackground(Color.WHITE);
        spBarang.setBorder(BorderFactory.createLineBorder(new Color(243, 244, 246)));
        pnlDaftarBarang.add(spBarang);

        // Input kuantitas jumlah beli & tombol tambah
        JLabel lblQty = new JLabel("Jumlah Beli:");
        lblQty.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblQty.setForeground(new Color(75, 85, 99)); // Gray-600
        lblQty.setBounds(20, 395, 90, 20);
        pnlDaftarBarang.add(lblQty);

        tfQty = new RoundTextField(6, "1");
        tfQty.setBounds(110, 388, 60, 34);
        tfQty.setHorizontalAlignment(JTextField.CENTER);
        tfQty.setFont(new Font("Segoe UI", Font.BOLD, 13));
        pnlDaftarBarang.add(tfQty);

        btnTambah = new RoundButton("Tambah ke Keranjang", 8);
        btnTambah.setBounds(185, 388, 295, 34);
        pnlDaftarBarang.add(btnTambah);

        // --- PANEL KANAN: KERANJANG BELANJA ---
        RoundPanel pnlKeranjang = new RoundPanel(16);
        pnlKeranjang.setBackground(Color.WHITE);
        pnlKeranjang.setBounds(535, 85, 370, 450);
        pnlKeranjang.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(243, 244, 246), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        mainContent.add(pnlKeranjang);

        JLabel lblKeranjangTitle = new JLabel("Keranjang Belanja");
        lblKeranjangTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblKeranjangTitle.setForeground(new Color(17, 24, 39)); // Gray-900
        lblKeranjangTitle.setBounds(20, 15, 200, 25);
        pnlKeranjang.add(lblKeranjangTitle);

        // Tabel Keranjang kustom estetik
        String[] colKeranjang = {"Nama Barang", "Harga", "Jumlah", "Subtotal"};
        DefaultTableModel modelKeranjang = new DefaultTableModel(colKeranjang, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tblKeranjang = new JTable(modelKeranjang);
        styleTable(tblKeranjang);

        JScrollPane spKeranjang = new JScrollPane(tblKeranjang);
        spKeranjang.setBounds(20, 50, 330, 220);
        spKeranjang.setBackground(Color.WHITE);
        spKeranjang.getViewport().setBackground(Color.WHITE);
        spKeranjang.setBorder(BorderFactory.createLineBorder(new Color(243, 244, 246)));
        pnlKeranjang.add(spKeranjang);

        // Tombol hapus item keranjang
        btnHapus = new RoundButton("Hapus Item Terpilih", 8);
        btnHapus.setBackground(new Color(254, 242, 242)); // Merah lembut
        btnHapus.setForeground(new Color(220, 38, 38)); // Merah gelap
        btnHapus.setBounds(20, 280, 330, 34);
        pnlKeranjang.add(btnHapus);

        // Informasi total harga
        JLabel lblTotalTitle = new JLabel("TOTAL BAYAR");
        lblTotalTitle.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblTotalTitle.setForeground(new Color(156, 163, 175)); // Gray-400
        lblTotalTitle.setBounds(20, 335, 150, 15);
        pnlKeranjang.add(lblTotalTitle);

        lblTotalHarga = new JLabel("Rp 0", SwingConstants.RIGHT);
        lblTotalHarga.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTotalHarga.setForeground(new Color(79, 70, 229)); // Indigo-600
        lblTotalHarga.setBounds(150, 325, 200, 30);
        pnlKeranjang.add(lblTotalHarga);

        // Tombol bayar / selesaikan transaksi
        btnBayar = new RoundButton("PROSES PEMBAYARAN", 10);
        btnBayar.setBounds(20, 385, 330, 45);
        pnlKeranjang.add(btnBayar);
    }

    /**
     * Styling JTable agar memiliki visual yang modern & minimalis.
     */
    private void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setForeground(new Color(55, 65, 81)); // Gray-700
        table.setRowHeight(32);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(243, 244, 246)); // Gray-100 saat diseleksi
        table.setSelectionForeground(new Color(17, 24, 39)); // Gray-900 saat diseleksi

        // Styling Header Tabel
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBackground(new Color(249, 250, 251)); // Gray-50
        header.setForeground(new Color(107, 114, 128)); // Gray-500
        header.setPreferredSize(new Dimension(100, 36));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(229, 231, 235)));

        // Penyelarasan isi sel
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
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
            
            // Menggunakan background kustom jika sudah diset secara manual di instance luar
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
