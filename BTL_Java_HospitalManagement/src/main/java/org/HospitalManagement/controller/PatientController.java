package org.HospitalManagement.controller;

import org.HospitalManagement.dao.AppointmentDAO;
import org.HospitalManagement.dao.UserDAO;
import org.HospitalManagement.view.ChangePassword;
import org.HospitalManagement.view.LoginView;
import org.HospitalManagement.view.patient.*;
import org.HospitalManagement.model.Appointment;
import org.HospitalManagement.view.patient.feedback.FeedbackSelectionForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        //  Đổi mật khẩu
        patientView.addChangePasswordListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                patientView.setVisible(false);
                ChangePassword changePassword = new ChangePassword(patientId, patientView);
                changePassword.setVisible(true);
            }
        });

        // Gửi đơn đăng ký lịch khám
        patientView.addSubmitAppointmentListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Hiển thị form đăng ký lịch khám
                SubmitForm submitForm = new SubmitForm(patientView);
                submitForm.addSubmitListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        // Lấy patientId từ PatientView
                        int patientId = patientView.getPatientId();
                        Appointment appointment = submitForm.getAppointmentData(patientId);

                        if (appointment == null) {
                            JOptionPane.showMessageDialog(submitForm, "Thông tin không hợp lệ!");
                            return;
                        }

                        boolean isInserted = appointmentDAO.createAppointment(appointment);
                        if (isInserted) {
                            JOptionPane.showMessageDialog(submitForm, "Đăng ký lịch khám thành công!");
                            submitForm.dispose();
                        } else {
                            JOptionPane.showMessageDialog(submitForm, "Đăng ký lịch khám thất bại!");
                        }
                    }
                });

                submitForm.setVisible(true);
            }
        });

        // Gửi đánh giá
        patientView.sendFeedbackListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                patientView.setVisible(false);
                FeedbackSelectionForm selectionForm = new FeedbackSelectionForm(patientId, patientView);
                selectionForm.setVisible(true);
            }
        });

        //  Xem lịch sử đăng ký lịch khám
        patientView.addViewHistoryListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                patientView.setVisible(false);
                AppointmentHistory appointmentHistory = new AppointmentHistory(patientId, patientView);
                appointmentHistory.setVisible(true);
            }
        });

        patientView.LogoutListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                patientView.dispose();

                LoginView newloginView = new LoginView();
                LoginController loginController = new LoginController(newloginView);
                newloginView.resetFields();

                newloginView.addLoginListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String username = newloginView.getUsername();
                        String password = newloginView.getPassword();

                        String result = LoginController.handleLogin(username, password);

                        if (result.equals("SUCCESS")) {
                            newloginView.dispose();
                        } else {
                            JOptionPane.showMessageDialog(newloginView, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                newloginView.setVisible(true);
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
