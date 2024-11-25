package org.HospitalManagement.dao;

import org.HospitalManagement.model.Patient;
import org.HospitalManagement.model.PatientXML;
import org.HospitalManagement.utils.FileUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PatientDAO {
    private static final String PATIENT_FILE_NAME = "patients.xml"; // Tên file lưu trữ danh sách bệnh nhân
    private List<Patient> listPatients;

    public PatientDAO() {
        this.listPatients = readListPatients();
        if (listPatients == null) {
            listPatients = new ArrayList<>();
        }
    }

    //Lưu danh sách vào file xml
    public void writeListPatients(List<Patient> patients) {
        PatientXML patientXML = new PatientXML();
        patientXML.setPatients(patients);
        FileUtils.writeXMLtoFile(PATIENT_FILE_NAME, patientXML);
    }


    //Đọc danh sách từ file xml
    public List<Patient> readListPatients() {
        List<Patient> list = new ArrayList<>();
        PatientXML patientXML = (PatientXML) FileUtils.readXMLFile(
                PATIENT_FILE_NAME, PatientXML.class);
        if (patientXML != null) {
            list = patientXML.getPatients();
        }
        return list;
    }

    //Thêm vào danh sách
    public void add(Patient patient) {
        int id = 1;
        if (listPatients != null && !listPatients.isEmpty()) {
            id = listPatients.size() + 1;
        }
        patient.setPatientID(id);
        listPatients.add(patient);
        writeListPatients(listPatients);
    }

    //Cập nhật danh sách
    public void edit(Patient patient) {
        int size = listPatients.size();
        for (int i = 0; i < size; i++) {
            if (listPatients.get(i).getPatientID() == patient.getPatientID()) {
                listPatients.get(i).setName(patient.getName());
                listPatients.get(i).setAddress(patient.getAddress());
                listPatients.get(i).setPhone(patient.getPhone());
                listPatients.get(i).setDob(patient.getDob());
                listPatients.get(i).setGender(patient.getGender());
                writeListPatients(listPatients);
                break;
            }
        }
    }

    //Xóa khỏi danh sách và lưu danh sách vào file
    public boolean delete(Patient patient) {
        boolean isFound = false;
        int size = listPatients.size();
        for (Patient listPatient : listPatients) {
            if (listPatient.getPatientID() == patient.getPatientID()) {
                patient = listPatient;
                isFound = true;
                break;
            }
        }
        if (isFound) {
            listPatients.remove(patient);
            writeListPatients(listPatients);
            return true;
        }
        return false;
    }

    /**
     * Sắp xếp danh sách bệnh nhân theo tên theo thứ tự tăng dần
     */
    public void sortPatientsByName(List<Patient> listPatients) {
        for (int i = 0; i < listPatients.size() - 1; i++) {
            for (int j = i + 1; j < listPatients.size(); j++) {
                // So sánh tên của hai bệnh nhân
                if (listPatients.get(i).getName().compareTo(listPatients.get(j).getName()) > 0) {
                    // Hoán đổi nếu thứ tự không đúng
                    Patient temp = listPatients.get(i);
                    listPatients.set(i, listPatients.get(j));
                    listPatients.set(j, temp);
                }
            }
        }
    }

    /**
     * Sắp xếp danh sách bệnh nhân theo địa chỉ
     */
    public void sortPatientsByAddress(List<Patient> listPatients) {
        for (int i = 0; i < listPatients.size() - 1; i++) {
            for (int j = i + 1; j < listPatients.size(); j++) {
                // So sánh địa chỉ của hai bệnh nhân
                if (listPatients.get(i).getAddress().compareTo(listPatients.get(j).getAddress()) > 0) {
                    // Hoán đổi nếu thứ tự không đúng
                    Patient temp = listPatients.get(i);
                    listPatients.set(i, listPatients.get(j));
                    listPatients.set(j, temp);
                }
            }
        }
    }

    public List<Patient> getListPatients() {
        return listPatients;
    }

    public void setListPatients(List<Patient> listPatients) {
        this.listPatients = listPatients;
    }
}
