package org.HospitalManagement.view.doctor.manager;

import org.HospitalManagement.utils.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AddAppointmentForm extends JFrame {
    private JTextField patientNameField;
    private JComboBox<String> roomComboBox;
    private JComboBox<String> timeComboBox;
    private JSpinner dateSpinner;
    private JButton saveButton, cancelButton;

    private int doctorId; // Biến lưu trữ doctorId

    // Constructor nhận doctorId
    public AddAppointmentForm(int doctorId) {
        this.doctorId = doctorId;

        setTitle("Thêm Lịch Hẹn Mới");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLayout(new GridLayout(6, 2, 10, 10));

        // Các thành phần giao diện
        JLabel patientNameLabel = new JLabel("Tên Bệnh Nhân:");
        patientNameField = new JTextField();

        JLabel dateLabel = new JLabel("Ngày Hẹn:");
        dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(dateEditor);

        JLabel timeLabel = new JLabel("Giờ Hẹn:");
        timeComboBox = new JComboBox<>(new String[]{"9h", "11h", "13h", "15h"});

        JLabel roomLabel = new JLabel("Phòng:");
        roomComboBox = new JComboBox<>();
        loadRooms(); // Tải danh sách phòng từ cơ sở dữ liệu

        saveButton = new JButton("Lưu");
        cancelButton = new JButton("Hủy");

        // Thêm các thành phần vào JFrame
        add(patientNameLabel);
        add(patientNameField);
        add(dateLabel);
        add(dateSpinner);
        add(timeLabel);
        add(timeComboBox);
        add(roomLabel);
        add(roomComboBox);
        add(saveButton);
        add(cancelButton);

        // Sự kiện nút bấm
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAppointment();
            }
        });

        cancelButton.addActionListener(e -> dispose());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Phương thức tải danh sách phòng từ cơ sở dữ liệu
    private void loadRooms() {
        try {
            String query = "SELECT room_name FROM rooms";
            PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                roomComboBox.addItem(rs.getString("room_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách phòng: " + e.getMessage());
        }
    }

    // Phương thức lưu lịch hẹn vào cơ sở dữ liệu
    private void saveAppointment() {
        String patientName = patientNameField.getText().trim();
        String date = ((JSpinner.DateEditor) dateSpinner.getEditor()).getFormat().format(dateSpinner.getValue());
        String time = (String) timeComboBox.getSelectedItem();
        String room = (String) roomComboBox.getSelectedItem();

        if (patientName.isEmpty() || date.isEmpty() || time.isEmpty() || room.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.");
            return;
        }

        try {
            // Kiểm tra xung đột lịch hẹn
            String checkQuery = "SELECT COUNT(*) AS count FROM appointments WHERE appointment_date = ? AND appointment_time = ? AND room = ?";
            PreparedStatement checkStmt = DatabaseConnection.getConnection().prepareStatement(checkQuery);
            checkStmt.setString(1, date);
            checkStmt.setString(2, time);
            checkStmt.setString(3, room);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt("count") > 0) {
                JOptionPane.showMessageDialog(this, "Phòng đã được đặt cho giờ này. Vui lòng chọn thời gian hoặc phòng khác.");
                return;
            }

            // Thêm lịch hẹn mới
            String insertQuery = "INSERT INTO appointments (patient_name, appointment_date, appointment_time, room, doctor_id, status) VALUES (?, ?, ?, ?, ?, 'PENDING')";
            PreparedStatement insertStmt = DatabaseConnection.getConnection().prepareStatement(insertQuery);
            insertStmt.setString(1, patientName);
            insertStmt.setString(2, date);
            insertStmt.setString(3, time);
            insertStmt.setString(4, room);
            insertStmt.setInt(5, doctorId);

            insertStmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Lịch hẹn đã được thêm thành công.");
            dispose();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu lịch hẹn: " + e.getMessage());
        }
    }

}