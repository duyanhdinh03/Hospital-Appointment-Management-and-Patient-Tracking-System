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
    private static LoginView loginView ;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;
        initialize();
    }

    private void initialize() {
        // Đăng nhập
        loginView.addLoginListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = loginView.getUsername();
                String password = loginView.getPassword();
                String result = handleLogin(username, password);

                if (result.equals("SUCCESS")) {
                    loginView.dispose();
                } else {
                    JOptionPane.showMessageDialog(loginView, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private static Connection connect() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/hams", "root", "789123456");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Kết nối cơ sở dữ liệu thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }

    public static String handleLogin(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            if (conn == null) return "ERROR: Database connection failed";

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String dbPassword = rs.getString("password");
                String role = rs.getString("role");

                if (password.equals(dbPassword)) {
                    int userId = rs.getInt("id");
                    openRoleBasedView(role, userId);
                    return "SUCCESS";
                } else {
                    return "ERROR: Incorrect password";
                }
            } else {
                return "ERROR: Username not found";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "ERROR: Database error occurred"; // Lỗi CSDL
        }
    }



    private static void openRoleBasedView(String role, int userId) {
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
