package org.HospitalManagement.controller;

import org.HospitalManagement.dao.AppointmentDAO;
import org.HospitalManagement.view.patient.AppointmentList;
import org.HospitalManagement.view.patient.PatientView;
import org.HospitalManagement.model.Appointment;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PatientController {
    private final PatientView patientView;
    private final AppointmentDAO appointmentDAO;
    private final int patientId;

    public PatientController(PatientView patientView, int patientId) {
        this.patientView = patientView;
        this.appointmentDAO = new AppointmentDAO();
        this.patientId = patientId;
        initialize();
    }

    private void initialize() {
        //  Xem lịch khám
        patientView.addViewAppointmentsListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Appointment> appointments = appointmentDAO.getAppointmentsByPatientId(patientId);
                if(appointments.isEmpty()) {
                    JOptionPane.showMessageDialog(patientView,"Hiện tại không có lịch khám nào!");
                }
                else {
                    AppointmentList appointmentList = new AppointmentList(appointments);
                    appointmentList.setVisible(true);
                }
            }
        });

        // Lắng nghe sự kiện khi nhấn nút Đổi mật khẩu
        patientView.addChangePasswordListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mở cửa sổ đổi mật khẩu
                JOptionPane.showMessageDialog(patientView, "Chức năng đổi mật khẩu đang phát triển!");
            }
        });

        // Lắng nghe sự kiện khi nhấn nút Gửi đơn đăng ký lịch khám
        patientView.addSubmitAppointmentListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mở cửa sổ gửi đơn đăng ký lịch khám
                JOptionPane.showMessageDialog(patientView, "Chức năng gửi đơn đăng ký lịch khám đang phát triển!");
            }
        });

        // Lắng nghe sự kiện khi nhấn nút Hủy / Dời lịch hẹn
        patientView.addCancelOrRescheduleListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mở cửa sổ hủy/dời lịch hẹn
                JOptionPane.showMessageDialog(patientView, "Chức năng hủy/dời lịch hẹn đang phát triển!");
            }
        });

        // Lắng nghe sự kiện khi nhấn nút Xem lịch sử đăng ký lịch khám
        patientView.addViewHistoryListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Hiển thị lịch sử các đơn đăng ký lịch khám
                JOptionPane.showMessageDialog(patientView, "Đang hiển thị lịch sử các đơn đăng ký!");
            }
        });
    }

    private void loadAppointments() {
        List<Appointment> appointments = (List<Appointment>) appointmentDAO.getAppointmentsByPatientId(patientId);
        patientView.updateAppointmentsTable(appointments);
    }

    public void showPatientView() {
        patientView.setVisible(true);
    }


}