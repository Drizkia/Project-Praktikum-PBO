/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author LENOVO
 */
public class ViewLogin extends JFrame {
    public JTextField tfUsername;
    public JPasswordField pfPassword;
    public JButton btnLogin;

    private JPanel pnlMessage;
    private JLabel lblMessage;
    private javax.swing.Timer messageTimer;

    public enum MessageType {
        SUCCESS, ERROR, WARNING
    }

    public ViewLogin() {
        setTitle("Login");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel Kiri
        JPanel leftPanel = new GradientPanel();
        leftPanel.setPreferredSize(new Dimension(180, 0));
        leftPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcLeft = new GridBagConstraints();
        gbcLeft.gridx = 0;
        gbcLeft.gridy = 0;
        gbcLeft.insets = new Insets(0, 0, 0, 0);
        gbcLeft.anchor = GridBagConstraints.CENTER;
        JLabel title = new JLabel("CAHIER");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        leftPanel.add(title, gbcLeft);
        gbcLeft.gridy++;
        JLabel slogan = new JLabel("Mudah. Cepat. Terpercaya.");
        slogan.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        slogan.setForeground(Color.WHITE);
        leftPanel.add(slogan, gbcLeft);
        add(leftPanel, BorderLayout.WEST);

        // Panel Kanan 
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        int row = 0;

        // Message panel
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        formPanel.add(createMessagePanel(), gbc);
        gbc.gridwidth = 1;

        // Username
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        tfUsername = new JTextField(15);
        formPanel.add(tfUsername, gbc);
        row++;

        // Password
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        pfPassword = new JPasswordField(15);
        formPanel.add(pfPassword, gbc);
        row++;

        // Login button
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        btnLogin = new JButton("Login");
        formPanel.add(btnLogin, gbc);

        add(formPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createMessagePanel() {
        pnlMessage = new JPanel(new BorderLayout());
        pnlMessage.setBackground(new Color(254, 242, 242));
        pnlMessage.setVisible(false);
        lblMessage = new JLabel("", SwingConstants.CENTER);
        lblMessage.setFont(new Font("Segoe UI", Font.BOLD, 13));
        pnlMessage.add(lblMessage, BorderLayout.CENTER);
        return pnlMessage;
    }

    public void showMessage(String text, MessageType type) {
        if (messageTimer != null && messageTimer.isRunning()) {
            messageTimer.stop();
        }
        lblMessage.setText(text);
        switch (type) {
            case SUCCESS:
                pnlMessage.setBackground(new Color(240, 253, 250));
                lblMessage.setForeground(new Color(13, 148, 136));
                break;
            case ERROR:
                pnlMessage.setBackground(new Color(254, 242, 242));
                lblMessage.setForeground(new Color(220, 38, 38));
                break;
            case WARNING:
                pnlMessage.setBackground(new Color(254, 253, 237));
                lblMessage.setForeground(new Color(202, 138, 4));
                break;
        }
        pnlMessage.setVisible(true);
        messageTimer = new javax.swing.Timer(3000, e -> pnlMessage.setVisible(false));
        messageTimer.setRepeats(false);
        messageTimer.start();
    }

    private static class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            int w = getWidth();
            int h = getHeight();
            Color start = new Color(79, 70, 229); // Indigo-600
            Color end = new Color(59, 130, 246); // Blue-500
            GradientPaint gp = new GradientPaint(0, 0, start, w, h, end);
            g2.setPaint(gp);
            g2.fillRect(0, 0, w, h);
            g2.dispose();
        }
    }
}
