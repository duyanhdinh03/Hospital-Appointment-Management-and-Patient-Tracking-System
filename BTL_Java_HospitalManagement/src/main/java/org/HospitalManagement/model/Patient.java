package org.HospitalManagement.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


@XmlRootElement(name = "patient")
@XmlAccessorType(XmlAccessType.FIELD)
public class Patient implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int patientID;
    private String name;
    private byte age;
    private String address;
    private String phone;
    private Date dob;
    private String email;
    private String gender;
    private String medicalHistory;

    public Patient() {
    }

    public Patient(int patientID, String name, byte age, String address, String phone, Date dob, String email, String gender, String medicalHistory) {
        this.patientID = patientID;
        this.name = name;
        this.age = age;
        this.address = address;
        this.phone = phone;
        this.dob = dob;
        this.email = email;
        this.gender = gender;
        this.medicalHistory = medicalHistory;
    }


    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }
}
