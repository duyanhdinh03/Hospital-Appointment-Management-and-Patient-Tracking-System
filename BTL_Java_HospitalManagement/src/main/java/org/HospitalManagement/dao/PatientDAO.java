package org.HospitalManagement.dao;

import org.HospitalManagement.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientDAO {

    public boolean changePassword(int patientId, String oldPassword, String newPassword) {
        String sql = "SELECT u.password FROM users u "
                + "JOIN patients p ON u.id = p.user_id "
                + "WHERE p.id = ?";  // Truy vấn user_id dựa trên patientId
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, patientId);  // Set patientId vào truy vấn
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");  // Lấy mật khẩu đã lưu

                // Kiểm tra mật khẩu cũ
                if (storedPassword.equals(oldPassword)) {
                    String updateSql = "UPDATE users SET password = ? WHERE id = (SELECT user_id FROM patients WHERE id = ?)";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                        updateStatement.setString(1, newPassword);  // Set mật khẩu mới
                        updateStatement.setInt(2, patientId);  // Set patientId vào câu truy vấn
                        updateStatement.executeUpdate();
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
