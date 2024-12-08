package org.HospitalManagement.view.patient;

import org.HospitalManagement.model.Appointment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AppointmentList extends JFrame {
    private JTable appointmentsTable;
    private DefaultTableModel tableModel;

    public AppointmentList(List<Appointment> appointments) {
        setTitle("Danh sách lịch khám");
        setSize(600,400);
        setLocationRelativeTo(null);

        tableModel = new DefaultTableModel(new String[]{"ID", "Tên bác sĩ ", "Tên bệnh nhân", "ngày khám", "trạng thái ", "ghi chú "}, 0);
        appointmentsTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(appointmentsTable);
        add(scrollPane, BorderLayout.CENTER);

        //Fetch data
        updateAppointmentsTable(appointments);
    }
    public void updateAppointmentsTable(List<Appointment> appointments) {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ
        for (Appointment appointment : appointments) {
            tableModel.addRow(new Object[]{
                    appointment.getId(),
                    appointment.getDoctorName(),
                    appointment.getPatientName(),
                    appointment.getAppointmentDate(),
                    appointment.getStatus(),
                    appointment.getFeedback()
            });
        }
    }
}
