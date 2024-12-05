package org.HospitalManagement.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PatientView extends JFrame {
    private JPanel contentPanel;
    private JMenuBar menuBar;
    private JTable appointmentTable;
    private JLabel loginInfoLabel;
    private JPanel buttonPanel;
    private JPanel dataGridPanel;

    public PatientView() {
        // Setup main window
        setTitle("Patient Management");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Setup the navigation bar (Menu)
        setupMenuBar();

        // Display login info (role)
        displayLoginInfo();

        // Main content panel for buttons and data grid
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        // Setup function buttons (main operations)
        setupFunctionButtons();

        // Add the content panel to the frame
        add(contentPanel, BorderLayout.CENTER);
    }

    private void setupMenuBar() {
        menuBar = new JMenuBar();

        // Menu
        JMenu menu = new JMenu("Chức năng");
        JMenuItem changePasswordItem = new JMenuItem("Đổi mật khẩu");
        changePasswordItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Show change password dialog or form
                showChangePasswordDialog();
            }
        });

        JMenuItem logoutItem = new JMenuItem("Đăng xuất");
        logoutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle logout logic here
                logout();
            }
        });

        menu.add(changePasswordItem);
        menu.add(logoutItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    private void displayLoginInfo() {
        // Show user role (currently logged in)
        loginInfoLabel = new JLabel("Bạn đang đăng nhập với quyền: Bệnh nhân", JLabel.CENTER);
        loginInfoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(loginInfoLabel, BorderLayout.NORTH);
    }

    private void setupFunctionButtons() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        // Buttons for main operations
        JButton viewAppointmentsButton = new JButton("Xem lịch khám");
        JButton bookAppointmentButton = new JButton("Đặt lịch khám");
        JButton cancelOrRescheduleButton = new JButton("Gửi yêu cầu hủy/dời lịch");
        JButton rateDoctorButton = new JButton("Đánh giá bác sĩ");

        buttonPanel.add(viewAppointmentsButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between buttons
        buttonPanel.add(bookAppointmentButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(cancelOrRescheduleButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(rateDoctorButton);

        // Add the button panel to the content panel
        contentPanel.add(buttonPanel, BorderLayout.WEST);

        // Add action listeners for buttons
        viewAppointmentsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAppointments();
            }
        });

        bookAppointmentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bookAppointment();
            }
        });

        cancelOrRescheduleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleCancelOrReschedule();
            }
        });

        rateDoctorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rateDoctor();
            }
        });
    }

    private void setupAppointmentTablePanel() {
        // Create a panel to hold the data grid (JTable)
        dataGridPanel = new JPanel();
        dataGridPanel.setLayout(new BorderLayout());

        // Set the preferred size for the table panel to avoid stretching
        dataGridPanel.setPreferredSize(new Dimension(650, 500));  // Adjust height if needed

        // Set up table to display appointment data
        String[] columnNames = {"Mã Lịch", "Bác Sĩ", "Ngày Giờ", "Trạng Thái"};
        Object[][] data = {};  // Data will be fetched from the database
        appointmentTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(appointmentTable);

        dataGridPanel.add(scrollPane, BorderLayout.CENTER);

        // Add the data grid panel to the content panel on the right side
        contentPanel.add(dataGridPanel, BorderLayout.CENTER);
    }

    private void showAppointments() {
        // Logic to fetch and display appointments in the data grid
        JOptionPane.showMessageDialog(this, "Hiển thị lịch khám");
    }

    private void bookAppointment() {
        // Show popup to book a new appointment
        JOptionPane.showMessageDialog(this, "Hiển thị form đặt lịch khám");
    }

    private void handleCancelOrReschedule() {
        // Show form to cancel or reschedule an appointment
        JOptionPane.showMessageDialog(this, "Hiển thị form hủy/dời lịch khám");
    }

    private void rateDoctor() {
        // Show form to rate a doctor
        JOptionPane.showMessageDialog(this, "Hiển thị form đánh giá bác sĩ");
    }

    private void showChangePasswordDialog() {
        // Logic to show a password change dialog/form
        JOptionPane.showMessageDialog(this, "Hiển thị form đổi mật khẩu");
    }

    private void logout() {
        // Handle logout logic (e.g., return to login screen)
        JOptionPane.showMessageDialog(this, "Đăng xuất thành công");
        // You can add logic to go back to the login screen
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PatientView().setVisible(true));
    }
}



