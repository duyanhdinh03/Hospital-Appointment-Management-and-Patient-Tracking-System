package org.HospitalManagement.view.doctor.manager;

import org.HospitalManagement.utils.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AppointmentManagementView extends JFrame {
    private JTable appointmentTable;
    private JTextField searchField;
    private JButton searchButton, filterButton, cancelButton, addButton, advancedSearchButton;
    private JComboBox<String> searchModeComboBox;
    private DefaultTableModel tableModel;
    private int doctorId ;

    public AppointmentManagementView(int doctorId) {
        this.doctorId = doctorId;

        setTitle("Quản Lý Danh Sách Lịch Hẹn");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLayout(new BorderLayout());

        // Panel trên cùng chứa chức năng
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // ComboBox để chọn chế độ tìm kiếm
        searchModeComboBox = new JComboBox<>(new String[]{"Tìm theo tên bệnh nhân", "Tìm theo tên bác sĩ"});
        searchField = new JTextField(15);
        searchButton = new JButton("Tìm kiếm");
        filterButton = new JButton("Lọc theo ngày");
        cancelButton = new JButton("Hủy lịch hẹn");
        addButton = new JButton("Thêm lịch hẹn");
        advancedSearchButton = new JButton("Tìm kiếm nâng cao");

        topPanel.add(new JLabel("Chế độ tìm kiếm:"));
        topPanel.add(searchModeComboBox);
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(filterButton);
        topPanel.add(cancelButton);
        topPanel.add(addButton);
        topPanel.add(advancedSearchButton);

        // Bảng hiển thị danh sách lịch hẹn
        String[] columnNames = {"Mã lịch hẹn", "Tên bệnh nhân", "Ngày hẹn", "Giờ hẹn", "Phòng", "Trạng thái"};
        tableModel = new DefaultTableModel(columnNames, 0);
        appointmentTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(appointmentTable);

        // Thêm các thành phần vào JFrame
        add(topPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);

        // Hiển thị JFrame
        setLocationRelativeTo(null);
        setVisible(true);

        // Sự kiện cho các nút
        addActionListeners();
    }

    private void addActionListeners() {
        // Nút tìm kiếm
        searchButton.addActionListener(e -> searchAppointments());

        // Nút lọc theo ngày
        filterButton.addActionListener(e -> filterAppointmentsByDate());

        // Nút hủy lịch hẹn
        cancelButton.addActionListener(e -> cancelAppointment());

        // Nút thêm lịch hẹn
        addButton.addActionListener(e -> addNewAppointment());

        // Nút tìm kiếm nâng cao
        advancedSearchButton.addActionListener(e -> openAdvancedSearchForm());
    }

    // Placeholder methods for event handling
    private void searchAppointments() {
        String searchMode = (String) searchModeComboBox.getSelectedItem();
        String searchKeyword = searchField.getText().trim();
        if (searchKeyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm.");
            return;
        }

        try{

            String query;
            if (searchMode.equals("Tìm theo tên bệnh nhân")) {
                query = "SELECT * FROM appointments WHERE patient_name LIKE ?";
            } else { // Tìm theo tên bác sĩ
                query = "SELECT * FROM appointments WHERE doctor_name LIKE ?";
            }
            PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query);
            stmt.setString(1, "%" + searchKeyword + "%");
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
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + e.getMessage());
        }
    }

    private void filterAppointmentsByDate() {
        String date = JOptionPane.showInputDialog(this, "Nhập ngày (yyyy-MM-dd):");
        if (date == null || date.trim().isEmpty()) return;

        try {
            String query = "SELECT * FROM appointments WHERE appointment_date = ?";
            PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query);
            stmt.setString(1, date);
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
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lọc lịch hẹn: " + e.getMessage());
        }
    }

    private void cancelAppointment() {
        int selectedRow = appointmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một lịch hẹn để hủy.");
            return;
        }

        String appointmentId = (String) tableModel.getValueAt(selectedRow, 0);
        try {
            String query = "UPDATE appointments SET status = 'CANCELED' WHERE appointment_id = ?";
            PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query);
            stmt.setString(1, appointmentId);
            stmt.executeUpdate();

            // Xóa dòng trong bảng
            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Lịch hẹn đã được hủy.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi hủy lịch hẹn: " + e.getMessage());
        }
    }

    private void addNewAppointment() {
        AddAppointmentForm addAppointmentForm = new AddAppointmentForm(doctorId);
        addAppointmentForm.setVisible(true);
    }

    private void openAdvancedSearchForm() {
        new AdvancedSearchForm(this);
    }


}
