package org.HospitalManagement.view.patient;

import org.HospitalManagement.model.Appointment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import com.toedter.calendar.JDateChooser;

public class SubmitForm extends JDialog {
    private JDateChooser txtAppointmentDate;
    private JTextArea txtSymptoms;
    private JTextArea txtNotes;
    private JButton btnSubmit;
    private JButton btnCancel;
    private int patientId;

    public SubmitForm(JFrame parent) {
        super(parent, "Đăng ký lịch khám", true);
        setLayout(new BorderLayout());
        setSize(600, 400);
        setLocationRelativeTo(parent);

        //Main panel
        JPanel mainPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        mainPanel.add(new JLabel("Ngày mong muốn khám (yyyy-MM-dd):"));
        txtAppointmentDate = new JDateChooser();
        mainPanel.add(txtAppointmentDate);

        mainPanel.add(new JLabel("Mô tả triệu chứng:"));
        txtSymptoms = new JTextArea(3, 20);
        JScrollPane symptomsScroll = new JScrollPane(txtSymptoms);
        mainPanel.add(symptomsScroll);

        mainPanel.add(new JLabel("Ghi chú (tùy chọn):"));
        txtNotes = new JTextArea(2, 20);
        JScrollPane notesScroll = new JScrollPane(txtNotes);
        mainPanel.add(notesScroll);

        //Button
        JPanel buttonPanel = new JPanel();
        btnSubmit = new JButton("Gửi đăng ký");
        btnCancel = new JButton("Hủy");
        buttonPanel.add(btnSubmit);
        buttonPanel.add(btnCancel);


        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        btnCancel.addActionListener(e -> dispose());

    }


    public String getAppointmentDate() {
        return txtAppointmentDate.getDate() != null ? txtAppointmentDate.getDate().toString() : "";
    }

    public String getSymptoms() {
        return txtSymptoms.getText().trim();
    }

    public String getNotes() {
        return txtNotes.getText().trim();
    }

    public void addSubmitListener(ActionListener listener) {
        btnSubmit.addActionListener(listener);
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public Appointment getAppointmentData(int patientId) {
        Appointment appointment = new Appointment();
        try {
            String symptoms = txtSymptoms.getText().trim();
            java.util.Date selectedDate = txtAppointmentDate.getDate();

            if (selectedDate == null) {
                throw new IllegalArgumentException("Vui lòng chọn ngày hẹn.");
            }

            java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());

            appointment.setPatientId(patientId);
            appointment.setAppointmentDate(sqlDate);
            appointment.setSymptoms(symptoms);
            appointment.setStatus("PENDING");
            appointment.setDoctorId(null);

            return appointment;

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
