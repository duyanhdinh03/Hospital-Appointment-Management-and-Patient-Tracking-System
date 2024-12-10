package org.HospitalManagement.view.patient.feedback;

import org.HospitalManagement.view.patient.PatientView;

import javax.swing.*;
import java.awt.*;

public class FeedbackSelectionForm extends JFrame {
    private JButton appointmentFeedbackButton;
    private JButton generalFeedbackButton;
    private JButton backButton;

    public FeedbackSelectionForm(int patientId, PatientView patientView) {
        setTitle("Chọn loại đánh giá");
        setSize(600, 400);
        setLayout(new GridLayout(5, 2, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Nút đánh giá theo lịch hẹn
        appointmentFeedbackButton = new JButton("Đánh giá theo lịch hẹn");
        appointmentFeedbackButton.addActionListener(e -> {
            dispose(); // Đóng form này
            new AppointmentFeedbackForm(patientId, this).setVisible(true); // Mở form đánh giá theo lịch
        });

        // Nút đánh giá chung
        generalFeedbackButton = new JButton("Đánh giá chung");
        generalFeedbackButton.addActionListener(e -> {
            dispose(); // Đóng form này
            new GeneralFeedbackForm(patientId, this).setVisible(true); // Mở form đánh giá chung
        });

        // Nút quay lại
        backButton = new JButton("Quay lại");
        backButton.addActionListener(e -> {
            dispose(); // Đóng form này
            patientView.setVisible(true); // Quay lại PatientView
        });

        add(appointmentFeedbackButton);
        add(generalFeedbackButton);
        add(backButton);
    }
}
