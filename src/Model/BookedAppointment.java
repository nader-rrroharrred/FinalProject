/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Masterz
 */
public class BookedAppointment {
    private int id;
    private int appointmentID;
    private int userID;
    private String status;
    private String doctorComment;
    

    public BookedAppointment() {
    }

    public BookedAppointment(int id, int appointmentID, int userID, String status, String doctorComment) {
        this.id = id;
        this.appointmentID = appointmentID;
        this.userID = userID;
        this.status = status;
        this.doctorComment = doctorComment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDoctorComment() {
        return doctorComment;
    }

    public void setDoctorComment(String doctorComment) {
        this.doctorComment = doctorComment;
    }

    public void save(Connection connection) throws SQLException {
        String sql = "INSERT INTO booked_appointments (appointment_id, user_id, status, doctor_commnet)"
                + "VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, appointmentID);
        statement.setInt(2, userID);
        statement.setString(3, status);
        statement.setString(4, doctorComment);
        statement.executeUpdate();
    }

}
