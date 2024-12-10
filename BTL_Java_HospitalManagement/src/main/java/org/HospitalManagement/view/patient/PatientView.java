package org.HospitalManagement.view.patient;

import org.HospitalManagement.model.Appointment;
import org.HospitalManagement.view.LoginView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class PatientView extends JFrame {
    private int patientId;
    private JButton viewAppointmentsButton;
    private JButton changePasswordButton;
    private JButton submitAppointmentButton;
    private JButton sendFeedbackButton;
    private JButton ViewHistoryButton;
    private JButton logoutButton;
    private JLabel messageLabel;
    private JTable appointmentsTable;
    private DefaultTableModel tableModel;

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
        panel.setLayout(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Initialize messageLabel
        messageLabel = new JLabel(" ");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        messageLabel.setForeground(Color.RED);

        // Buttons
        viewAppointmentsButton = new JButton("Xem lịch khám");
        submitAppointmentButton = new JButton("Gửi đơn đăng ký lịch khám");
        sendFeedbackButton = new JButton("Gửi đánh giá");
        ViewHistoryButton = new JButton("Xem lịch sử");
        changePasswordButton = new JButton("Đổi mật khẩu");
        logoutButton = new JButton("Đăng xuất");

        // Add buttons to panel
        panel.add(messageLabel);
        panel.add(viewAppointmentsButton);
        panel.add(submitAppointmentButton);
        panel.add(sendFeedbackButton);
        panel.add(ViewHistoryButton);
        panel.add(changePasswordButton);
        panel.add(logoutButton);


        //Danh sach lich hen
        tableModel = new DefaultTableModel(new String[]{"ID", "Doctor ID", "Date", "Status", "Feedback"}, 0);
        appointmentsTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(appointmentsTable);
        add(scrollPane, BorderLayout.CENTER);
        setLocationRelativeTo(null);

        // Add panel to frame
        add(panel, BorderLayout.CENTER);

    }

    // Các phương thức gắn sự kiện
    public void addViewAppointmentsListener(ActionListener listener) {
        viewAppointmentsButton.addActionListener(listener);
    }

    public void addChangePasswordListener(ActionListener listener) {
        changePasswordButton.addActionListener(listener);
    }

    public void addSubmitAppointmentListener(ActionListener listener) {
        submitAppointmentButton.addActionListener(listener);
    }

    public void addViewHistoryListener(ActionListener listener) {
        ViewHistoryButton.addActionListener(listener);
    }

    public void sendFeedbackListener(ActionListener listener) {
        sendFeedbackButton.addActionListener(listener);
    }

    public void LogoutListener(ActionListener listener){
        logoutButton.addActionListener(listener);
    }

    // Hiển thị thông báo
    public void showMessage(String message) {
        messageLabel.setText(message);
    }

    public void updateAppointmentsTable(List<Appointment> appointments) {
        tableModel.setRowCount(0);
        for (Appointment appointment : appointments) {
            tableModel.addRow(new Object[]{
                    appointment.getId(),
                    appointment.getDoctorId(),
                    appointment.getDoctorName(),
                    appointment.getPatientName(),
                    appointment.getAppointmentDate(),
                    appointment.getStatus(),
                    appointment.getFeedback()
            });
        }
    }

    public int getPatientId(){
        return this.patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PatientView(1).setVisible(true)); // Test giao diện với ID 1
    }


}
