package org.HospitalManagement.controller;

import org.HospitalManagement.dao.AppointmentDAO;
import org.HospitalManagement.view.ChangePassword;
import org.HospitalManagement.view.LoginView;
import org.HospitalManagement.view.doctor.HistoryView;
import org.HospitalManagement.view.doctor.appove.AppointmentApprovalView;
import org.HospitalManagement.view.doctor.manager.AppointmentManagementView;
import org.HospitalManagement.view.doctor.DoctorView;
import org.HospitalManagement.view.doctor.staticsic.StatisticsView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DoctorController {
    private final DoctorView doctorView;
    private final AppointmentDAO appointmentDAO;
    private final int doctorId;

    public DoctorController(DoctorView doctorView, int doctorId) {
        this.doctorView = doctorView;
        this.appointmentDAO =  new AppointmentDAO();
        this.doctorId = doctorId ;
        initialize();
    }

    private void initialize() {
        // Quan ly danh sach lich hen
        doctorView.addViewAppointmentManagementListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doctorView.setVisible(false);

                AppointmentManagementView mngView = new AppointmentManagementView(doctorId);
                mngView.setVisible(true);
            }
        });

        //xem lich su lich hen
        doctorView.addViewHistoryListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doctorView.setVisible(false);
                HistoryView historyView = new HistoryView(doctorId, doctorView);
                historyView.setVisible(true);
            }
        });

        //Duyet yeu cau lich hen
        doctorView.addViewRequestListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppointmentApprovalView approvalView = new AppointmentApprovalView(doctorId);
                approvalView.setVisible(true);
            }
        });


        //xem thong ke
        doctorView.addViewStatisticalListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StatisticsView statisticsView = new StatisticsView(doctorId);
                statisticsView.setVisible(true);
                doctorView.dispose();
            }
        });

        //Doi mau khau
        doctorView.changePasswordListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doctorView.dispose();
                ChangePassword changePassword = new ChangePassword(doctorId, doctorView);
                changePassword.setVisible(true);
            }
        });

        //dang xuat
        doctorView.LogoutListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doctorView.dispose();

                LoginView newloginView = new LoginView();
                LoginController loginController = new LoginController(newloginView);
                newloginView.setVisible(true);
                newloginView.resetFields();

                newloginView.addLoginListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String username = newloginView.getUsername();
                        String password = newloginView.getPassword();

                        String result = loginController.handleLogin(username, password);

                        if (result.equals("SUCCESS")) {
                            newloginView.dispose();
                        } else {
                            JOptionPane.showMessageDialog(newloginView, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

            }
        });
    }

    public void showDoctorView() {
        doctorView.setVisible(true);
    }
}
