package org.HospitalManagement.view;

import org.HospitalManagement.dao.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePassword extends JFrame {
    private final JPasswordField oldPasswordField;
    private final JPasswordField newPasswordField;
    private final JPasswordField confirmPasswordField;  // Thêm trường xác nhận mật khẩu
    private final int userId;

    public ChangePassword(int userId, JFrame previousView) {
        this.userId = userId;

        setTitle("Đổi mật khẩu");
        setSize(800, 600);  // Tăng kích thước cho phù hợp
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        setLocationRelativeTo(null);  // Hiển thị form ở giữa màn hình
        Font font = new Font("Arial", Font.PLAIN, 14);

        // Mật khẩu cũ
        JLabel oldPasswordLabel = new JLabel("Mật khẩu cũ:");
        oldPasswordLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;  // Không cho phép fill
        add(oldPasswordLabel, gbc);

        oldPasswordField = new JPasswordField(20);
        oldPasswordField.setFont(font);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Cho phép fill ngang
        gbc.anchor = GridBagConstraints.WEST;  // Giữ anchor bên trái
        add(oldPasswordField, gbc);

        // Mật khẩu mới
        JLabel newPasswordLabel = new JLabel("Mật khẩu mới:");
        newPasswordLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(newPasswordLabel, gbc);

        newPasswordField = new JPasswordField(20);
        newPasswordField.setFont(font);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(newPasswordField, gbc);

        // Xác nhận mật khẩu mới
        JLabel confirmPasswordLabel = new JLabel("Xác nhận mật khẩu:");
        confirmPasswordLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        add(confirmPasswordLabel, gbc);

        confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setFont(font);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(confirmPasswordField, gbc);

        // Nút đổi mật khẩu
        JButton changePasswordButton = new JButton("Đổi mật khẩu");
        changePasswordButton.setFont(font);
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePassword();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;  // Nút chiếm toàn bộ chiều rộng
        gbc.fill = GridBagConstraints.NONE;  // Không cho phép fill
        gbc.anchor = GridBagConstraints.CENTER; // Căn giữa
        add(changePasswordButton, gbc);

        // Nút quay lại
        JButton backButton = new JButton("Quay lại");
        backButton.setFont(font);
        backButton.addActionListener(e -> {
            dispose();
            previousView.setVisible(true); // Quay lại PatientView
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(backButton, gbc);
    }

    private void changePassword() {
        String oldPassword = new String(oldPasswordField.getPassword());
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu mới và xác nhận không khớp.");
            return;
        }

        if (UserDAO.changePassword(userId, oldPassword, newPassword)) {
            JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công!");
            dispose();

            if (LoginView.instance != null) {
                LoginView.instance.setVisible(true);
            } else {
                LoginView loginView = new LoginView();
                LoginView.instance = loginView;
                loginView.resetFields();
                loginView.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Đổi mật khẩu thất bại!");
        }
    }


}
