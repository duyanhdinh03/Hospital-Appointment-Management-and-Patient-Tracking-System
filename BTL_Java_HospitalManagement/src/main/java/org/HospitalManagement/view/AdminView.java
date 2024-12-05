package org.HospitalManagement.view;

import javax.swing.*;
import java.awt.*;

public class AdminView extends JFrame {
    private JButton btnLogout, btnChangePassword, btnManageUsers, btnStatistics, btnReport, btnProcessRequests, btnFeedback;

    public AdminView() {
        setTitle("Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Navigation Panel
        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.Y_AXIS));

        btnLogout = new JButton("Logout");
        btnChangePassword = new JButton("Change Password");
        btnManageUsers = new JButton("Manage Users");
        btnStatistics = new JButton("Statistics");
        btnReport = new JButton("Report");
        btnProcessRequests = new JButton("Process Requests");
        btnFeedback = new JButton("Feedback for Doctors");

        navigationPanel.add(btnLogout);
        navigationPanel.add(btnChangePassword);
        navigationPanel.add(btnManageUsers);
        navigationPanel.add(btnStatistics);
        navigationPanel.add(btnReport);
        navigationPanel.add(btnProcessRequests);
        navigationPanel.add(btnFeedback);

        add(navigationPanel, BorderLayout.WEST);

        setVisible(true);
    }

    // Getters for buttons to add ActionListeners
    public JButton getBtnLogout() {
        return btnLogout;
    }

    public JButton getBtnChangePassword() {
        return btnChangePassword;
    }

    public JButton getBtnManageUsers() {
        return btnManageUsers;
    }

    public JButton getBtnStatistics() {
        return btnStatistics;
    }

    public JButton getBtnReport() {
        return btnReport;
    }

    public JButton getBtnProcessRequests() {
        return btnProcessRequests;
    }

    public JButton getBtnFeedback() {
        return btnFeedback;
    }
}

