package org.HospitalManagement.view.patient;

import org.HospitalManagement.dao.AppointmentDAO;
import org.HospitalManagement.model.Appointment;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AppointmentHistory extends JFrame {
    private JTable historyTable;
    private int patientId;

    public AppointmentHistory(int patientId, PatientView patientView) {
        this.patientId = patientId;
        setTitle("Lịch sử khám bệnh");
        setSize(600, 400);
        setLayout(new BorderLayout());


        AppointmentDAO appointmentDAO = new AppointmentDAO();
        List<Appointment> appointments = AppointmentDAO.getAppointmentHistoryByPatientId(patientId);

        String[] columnNames = {"Mã lịch hẹn", "Ngày khám", "Bác sĩ", "Chẩn đoán", "Ghi chú"};
        Object[][] data = new Object[appointments.size()][5];

        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            data[i][0] = appointment.getId();
            data[i][1] = appointment.getAppointmentDate();
            data[i][2] = appointment.getDoctorName();
            data[i][3] = appointment.getSymptoms();
            data[i][4] = appointment.getFeedback();
        }

        historyTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(historyTable);
        add(scrollPane, BorderLayout.CENTER);

        // Nút quay lại
        JButton backButton = new JButton("Quay lại");
        backButton.addActionListener(e -> {
            dispose();
            patientView.setVisible(true);
        });
        add(backButton, BorderLayout.SOUTH);
    }
}
