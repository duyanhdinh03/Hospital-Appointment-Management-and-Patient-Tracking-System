package org.HospitalManagement.view.patient.feedback;

import org.HospitalManagement.dao.FeedbackDAO;

import javax.swing.*;
import java.awt.*;

public class GeneralFeedbackForm extends JFrame {
    private JSpinner qualityRatingSpinner, serviceRatingSpinner;
    private JTextArea commentsArea;
    private JButton submitButton, cancelButton;

    public GeneralFeedbackForm(int patientId, FeedbackSelectionForm selectionForm) {
        setTitle("Đánh giá chung");
        setSize(400, 300);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Đánh giá chất lượng (1-5):"));
        qualityRatingSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        add(qualityRatingSpinner);

        add(new JLabel("Đánh giá dịch vụ (1-5):"));
        serviceRatingSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        add(serviceRatingSpinner);

        add(new JLabel("Ý kiến:"));
        commentsArea = new JTextArea(3, 20);
        add(new JScrollPane(commentsArea));

        submitButton = new JButton("Xác nhận");
        cancelButton = new JButton("Hủy");
        add(submitButton);
        add(cancelButton);

        submitButton.addActionListener(e -> {
            int qualityRating = (int) qualityRatingSpinner.getValue();
            int serviceRating = (int) serviceRatingSpinner.getValue();
            String comments = commentsArea.getText();

            FeedbackDAO feedbackDAO = new FeedbackDAO();
            boolean success = feedbackDAO.addGeneralFeedback(patientId, qualityRating, serviceRating, comments);
            if (success) {
                JOptionPane.showMessageDialog(this, "Đánh giá thành công!");
                dispose();
                selectionForm.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Đánh giá thất bại!");
            }
        });

        cancelButton.addActionListener(e -> {
            dispose();
            selectionForm.setVisible(true);
        });

        add(submitButton);
        add(cancelButton);
    }
}
