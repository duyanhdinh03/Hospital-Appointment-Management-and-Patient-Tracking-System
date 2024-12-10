package org.HospitalManagement.dao;

import org.HospitalManagement.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FeedbackDAO {
    public boolean addFeedbackForAppointment(int patientId, int appointmentId, int qualityRating, int serviceRating, String comments) {
        String sql = "INSERT INTO feedbacks (patient_id, appointment_id, quality_rating, service_rating, comments) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, patientId);
            statement.setInt(2, appointmentId);
            statement.setInt(3, qualityRating);
            statement.setInt(4, serviceRating);
            statement.setString(5, comments);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addGeneralFeedback(int patientId, int qualityRating, int serviceRating, String comments) {
        String sql = "INSERT INTO feedbacks (patient_id, quality_rating, service_rating, comments) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, patientId);
            statement.setInt(2, qualityRating);
            statement.setInt(3, serviceRating);
            statement.setString(4, comments);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
