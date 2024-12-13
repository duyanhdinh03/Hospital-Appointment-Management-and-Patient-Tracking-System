package org.HospitalManagement.model;

public class Room {
    private int roomId ;
    private String roomName ;
    private int maxAppointmentsMorning;
    private int maxAppointmentsAfternoon;

    public Room(int roomId, String roomName, int maxAppointmentsMorning, int maxAppointmentsAfternoon) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.maxAppointmentsMorning = maxAppointmentsMorning;
        this.maxAppointmentsAfternoon = maxAppointmentsAfternoon;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getMaxAppointmentsMorning() {
        return maxAppointmentsMorning;
    }

    public void setMaxAppointmentsMorning(int maxAppointmentsMorning) {
        this.maxAppointmentsMorning = maxAppointmentsMorning;
    }

    public int getMaxAppointmentsAfternoon() {
        return maxAppointmentsAfternoon;
    }

    public void setMaxAppointmentsAfternoon(int maxAppointmentsAfternoon) {
        this.maxAppointmentsAfternoon = maxAppointmentsAfternoon;
    }
}
