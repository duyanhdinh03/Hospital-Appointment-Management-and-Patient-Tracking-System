package org.HospitalManagement.dao;


import org.HospitalManagement.model.Patient;
import org.HospitalManagement.model.PatientXML;
import org.HospitalManagement.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class PatientDAO {
    private static final String FILE_PATH = "patients.xml";
    private List<Patient> listPatients;

    public PatientDAO() {
        this.listPatients = readListPatients();
        if(listPatients == null){
            listPatients = new ArrayList<>();
        }
    }

    //Lưu vào file patients.xml
    public void writeListPatients(List<Patient> listPatients) {
        PatientXML patientXML = new PatientXML();
        patientXML.setPatient(listPatients);
        FileUtils.writeToXML(FILE_PATH, patientXML);
    }

    //Đọc từ file patients.xml
    private List<Patient> readListPatients() {
        PatientXML patientXML = (PatientXML) FileUtils.readFromXML(
                FILE_PATH, PatientXML.class);
        if(patientXML != null){
            return patientXML.getPatient();
        }
        return new ArrayList<>();
    }

    //Thêm patient vào list và lưu vào file
    public void add(Patient patient) {
        int id = listPatients.isEmpty() ? 1 : listPatients.size() +1 ;
        patient.setPatientID(id);
        listPatients.add(patient);
        writeListPatients(listPatients);
    }

    //Cập nhật patient trong list và lưu vào file
    public void edit(Patient patient) {
        for(int i = 0; i < listPatients.size(); i++){
            if(listPatients.get(i).getPatientID() == patient.getPatientID()){
                listPatients.set(i, patient);
                writeListPatients(listPatients);
                break;
            }
        }
    }

    //Xóa patient từ list và lưu lại vào file

    public boolean delete(Patient patient) {
        boolean removed = listPatients.removeIf(p -> p.getPatientID() == patient.getPatientID());
        if (removed) {
            writeListPatients(listPatients); // Cập nhật lại vào file
        }
        return removed;
    }


    public List<Patient> getListPatients() {
        return listPatients;
    }

    public void setListPatients(List<Patient> listPatients) {
        this.listPatients = listPatients;
    }

}
