package org.HospitalManagement.controller;

import org.HospitalManagement.dao.PatientDAO;
import org.HospitalManagement.model.Patient;
import org.HospitalManagement.view.DoctorView;

import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DoctorController {
    private PatientDAO patientDAO;
    private DoctorView doctorView;

    public DoctorController(DoctorView doctorView) {
        this.doctorView = doctorView;
        this.patientDAO = new PatientDAO();

        // Đăng ký các listener cho các hành động từ DoctorView
        doctorView.addPatientAddListener(new AddPatientListener());
        doctorView.addPatientEditListener(new EditPatientListener());
        doctorView.addPatientDeleteListener(new DeletePatientListener());
        doctorView.addPatientClearListener(new ClearPatientListener());
        doctorView.addListPatientSelectionListener(new ListPatientSelectionListener());
    }

    // Hiển thị danh sách bệnh nhân lên giao diện
    public void showPatientList() {
        List<Patient> patientList = patientDAO.getListPatients();
        doctorView.setVisible(true);
        doctorView.showPatientList(patientList);
    }

    // Listener cho nút "Add"
    class AddPatientListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Patient patient = doctorView.getPatientInfo();
            if (patient != null) {
                patientDAO.add(patient); // Thay đổi phương thức gọi từ DAO
                doctorView.showPatient(patient);
                doctorView.showPatientList(patientDAO.getListPatients());
                doctorView.showMessage("Thêm thành công!!", false);
            } else {
                doctorView.showMessage("Thông tin bệnh nhân không hợp lệ!", true);
            }
        }
    }

    // Listener cho nút "Edit"
    class EditPatientListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Patient patient = doctorView.getPatientInfo();
            if (patient != null) {
                patientDAO.edit(patient); // Thay đổi phương thức gọi từ DAO
                doctorView.showPatientList(patientDAO.getListPatients());
                doctorView.showMessage("Cập nhật thành công!", false);
            } else {
                doctorView.showMessage("Không thể cập nhật. Vui lòng chọn một bệnh nhân!", true);
            }
        }
    }

    // Listener cho nút "Delete"
    class DeletePatientListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Patient patient = doctorView.getPatientInfo();
            if (patient != null) {
                boolean success = patientDAO.delete(patient); // Thay đổi phương thức gọi từ DAO
                if (success) {
                    doctorView.clearPatientInfo();
                    doctorView.showPatientList(patientDAO.getListPatients());
                    doctorView.showMessage("Xóa thành công!!", false);
                } else {
                    doctorView.showMessage("Xóa thất bại. Không tìm thấy bệnh nhân!", true);
                }
            } else {
                doctorView.showMessage("Vui lòng chọn một bệnh nhân để xóa!", true);
            }
        }
    }

    // Listener cho nút "Clear"
    class ClearPatientListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            doctorView.clearPatientInfo();
        }
    }

    // Listener cho việc chọn bệnh nhân từ danh sách
    class ListPatientSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(javax.swing.event.ListSelectionEvent e) {
            doctorView.fillPatientFromSelectedRow();
        }
    }
}
