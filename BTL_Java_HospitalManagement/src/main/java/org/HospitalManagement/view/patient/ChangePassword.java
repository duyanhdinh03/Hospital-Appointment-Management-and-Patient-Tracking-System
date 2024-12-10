package org.HospitalManagement.view.patient;

import org.HospitalManagement.dao.PatientDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePassword extends JFrame {
    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;  // Thêm trường xác nhận mật khẩu
    private JButton changePasswordButton;
    private JButton backButton;
    private int patientId;

    public ChangePassword(int patientId, PatientView patientView) {
        this.patientId = patientId;

        setTitle("Đổi mật khẩu");
        setSize(400, 250);  // Tăng kích thước cho phù hợp
        setLayout(new GridLayout(4, 2, 10, 10));  // Cập nhật grid layout
        setLocation(1,1);
        // Mật khẩu cũ
        add(new JLabel("Mật khẩu cũ:"));
        oldPasswordField = new JPasswordField();
        add(oldPasswordField);

        // Mật khẩu mới
        add(new JLabel("Mật khẩu mới:"));
        newPasswordField = new JPasswordField();
        add(newPasswordField);

        // Xác nhận mật khẩu mới
        add(new JLabel("Xác nhận mật khẩu mới:"));
        confirmPasswordField = new JPasswordField();
        add(confirmPasswordField);

        // Nút đổi mật khẩu
        changePasswordButton = new JButton("Đổi mật khẩu");
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePassword();
            }
        });
        add(changePasswordButton);

        // Nút quay lại
        backButton = new JButton("Quay lại");
        backButton.addActionListener(e -> {
            dispose();
            patientView.setVisible(true); // Quay lại PatientView
        });
        add(backButton);
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
            JOptionPane.showMessageDialog(this, "Mật khẩu mới và xác nhận mật khẩu không khớp.");
            return;
        }

        PatientDAO patientDAO = new PatientDAO();
        boolean success = patientDAO.changePassword(patientId, oldPassword, newPassword);
        if (success) {
            JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công.");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Mật khẩu cũ không đúng.");
        }
    }


}
