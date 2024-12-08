package org.HospitalManagement.dao;

import org.HospitalManagement.model.Appointment;
import org.HospitalManagement.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hams";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "789123456";

    public List<Appointment> getAppointmentsByPatientId(int patientId) {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT a.id, a.patient_id, a.doctor_id, a.appointment_date, a.symptoms, a.status, a.notes, " +
                    "p.full_name AS patient_name, d.full_name AS doctor_name " +
                    "FROM appointments a " +
                    "JOIN patients p ON a.patient_id = p.id " +
                    "JOIN doctors d ON a.doctor_id = d.id " +
                    "WHERE a.patient_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, patientId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(resultSet.getInt("id"));
                appointment.setPatientId(resultSet.getInt("patient_id"));
                appointment.setDoctorId(resultSet.getInt("doctor_id"));
                appointment.setDoctorName(resultSet.getString("doctor_name")); // Lấy từ JOIN
                appointment.setPatientName(resultSet.getString("patient_name")); // Lấy từ JOIN
                appointment.setAppointmentDate(resultSet.getDate("appointment_date"));
                appointment.setSymptoms(resultSet.getString("symptoms"));
                appointment.setStatus(resultSet.getString("status"));
                appointment.setFeedback(resultSet.getString("notes"));
                appointments.add(appointment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointments;
    }

}
