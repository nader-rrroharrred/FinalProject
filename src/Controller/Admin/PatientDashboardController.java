/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Admin;

import Model.Appointment;

import Model.DB;
import Model.Functions;
import View.ViewManager;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Masterz
 */
public class PatientDashboardController implements Initializable {

    @FXML
    private TableColumn<Appointment, LocalDate> appointmentDate;
    @FXML
    private TableColumn<Appointment, String> appointmentDay;
    @FXML
    private TableColumn<Appointment, String> appointmentTime;
    @FXML
    private TableColumn<Appointment, String> appointmentStatus;
    @FXML
    private TableView<Appointment> appointmentTableView;
    @FXML
    private TableColumn<Appointment, Integer> appointmentID;
    @FXML
    private Button bookNowButton;
    @FXML
    private Button myAppointmentButton;
    @FXML
    private Button homeButton;
    @FXML
    private Button logOutButton1;
    @FXML
    private Button availableAppointments;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void bookNowButtonAction(ActionEvent event) throws SQLException {
        Appointment selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
        Appointment UpdatedStatus = new Appointment();
        if (selectedAppointment == null) {
            Functions.ErrorAlert();
            return;
        }
        java.sql.Connection connection = DB.getInstance().getConnection();
        String insertSql = "INSERT INTO booked_appointments (appointment_id, user_id, status) VALUES (?, ?, ?)";
        PreparedStatement insertStmt = connection.prepareStatement(insertSql);
        insertStmt.setInt(1, selectedAppointment.getId());
        insertStmt.setInt(2, PatientLoginController.currentUser.getId());
        insertStmt.setString(3, "Waiting");
        int rowsInserted = insertStmt.executeUpdate();
        appointmentTableView.refresh();
        if (rowsInserted > 0) {
            String updateSql = "UPDATE appointments SET status = 'Booked' WHERE id = ?";
            PreparedStatement updateStmt = connection.prepareStatement(updateSql);
            updateStmt.setInt(1, selectedAppointment.getId());
            int rowsUpdated = updateStmt.executeUpdate();
            if (rowsUpdated > 0) {
                selectedAppointment.setStatus("Booked");
                appointmentTableView.refresh();
                Functions.SuccessAlert();
                appointmentTableView.getItems().remove(selectedAppointment);
            } else {
                Functions.ErrorAlert();
            }
        } else {
            Functions.ErrorAlert();
        }
    }

    @FXML
    private void logOutButtonAction(ActionEvent event) {
        ViewManager.switcher.openLogin();

    }

    @FXML
    private void myAppointmentButtonAction(ActionEvent event) {
        ViewManager.switcher.openMyAppointments();

    }

    @FXML
    private void homeButtonAction(ActionEvent event) {
        ViewManager.switcher.openPatientDashboard();
    }

    @FXML
    private void availableAppointmentsAction(ActionEvent event) {
        try {
            Connection connection = DB.getInstance().getConnection();
            String sql = "SELECT * FROM appointments WHERE status = 'free'";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            ObservableList<Appointment> appointments = FXCollections.observableArrayList();
            while (rs.next()) {
                int id = rs.getInt("id");
                LocalDate date = rs.getDate("appointment_date").toLocalDate();
                String day = rs.getString("appointment_day");
                String time = rs.getString("appointment_time");
                String status = rs.getString("status");
                Appointment appointment = new Appointment(date, day, time, status);
                appointment.setId(id);
                appointments.add(appointment);
            }
            appointmentID.setCellValueFactory(new PropertyValueFactory<>("id"));
            appointmentDate.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
            appointmentDay.setCellValueFactory(new PropertyValueFactory<>("appointmentDay"));
            appointmentTime.setCellValueFactory(new PropertyValueFactory<>("appointmentTime"));
            appointmentStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

            appointmentTableView.setItems(appointments);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
