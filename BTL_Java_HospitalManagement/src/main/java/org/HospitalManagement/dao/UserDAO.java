package org.HospitalManagement.dao;

import org.HospitalManagement.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public static boolean changePassword(int userId, String oldPassword, String newPassword) {
        String query = "SELECT password FROM users WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");

                // Kiểm tra mật khẩu cũ
                if (storedPassword.equals(oldPassword)) {
                    String updateQuery = "UPDATE users SET password = ? WHERE id = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                        updateStmt.setString(1, newPassword);
                        updateStmt.setInt(2, userId);
                        int rowsUpdated = updateStmt.executeUpdate();
                        return rowsUpdated > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
