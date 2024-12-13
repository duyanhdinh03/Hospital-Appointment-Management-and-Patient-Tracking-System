package org.HospitalManagement.dao;

import org.HospitalManagement.model.Appointment;
import org.HospitalManagement.model.Room;
import org.HospitalManagement.utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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


    public boolean createAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointments (patient_id, doctor_id, appointment_date, symptoms, status, notes) " +
                "VALUES (?,?,?,?,?,?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Kiểm tra xem doctorId có null không, nếu có thì thay thế bằng giá trị mặc định
            Integer doctorId = appointment.getDoctorId();
            if (doctorId == null) {
                statement.setNull(2, java.sql.Types.INTEGER);
            } else {
                statement.setInt(2, doctorId);
            }

            statement.setInt(1, appointment.getPatientId());
            statement.setDate(3, new java.sql.Date(appointment.getAppointmentDate().getTime()));
            statement.setString(4, appointment.getSymptoms());
            statement.setString(5, appointment.getStatus());
            statement.setString(6, appointment.getFeedback());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Lịch hẹn thêm thành công !!");
                return true;
            } else {
                System.out.println("Lịch hẹn thêm thất bại !!");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Appointment> getCompletedAppointmentsByPatientId(int patientId) {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT a.id, a.patient_id, a.doctor_id, a.appointment_date, a.symptoms, a.status, a.notes, " +
                    "p.full_name AS patient_name, d.full_name AS doctor_name " +
                    "FROM appointments a " +
                    "JOIN patients p ON a.patient_id = p.id " +
                    "JOIN doctors d ON a.doctor_id = d.id " +
                    "WHERE a.patient_id = ? AND a.status = 'COMPLETED'"; // Truy vấn chỉ lấy lịch hẹn đã hoàn thành
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



    //Doctor
    public static List<Appointment> getAppointmentHistoryByPatientId(int patientId) {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Lấy các lịch hẹn có trạng thái COMPLETED hoặc CANCELED
            String sql = "SELECT a.id, a.patient_id, a.doctor_id, a.appointment_date, a.symptoms, a.status, a.notes, " +
                    "p.full_name AS patient_name, d.full_name AS doctor_name " +
                    "FROM appointments a " +
                    "JOIN patients p ON a.patient_id = p.id " +
                    "JOIN doctors d ON a.doctor_id = d.id " +
                    "WHERE a.patient_id = ? AND (a.status = 'COMPLETED' OR a.status = 'CANCELED')";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, patientId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(resultSet.getInt("id"));
                appointment.setPatientId(resultSet.getInt("patient_id"));
                appointment.setDoctorId(resultSet.getInt("doctor_id"));
                appointment.setDoctorName(resultSet.getString("doctor_name"));
                appointment.setPatientName(resultSet.getString("patient_name"));
                appointment.setAppointmentDate(resultSet.getDate("appointment_date"));
                appointment.setSymptoms(resultSet.getString("symptoms"));
                appointment.setStatus(resultSet.getString("status")); // COMPLETED hoặc CANCELED
                appointment.setFeedback(resultSet.getString("notes"));
                appointments.add(appointment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointments;
    }

    public static String getAvailableRoom(String date, String time) throws SQLException {
        String query = "SELECT room_name FROM rooms r " +
                "WHERE r.room_id NOT IN (SELECT room_id FROM appointments " +
                "WHERE appointment_date = ? AND appointment_time = ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, date);
            stmt.setString(2, time);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("room_name");
            }
        }
        return null;
    }

    public static void saveAppointment(String patientName, String doctorName, String date, String time, String room) throws SQLException {
        String query = "INSERT INTO appointments (patient_name, doctor_name, appointment_date, appointment_time, room, status) " +
                "VALUES (?, ?, ?, ?, ?, 'PENDING')";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, patientName);
            stmt.setString(2, doctorName);
            stmt.setString(3, date);
            stmt.setString(4, time);
            stmt.setString(5, room);
            stmt.executeUpdate();
        }
    }

    // Lấy danh sách lịch hẹn theo trạng thái
    public List<Appointment> getAppointmentsByStatus(String status) {
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT * FROM appointments WHERE status = ?";

        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(query)) {
            statement.setString(1, status);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(resultSet.getInt("id"));
                appointment.setDoctorId(resultSet.getInt("doctor_id"));
                appointment.setPatientId(resultSet.getInt("patient_id"));
                appointment.setDate(resultSet.getDate("appointment_date"));
                appointment.setStatus(resultSet.getString("status"));
                appointment.setFeedback(resultSet.getString("notes"));
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }


    // Lấy thông tin lịch hẹn theo ID
    public Appointment getAppointmentById(int appointmentId) {
        String query = "SELECT * FROM appointments WHERE id = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(query)) {
            statement.setInt(1, appointmentId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(resultSet.getInt("id"));
                appointment.setDoctorId(resultSet.getInt("doctor_id"));
                appointment.setPatientId(resultSet.getInt("patient_id"));
                appointment.setDate(resultSet.getDate("date"));
                appointment.setStatus(resultSet.getString("status"));
                appointment.setFeedback(resultSet.getString("feedback"));
                return appointment;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật thông tin lịch hẹn
    public boolean updateAppointment(Appointment appointment) {
        String query = "UPDATE appointments SET doctor_id = ?, status = ? WHERE id = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(query)) {
            statement.setInt(1, appointment.getDoctorId());
            statement.setString(2, appointment.getStatus());
            statement.setInt(3, appointment.getId());
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Appointment> getCompletedAppointmentsForDoctor(int doctorId) throws Exception {
        List<Appointment> appointments = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection(); // Ensure DatabaseUtils is implemented
        String query = "SELECT * FROM appointments WHERE doctor_id = ? AND (status = 'COMPLETED' OR status = 'CANCELED')";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, doctorId);

        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Appointment appointment = new Appointment();
            appointment.setId(resultSet.getInt("id"));
            appointment.setDoctorId(resultSet.getInt("doctor_id"));
            appointment.setPatientId(resultSet.getInt("patient_id"));
            appointment.setAppointmentDate(resultSet.getDate("appointment_date"));
            appointment.setFeedback(resultSet.getString("feedback"));
            appointments.add(appointment);
        }

        resultSet.close();
        statement.close();
        connection.close();

        return appointments;
    }

    public static List<Appointment> getAppointmentsForDoctor(int doctorId) {
        // Giả sử bạn dùng JDBC, đây là cách truy vấn cơ sở dữ liệu
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT * FROM appointments WHERE doctor_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, doctorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                appointments.add(new Appointment(
                        rs.getInt("id"),
                        rs.getInt("doctor_id"),
                        rs.getInt("patient_id"),
                        rs.getString("doctor_name"),
                        rs.getString("patient_name"),
                        rs.getDate("appointment_date"),
                        rs.getString("symptoms"),
                        rs.getString("status"),
                        rs.getString("notes")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointments;
    }

}
