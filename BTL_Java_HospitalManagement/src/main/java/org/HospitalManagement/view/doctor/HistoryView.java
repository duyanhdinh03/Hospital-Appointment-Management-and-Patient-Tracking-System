package org.HospitalManagement.view.doctor;

import org.HospitalManagement.model.Appointment;
import org.HospitalManagement.dao.AppointmentDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HistoryView extends JFrame {
    private JTable historyTable;
    private JButton backButton, refreshButton;
    private int doctorId;
    private JFrame doctorView; // Tham chiếu đến màn hình doctorView

    public HistoryView(int doctorId, JFrame doctorView) {
        this.doctorId = doctorId;
        this.doctorView = doctorView;

        setTitle("Lịch Sử Khám Bệnh");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table setup
        historyTable = new JTable();
        updateTableData(); // Load data into the table
        add(new JScrollPane(historyTable), BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        backButton = new JButton("Quay Lại");
        refreshButton = new JButton("Làm Mới");

        backButton.addActionListener(e -> {
            // Quay lại màn hình doctorView
            this.dispose();
            doctorView.setVisible(true);
        });

        refreshButton.addActionListener(e -> updateTableData());

        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    /**
     * Updates the table data by retrieving completed or canceled appointments.
     */
    private void updateTableData() {
        String[] columns = {"ID", "Doctor ID", "Patient ID", "Date", "Feedback"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

        try {
            // Retrieve completed or canceled appointments for the current doctor
            List<Appointment> completedAppointments = AppointmentDAO.getCompletedAppointmentsForDoctor(doctorId);

            for (Appointment appointment : completedAppointments) {
                tableModel.addRow(new Object[]{
                        appointment.getId(),
                        appointment.getDoctorId(),
                        appointment.getPatientId(),
                        appointment.getAppointmentDate(),
                        appointment.getFeedback() != null ? appointment.getFeedback() : "N/A"
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        historyTable.setModel(tableModel);
    }
}
