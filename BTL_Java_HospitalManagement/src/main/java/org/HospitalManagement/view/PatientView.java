package org.HospitalManagement.view;

import org.HospitalManagement.model.Patient;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class PatientView extends JFrame {
    private JTextField txtId, txtName, txtDob, txtAddress, txtPhone;
    private JComboBox<String> cmbGender;
    private JButton btnAdd, btnEdit, btnDelete, btnClear, btnSortByName, btnSortByAddress;
    private JTable tblPatients;
    private JLabel lblMessage;

    // Cột bảng bệnh nhân
    private String[] columnNames = {"ID", "Name", "Date of Birth", "Address", "Phone", "Gender"};
    private DefaultTableModel tableModel;

    public PatientView() {
        setTitle("Patient Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelForm = createFormPanel();
        JPanel panelButtons = createButtonPanel();
        JScrollPane scrollPaneTable = createTablePanel();

        lblMessage = new JLabel(" ");
        lblMessage.setForeground(Color.RED);
        lblMessage.setHorizontalAlignment(SwingConstants.CENTER);

        add(panelForm, BorderLayout.NORTH);
        add(scrollPaneTable, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);
        add(lblMessage, BorderLayout.PAGE_END);

        setVisible(true);
    }

    /**
     * Tạo panel chứa form nhập thông tin bệnh nhân
     */
    private JPanel createFormPanel() {
        JPanel panelForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Khởi tạo các thành phần
        JLabel lblId = new JLabel("ID:");
        JLabel lblName = new JLabel("Name:");
        JLabel lblDob = new JLabel("Date of Birth:");
        JLabel lblAddress = new JLabel("Address:");
        JLabel lblPhone = new JLabel("Phone:");
        JLabel lblGender = new JLabel("Gender:");

        txtId = new JTextField(10);
        txtId.setEditable(false); // Không cho phép chỉnh sửa ID
        txtName = new JTextField(15);
        txtDob = new JTextField(10);
        txtAddress = new JTextField(20);
        txtPhone = new JTextField(15);
        cmbGender = new JComboBox<>(new String[]{"Male", "Female", "Other"});

        // Thêm các thành phần vào panelForm
        gbc.anchor = GridBagConstraints.WEST;

        // ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelForm.add(lblId, gbc);

        gbc.gridx = 1;
        panelForm.add(txtId, gbc);

        // Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelForm.add(lblName, gbc);

        gbc.gridx = 1;
        panelForm.add(txtName, gbc);

        // DOB
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelForm.add(lblDob, gbc);

        gbc.gridx = 1;
        panelForm.add(txtDob, gbc);

        // Address
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelForm.add(lblAddress, gbc);

        gbc.gridx = 1;
        panelForm.add(txtAddress, gbc);

        // Phone
        gbc.gridx = 0;
        gbc.gridy = 4;
        panelForm.add(lblPhone, gbc);

        gbc.gridx = 1;
        panelForm.add(txtPhone, gbc);

        // Gender
        gbc.gridx = 0;
        gbc.gridy = 5;
        panelForm.add(lblGender, gbc);

        gbc.gridx = 1;
        panelForm.add(cmbGender, gbc);

        return panelForm;
    }

    /**
     * Tạo panel chứa các nút chức năng
     */
    private JPanel createButtonPanel() {
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        btnAdd = new JButton("Add");
        btnEdit = new JButton("Edit");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear");
        btnSortByName = new JButton("Sort By Name");
        btnSortByAddress = new JButton("Sort By Address");

        // Disable Edit và Delete ban đầu
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);

        // Thêm các nút vào panelButtons
        panelButtons.add(btnAdd);
        panelButtons.add(btnEdit);
        panelButtons.add(btnDelete);
        panelButtons.add(btnClear);
        panelButtons.add(btnSortByName);
        panelButtons.add(btnSortByAddress);

        return panelButtons;
    }

    public void showPatient(Patient patient) {
        txtId.setText("" + patient.getPatientID());
        txtName.setText(patient.getName());
        txtDob.setText(patient.getDob().toString());
        txtAddress.setText(patient.getAddress());
        cmbGender.setSelectedItem(patient.getGender());
        txtPhone.setText(patient.getPhone());

        btnEdit.setEnabled(true);
        btnDelete.setEnabled(true);

        btnAdd.setEnabled(false);
    }
    /**
     * Tạo bảng hiển thị danh sách bệnh nhân
     */
    private JScrollPane createTablePanel() {
        tableModel = new DefaultTableModel(columnNames, 0);
        tblPatients = new JTable(tableModel);
        tblPatients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        return new JScrollPane(tblPatients);
    }

    /**
     * Hiển thị thông báo
     */
    public void showMessage(String message, boolean isError) {
        lblMessage.setText(message);
        lblMessage.setForeground(isError ? Color.RED : Color.GREEN);
    }

    /**
     * Hiển thị danh sách bệnh nhân lên bảng
     */
    public void showPatientList(List<Patient> patientList) {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ
        for (Patient patient : patientList) {
            tableModel.addRow(new Object[]{
                    patient.getPatientID(),
                    patient.getName(),
                    patient.getDob(),
                    patient.getAddress(),
                    patient.getPhone(),
                    patient.getGender()
            });
        }
    }

    /**
     * Điền thông tin bệnh nhân từ hàng được chọn
     */
    public void fillPatientFromSelectedRow() {
        int row = tblPatients.getSelectedRow();
        if (row >= 0) {
            txtId.setText(tblPatients.getValueAt(row, 0).toString());
            txtName.setText(tblPatients.getValueAt(row, 1).toString());
            txtDob.setText(tblPatients.getValueAt(row, 2).toString());
            txtAddress.setText(tblPatients.getValueAt(row, 3).toString());
            txtPhone.setText(tblPatients.getValueAt(row, 4).toString());
            cmbGender.setSelectedItem(tblPatients.getValueAt(row, 5).toString());

            // Enable Edit và Delete, Disable Add
            btnEdit.setEnabled(true);
            btnDelete.setEnabled(true);
            btnAdd.setEnabled(false);
        }
    }

    /**
     * Lấy thông tin bệnh nhân từ form
     */
    public Patient getPatientInfo() {
        try {
            String name = txtName.getText().trim();
            String dob = txtDob.getText().trim();
            String address = txtAddress.getText().trim();
            String phone = txtPhone.getText().trim();
            String gender = (String) cmbGender.getSelectedItem();

            if (name.isEmpty() || dob.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                throw new IllegalArgumentException("All fields except ID are required!");
            }

            Patient patient = new Patient();
            if (!txtId.getText().isEmpty()) {
                patient.setPatientID(Integer.parseInt(txtId.getText()));
            }
            patient.setName(name);
            patient.setDob(java.sql.Date.valueOf(dob)); // Chuyển String sang Date
            patient.setAddress(address);
            patient.setPhone(phone);
            patient.setGender(gender);

            return patient;
        } catch (Exception e) {
            showMessage(e.getMessage(), true);
            return null;
        }
    }

    public void clearPatientInfo() {
        txtId.setText("");
        txtName.setText("");
        txtDob.setText("");
        txtAddress.setText("");
        txtPhone.setText("");
        cmbGender.setSelectedIndex(0);

        // Enable Add, Disable Edit và Delete
        btnAdd.setEnabled(true);
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
    }


    public void addPatientAddListener(ActionListener listener) {
        btnAdd.addActionListener(listener);
    }

    public void addPatientEditListener(ActionListener listener) {
        btnEdit.addActionListener(listener);
    }

    public void addPatientDeleteListener(ActionListener listener) {
        btnDelete.addActionListener(listener);
    }

    public void addPatientClearListener(ActionListener listener) {
        btnClear.addActionListener(listener);
    }

    public void addSortByNameListener(ActionListener listener) {
        btnSortByName.addActionListener(listener);
    }

    public void addSortByAddressListener(ActionListener listener) {
        btnSortByAddress.addActionListener(listener);
    }

    public void addListPatientSelectionListener(ListSelectionListener listener) {
        tblPatients.getSelectionModel().addListSelectionListener(listener);
    }
}
