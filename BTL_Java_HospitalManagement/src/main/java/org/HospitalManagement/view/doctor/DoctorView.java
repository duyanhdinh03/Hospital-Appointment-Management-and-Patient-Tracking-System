package org.HospitalManagement.view.doctor;

import org.HospitalManagement.model.Appointment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class DoctorView extends JFrame {
    private int doctorId;
    private JButton viewAppointmentManagementButton ;
    private JButton viewRequestButton;
    private JButton viewStatisticalButton ;
    private JButton viewHistoryButton ;
    private JButton logoutButton ;
    private JButton changePasswordButton ;
    private JLabel messageLabel;
    private JTable appointmentsTable;
    private DefaultTableModel tableModel;

    public DoctorView(int doctorId) {
        this.doctorId = doctorId;
        initUI();
    }

    private void initUI() {
        setTitle("Doctor View");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Main panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10,5,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        //message label
        messageLabel = new JLabel("");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        messageLabel.setForeground(Color.RED);

        //Buttons
        viewAppointmentManagementButton = new JButton("Quản lý danh sách lịch hẹn");
        viewRequestButton = new JButton("Xem yêu cầu lịch hen ");
        viewStatisticalButton = new JButton("Thống kê lịch hẹn");
        viewHistoryButton = new JButton("Xem lịch sử khám bệnh");
        changePasswordButton = new JButton("Đổi mật khẩu");
        logoutButton = new JButton("Đăng xuất");

        // Add buttons to panel
        panel.add(messageLabel);
        panel.add(viewAppointmentManagementButton);
        panel.add(viewRequestButton);
        panel.add(viewStatisticalButton);
        panel.add(viewHistoryButton);
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
    public void addViewAppointmentManagementListener(ActionListener listener) {
        viewAppointmentManagementButton.addActionListener(listener);
    }

    public void addViewRequestListener(ActionListener listener) {
        viewRequestButton.addActionListener(listener);
    }

    public void addViewStatisticalListener(ActionListener listener) {
        viewStatisticalButton.addActionListener(listener);
    }

    public void addViewHistoryListener(ActionListener listener) {
        viewHistoryButton.addActionListener(listener);
    }

    public void changePasswordListener(ActionListener listener) {
        changePasswordButton.addActionListener(listener);
    }

    public void LogoutListener(ActionListener listener){
        logoutButton.addActionListener(listener);
    }

    // Hiển thị thông báo
    public void showMessage(String message) {
        messageLabel.setText(message);
    }


    public int getDoctorId() {
        return this.doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }
}
