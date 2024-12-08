package org.HospitalManagement.controller;

import org.HospitalManagement.view.LoginView;
import org.HospitalManagement.view.patient.PatientView;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private final LoginView loginView;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;
        initialize();
    }

    private void initialize() {
        // Lắng nghe sự kiện khi nhấn nút đăng nhập
        loginView.addLoginListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = loginView.getUsername();
                String password = loginView.getPassword();
                handleLogin(username, password);
            }
        });
    }

    private Connection connect() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/hams", "root", "789123456");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Kết nối cơ sở dữ liệu thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }

    private void handleLogin(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            if (conn == null) return; // Nếu không kết nối được, dừng logic đăng nhập.

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String dbPassword = rs.getString("password"); // Lấy mật khẩu từ CSDL
                String role = rs.getString("role"); // Lấy vai trò từ CSDL

                // Kiểm tra mật khẩu
                if (password.equals(dbPassword)) {
                    JOptionPane.showMessageDialog(loginView, "Đăng nhập thành công!");

                    int userId = rs.getInt("id");
                    openRoleBasedView(role, userId); // Điều hướng giao diện dựa trên vai trò.
                    loginView.dispose(); // Đóng giao diện đăng nhập.
                } else {
                    JOptionPane.showMessageDialog(loginView, "Mật khẩu không đúng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(loginView, "Tên đăng nhập không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(loginView, "Đã xảy ra lỗi trong quá trình đăng nhập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void openRoleBasedView(String role, int userId) {
        switch (role.toUpperCase()) {
            case "PATIENT":
                PatientView pt = new PatientView(userId);
                pt.setVisible(true);
                PatientController patientController = new PatientController(pt, userId);
                patientController.showPatientView();
                break;
            case "DOCTOR":
                // Triển khai DoctorView sau
                JOptionPane.showMessageDialog(null, "Doctor view chưa triển khai!");
                break;
            case "ADMIN":
                // Triển khai AdminView sau
                JOptionPane.showMessageDialog(null, "Admin view chưa triển khai!");
                break;
            default:
                JOptionPane.showMessageDialog(null, "Vai trò không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

    public void showLoginView() {
        loginView.setVisible(true);
    }
}
