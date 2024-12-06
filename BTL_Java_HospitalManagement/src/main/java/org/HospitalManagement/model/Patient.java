package org.HospitalManagement.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Patient implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int patientID;
    private int userId;
    private String name;
    private byte age;
    private String address;
    private String phone;
    private LocalDate dob;
    private String email;
    private String gender;
    private List<Appointment> appointmentHistory;

    public Patient() {
    }

    public Patient(int patientID, int userId, String name, byte age, String address, String phone, LocalDate dob, String email, String gender, List<Appointment> appointmentHistory) {
        this.patientID = patientID;
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.address = address;
        this.phone = phone;
        this.dob = dob;
        this.email = email;
        this.gender = gender;
        this.appointmentHistory = appointmentHistory;
    }


    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
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


    public List<Appointment> getAppointmentHistory() {
        return appointmentHistory;
    }

    public void setAppointmentHistory(List<Appointment> appointmentHistory) {
        this.appointmentHistory = appointmentHistory;
    }
}
