package org.HospitalManagement.view.doctor.staticsic;

import org.HospitalManagement.model.Appointment;
import org.HospitalManagement.dao.AppointmentDAO;
import org.HospitalManagement.view.doctor.DoctorView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class StatisticsView extends JFrame {
    private JTable statisticsTable;
    private JComboBox<String> filterComboBox;
    private JButton filterButton, resetButton, exportButton, backButton;
    private int doctorId;

    public StatisticsView(int doctorId) {
        this.doctorId = doctorId;

        setTitle("Thống Kê Lịch Hẹn");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Fetch appointments for the current doctor
        List<Appointment> appointments = AppointmentDAO.getAppointmentsForDoctor(doctorId);

        // Table to display appointments
        statisticsTable = new JTable();
        updateTableData(appointments); // Populate data into the table
        add(new JScrollPane(statisticsTable), BorderLayout.CENTER);

        // Filter options
        JPanel topPanel = new JPanel();
        filterComboBox = new JComboBox<>(new String[]{
                "Lọc theo ngày", "Lọc theo trạng thái", "Lọc theo ID bệnh nhân", "Lọc theo phòng"});
        filterButton = new JButton("Lọc");
        resetButton = new JButton("Bỏ Lọc");

        topPanel.add(filterComboBox);
        topPanel.add(filterButton);
        topPanel.add(resetButton);
        add(topPanel, BorderLayout.NORTH);

        // Buttons at the bottom
        JPanel bottomPanel = new JPanel();
        exportButton = new JButton("Xuất File");
        exportButton.addActionListener(e -> {
            // Open export format selection form
            new ExportFormatForm(statisticsTable).setVisible(true);
        });

        backButton = new JButton("Quay Lại");
        backButton.addActionListener(e -> {
            // Return to DoctorView
            dispose();
            new DoctorView(doctorId).setVisible(true);
        });

        bottomPanel.add(exportButton);
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    /**
     * Populates data into the statistics table.
     */
    private void updateTableData(List<Appointment> appointments) {
        String[] columns = {"ID", "Doctor ID", "Patient ID", "Date", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

        for (Appointment appointment : appointments) {
            tableModel.addRow(new Object[]{
                    appointment.getId(),
                    appointment.getDoctorId(),
                    appointment.getPatientId(),
                    appointment.getAppointmentDate(),
                    appointment.getStatus()
            });
        }
        statisticsTable.setModel(tableModel);
    }
}
