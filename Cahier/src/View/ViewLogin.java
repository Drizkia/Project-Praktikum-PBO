/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author LENOVO
 */
public class ViewLogin extends JFrame {
    public JTextField tfUsername = new JTextField();
    public JPasswordField pfPassword = new JPasswordField();
    public JButton btnLogin = new JButton("LOGIN");

    public ViewLogin() {

        setTitle("Login");
        setSize(350, 250);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel lbUsername = new JLabel("Username");
        lbUsername.setBounds(40, 40, 80, 20);
        add(lbUsername);

        tfUsername.setBounds(140, 40, 150, 25);
        add(tfUsername);

        JLabel lbPassword = new JLabel("Password");
        lbPassword.setBounds(40, 90, 100, 20);
        add(lbPassword);

        pfPassword.setBounds(140, 90, 150, 25);
        add(pfPassword);

        btnLogin.setBounds(110, 150, 120, 30);
        add(btnLogin);

        setVisible(true);
    }
}
