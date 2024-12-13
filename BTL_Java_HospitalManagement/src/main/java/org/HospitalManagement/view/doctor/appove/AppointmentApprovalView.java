package org.HospitalManagement.view.doctor.appove;

import org.HospitalManagement.dao.AppointmentDAO;
import org.HospitalManagement.model.Appointment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AppointmentApprovalView extends JFrame {
    private JTable appointmentsTable;
    private DefaultTableModel tableModel;
    private JButton approveButton;
    private JButton rejectButton;
    private JButton backButton;
    private int doctorId;
    private AppointmentDAO appointmentDAO;

    public AppointmentApprovalView(int doctorId) {
        this.doctorId = doctorId;
        this.appointmentDAO = new AppointmentDAO();
        initUI();
        loadPendingAppointments();
    }

    private void initUI() {
        setTitle("Duyệt lịch hẹn");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel panel = new JPanel(new BorderLayout());

        // Table setup
        tableModel = new DefaultTableModel(new String[]{"Tên bệnh nhân", "Triệu chứng", "Ngày khám", "Trạng thái"}, 0);
        appointmentsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(appointmentsTable);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        approveButton = new JButton("Duyệt");
        rejectButton = new JButton("Từ chối");
        backButton = new JButton("Quay lại");
        buttonPanel.add(approveButton);
        buttonPanel.add(rejectButton);
        buttonPanel.add(backButton);

        // Add components to panel
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        // Button actions
        approveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                approveSelectedAppointment();
            }
        });

        rejectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rejectSelectedAppointment();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void loadPendingAppointments() {
        List<Appointment> pendingAppointments = appointmentDAO.getAppointmentsByStatus("PENDING");
        tableModel.setRowCount(0); // Clear table

        for (Appointment appointment : pendingAppointments) {
            tableModel.addRow(new Object[]{
                    appointment.getPatientName(),
                    appointment.getSymptoms(),
                    appointment.getAppointmentDate(),
                    appointment.getStatus()
            });
        }
    }

    private void approveSelectedAppointment() {
        int selectedRow = appointmentsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một lịch hẹn để duyệt.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int appointmentId = (int) tableModel.getValueAt(selectedRow, 0);
        Appointment appointment = appointmentDAO.getAppointmentById(appointmentId);
        appointment.setDoctorId(doctorId);
        appointment.setStatus("CONFIRMED");

        if (appointmentDAO.updateAppointment(appointment)) {
            JOptionPane.showMessageDialog(this, "Lịch hẹn đã được duyệt thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadPendingAppointments();
        } else {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi duyệt lịch hẹn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void rejectSelectedAppointment() {
        int selectedRow = appointmentsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một lịch hẹn để từ chối.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int appointmentId = (int) tableModel.getValueAt(selectedRow, 0);
        Appointment appointment = appointmentDAO.getAppointmentById(appointmentId);
        appointment.setStatus("REJECTED");

        if (appointmentDAO.updateAppointment(appointment)) {
            JOptionPane.showMessageDialog(this, "Lịch hẹn đã bị từ chối.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadPendingAppointments();
        } else {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi từ chối lịch hẹn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
