package org.HospitalManagement.view.patient;

import org.HospitalManagement.view.LoginView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PatientView extends JFrame {
    private int patientId;

    public PatientView(int patientId) {
        this.patientId = patientId;
        initUI();
    }

    private void initUI() {
        setTitle("Patient Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));

        // Buttons
        JButton btnViewAppointments = new JButton("Xem lịch khám");
        JButton btnRegisterAppointment = new JButton("Gửi đơn đăng ký lịch khám");
        JButton btnChangePassword = new JButton("Đổi mật khẩu");
        JButton btnViewHistory = new JButton("Xem lịch sử");
        JButton btnLogout = new JButton("Đăng xuất");

        // Add buttons to panel
        panel.add(btnViewAppointments);
        panel.add(btnRegisterAppointment);
        panel.add(btnChangePassword);
        panel.add(btnViewHistory);
        panel.add(btnLogout);

        // Add panel to frame
        add(panel);

        // Event handling
        btnLogout.addActionListener(e -> logout());
    }

    private void logout() {
        JOptionPane.showMessageDialog(this, "Đăng xuất thành công!");
        dispose();
        new LoginView().setVisible(true); // Quay lại màn hình đăng nhập
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PatientView(1).setVisible(true)); // Test giao diện với ID 1
    }
}
