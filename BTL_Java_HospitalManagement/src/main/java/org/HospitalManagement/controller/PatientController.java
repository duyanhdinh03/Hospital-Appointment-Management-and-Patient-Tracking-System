package org.HospitalManagement.controller;

import org.HospitalManagement.dao.PatientDAO;
import org.HospitalManagement.model.Patient;
import org.HospitalManagement.view.PatientView;

import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PatientController {
    private PatientDAO patientDAO;
    private PatientView patientView;

    public PatientController(PatientView patientView) {
        this.patientView = patientView;
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
        patientView.setVisible(true);
        patientView.showPatientList(patientList);
    }

    //Button Add

    class AddPatientListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            Patient patient = patientView.getPatientInfo();
            if(patient != null){
                patientDAO.add(patient);
                patientView.showPatient(patient);
                patientView.showPatientList(patientDAO.getListPatients());
                patientView.showMessage("Thêm thành công!!",false);
            }
        }
    }

    //Button Edit

    class EditPatientListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            Patient patient = patientView.getPatientInfo();
            if (patient != null) {
                patientDAO.edit(patient);
                patientView.showPatient(patient);
                patientView.showPatientList(patientDAO.getListPatients());
                patientView.showMessage("Cập nhật thành công!",false);
            }
        }
    }

    //Button Delete

    class DeletePatientListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
             Patient patient = patientView.getPatientInfo();
             if (patient != null) {
                 patientDAO.delete(patient);
                 patientView.clearPatientInfo();
                 patientView.showPatientList(patientDAO.getListPatients());
                 patientView.showMessage("Xóa thành công !!",false);
             }
        }
    }

    //Button Clear

    class ClearPatientListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            patientView.clearPatientInfo();
        }
    }

    //Sort by address

    class SortPatientAddressListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            patientDAO.sortPatientsByAddress(patientDAO.getListPatients());
            patientView.showPatientList(patientDAO.getListPatients());
        }
    }

    //Sort by name

    class SortPatientNameListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            patientDAO.sortPatientsByName(patientDAO.getListPatients());
            patientView.showPatientList(patientDAO.getListPatients());
        }
    }

    class ListPatientSelectionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            patientView.fillPatientFromSelectedRow();
        }
    }



}
