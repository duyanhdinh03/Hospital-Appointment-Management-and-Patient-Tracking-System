package org.HospitalManagement.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JLabel lblMessage;

    public LoginView() {
        // Thiết lập JFrame
        setTitle("Đăng Nhập");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Căn giữa màn hình

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel lblUsername = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblUsername, gbc);

        txtUsername = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtUsername, gbc);

        JLabel lblPassword = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblPassword, gbc);

        txtPassword = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(txtPassword, gbc);

        btnLogin = new JButton("Login");
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(btnLogin, gbc);

        lblMessage = new JLabel("");
        lblMessage.setForeground(Color.RED);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(lblMessage, gbc);

        add(panel);
    }

    // Thêm sự kiện cho nút Login
    public void addLoginListener(ActionListener listener) {
        btnLogin.addActionListener(listener);
    }

    // Lấy thông tin username và password
    public String getUsername() {
        return txtUsername.getText();
    }

    public String getPassword() {
        return new String(txtPassword.getPassword());
    }

    // Hiển thị thông báo
    public void showMessage(String message, boolean isError) {
        lblMessage.setText(message);
        lblMessage.setForeground(isError ? Color.RED : Color.GREEN);
    }
}
