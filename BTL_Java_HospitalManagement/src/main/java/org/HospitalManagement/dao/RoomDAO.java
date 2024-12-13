package org.HospitalManagement.dao;

import org.HospitalManagement.model.Room;
import org.HospitalManagement.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {
    // Lấy danh sách tất cả các phòng
    public static List<Room> getAllRooms() throws SQLException {
        String query = "SELECT * FROM rooms";
        List<Room> rooms = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Room room = new Room(
                        rs.getInt("room_id"),
                        rs.getString("room_name"),
                        rs.getInt("max_appointments_morning"),
                        rs.getInt("max_appointments_afternoon")
                );
                rooms.add(room);
            }
        }
        return rooms;
    }
}
