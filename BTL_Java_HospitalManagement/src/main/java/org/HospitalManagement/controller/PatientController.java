package org.HospitalManagement.controller;

import org.HospitalManagement.dao.PatientDAO;
import org.HospitalManagement.model.Patient;
import org.HospitalManagement.view.DoctorView;

import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PatientController {
    private PatientDAO patientDAO;
    private DoctorView doctorView;

    public PatientController(DoctorView patientView) {
        this.doctorView = patientView;
        patientDAO = new PatientDAO();

        patientView.addPatientAddListener(new AddPatientListener());
        patientView.addPatientEditListener(new EditPatientListener());
        patientView.addPatientDeleteListener(new DeletePatientListener());
        patientView.addPatientClearListener(new ClearPatientListener());
        patientView.addSortByAddressListener(new SortPatientAddressListener());
        patientView.addSortByNameListener(new SortPatientNameListener());
        patientView.addListPatientSelectionListener((ListSelectionListener) new ListPatientSelectionListener());
    }

    public void showPatientView(){
        List<Patient> patientList = patientDAO.getListPatients();
        doctorView.setVisible(true);
        doctorView.showPatientList(patientList);
    }

    //Button Add

    class AddPatientListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            Patient patient = doctorView.getPatientInfo();
            if(patient != null){
                patientDAO.add(patient);
                doctorView.showPatient(patient);
                doctorView.showPatientList(patientDAO.getListPatients());
                doctorView.showMessage("Thêm thành công!!",false);
            }
        }
    }

    //Button Edit

    class EditPatientListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            Patient patient = doctorView.getPatientInfo();
            if (patient != null) {
                patientDAO.edit(patient);
                doctorView.showPatient(patient);
                doctorView.showPatientList(patientDAO.getListPatients());
                doctorView.showMessage("Cập nhật thành công!",false);
            }
        }
    }

    //Button Delete

    class DeletePatientListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
             Patient patient = doctorView.getPatientInfo();
             if (patient != null) {
                 patientDAO.delete(patient);
                 doctorView.clearPatientInfo();
                 doctorView.showPatientList(patientDAO.getListPatients());
                 doctorView.showMessage("Xóa thành công !!",false);
             }
        }
    }

    //Button Clear

    class ClearPatientListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            doctorView.clearPatientInfo();
        }
    }

    //Sort by address

    class SortPatientAddressListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            patientDAO.sortPatientsByAddress(patientDAO.getListPatients());
            doctorView.showPatientList(patientDAO.getListPatients());
        }
    }

    //Sort by name

    class SortPatientNameListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            patientDAO.sortPatientsByName(patientDAO.getListPatients());
            doctorView.showPatientList(patientDAO.getListPatients());
        }
    }

    class ListPatientSelectionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            doctorView.fillPatientFromSelectedRow();
        }
    }



}
