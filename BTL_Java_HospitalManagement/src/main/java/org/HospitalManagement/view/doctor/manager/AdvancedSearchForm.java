package org.HospitalManagement.view.doctor.manager;

import org.HospitalManagement.utils.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdvancedSearchForm extends JDialog {
    private JTextField startDateField, endDateField, doctorNameField;
    private JComboBox<String> statusComboBox;
    private JButton searchButton, cancelButton;
    private DefaultTableModel tableModel;

    public AdvancedSearchForm(JFrame parent) {
        super(parent, "Tìm kiếm nâng cao", true);
        setSize(400, 300);
        setLayout(new GridLayout(5, 2, 10, 10));

        // Các trường nhập và lựa chọn
        add(new JLabel("Ngày bắt đầu (yyyy-MM-dd):"));
        startDateField = new JTextField();
        add(startDateField);

        add(new JLabel("Ngày kết thúc (yyyy-MM-dd):"));
        endDateField = new JTextField();
        add(endDateField);

        add(new JLabel("Tên bác sĩ:"));
        doctorNameField = new JTextField();
        add(doctorNameField);

        add(new JLabel("Trạng thái:"));
        statusComboBox = new JComboBox<>(new String[]{"Tất cả", "COMPLETED", "CANCELED", "PENDING"});
        add(statusComboBox);

        // Nút tìm kiếm và hủy
        searchButton = new JButton("Tìm kiếm");
        cancelButton = new JButton("Hủy");
        add(searchButton);
        add(cancelButton);

        // Sự kiện cho các nút
        searchButton.addActionListener(e -> performAdvancedSearch());
        cancelButton.addActionListener(e -> dispose());

        // Hiển thị form
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void performAdvancedSearch() {
        String startDate = startDateField.getText().trim();
        String endDate = endDateField.getText().trim();
        String doctorName = doctorNameField.getText().trim();
        String status = (String) statusComboBox.getSelectedItem();

        try {
            StringBuilder query = new StringBuilder("SELECT * FROM appointments WHERE 1=1");

            if (!startDate.isEmpty()) query.append(" AND appointment_date >= ?");
            if (!endDate.isEmpty()) query.append(" AND appointment_date <= ?");
            if (!doctorName.isEmpty()) query.append(" AND doctor_name LIKE ?");
            if (!status.equals("Tất cả")) query.append(" AND status = ?");

            PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query.toString());

            int paramIndex = 1;
            if (!startDate.isEmpty()) stmt.setString(paramIndex++, startDate);
            if (!endDate.isEmpty()) stmt.setString(paramIndex++, endDate);
            if (!doctorName.isEmpty()) stmt.setString(paramIndex++, "%" + doctorName + "%");
            if (!status.equals("Tất cả")) stmt.setString(paramIndex++, status);

            ResultSet rs = stmt.executeQuery();
            tableModel.setRowCount(0);

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getString("appointment_id"),
                        rs.getString("patient_name"),
                        rs.getString("appointment_date"),
                        rs.getString("appointment_time"),
                        rs.getString("room"),
                        rs.getString("status")
                });
            }
            dispose();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm nâng cao: " + e.getMessage());
        }
    }
}
