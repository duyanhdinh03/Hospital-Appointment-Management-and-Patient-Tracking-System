package org.HospitalManagement.model;

public class Feedback {
    private int id;
    private int appointmentId;
    private int patientId;
    private int rating;
    private String comment;

    public Feedback(int id, int appointmentId, int patientId, int rating, String comment) {
        this.id = id;
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.rating = rating;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
