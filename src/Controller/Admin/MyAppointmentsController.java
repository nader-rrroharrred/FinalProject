/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Admin;

import Model.DB;

import Model.MyAppointments;
import View.ViewManager;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Masterz
 */
public class MyAppointmentsController implements Initializable {

    @FXML
    private TableView<MyAppointments> myAppointment;
    @FXML
    private TableColumn<MyAppointments, Integer> bookedID;
    @FXML
    private TableColumn<MyAppointments, String> firstNAME;
    @FXML
    private TableColumn<MyAppointments, String> lastNAME;
    @FXML
    private TableColumn<MyAppointments, String> appointmentDATE;
    @FXML
    private TableColumn<MyAppointments, String> appointmentTIME;
    @FXML
    private TableColumn<MyAppointments, String> doctorCOMMENT;
    @FXML
    private Button homeButton;
    @FXML
    private Button myAppointmentButton;
    @FXML
    private Button logOutButton;
    @FXML
    private Button show;
    ObservableList<MyAppointments> appointments = FXCollections.observableArrayList();
    MyAppointments iAppointments = new MyAppointments();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void homeButtonAction(ActionEvent event) {
        ViewManager.switcher.openPatientDashboard();

    }

    @FXML
    private void myAppointmentButtonAction(ActionEvent event) {
        ViewManager.switcher.openMyAppointments();
    }

    @FXML
    private void logOutButtonAction(ActionEvent event) {
        appointments.clear();
        ViewManager.switcher.openLogin();
    }

    @FXML
    private void showAction(ActionEvent event) {
        try (Connection connection = DB.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT booked_appointments.id, users.firstname, users.lastname, appointments.appointment_date, appointments.appointment_day, appointments.appointment_time, booked_appointments.doctor_comment "
                        + "FROM booked_appointments "
                        + "INNER JOIN users ON booked_appointments.user_id = users.id "
                        + "INNER JOIN appointments ON appointments.id = booked_appointments.appointment_id "
                        + "WHERE users.username = ?"
                )) {

            statement.setString(1, PatientLoginController.currentUser.getUsername());
            ResultSet rs = statement.executeQuery();

            appointments = FXCollections.observableArrayList();
            while (rs.next()) {
                int id = rs.getInt("id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String date = rs.getString("appointment_date");
                String time = rs.getString("appointment_time");
                String comment = rs.getString("doctor_comment");
                iAppointments = new MyAppointments();
                iAppointments.setId(id);
                iAppointments.setFirstName(firstname);
                iAppointments.setLastName(lastname);
                iAppointments.setAppointmentDate(date);
                iAppointments.setAppointmentTime(time);
                iAppointments.setComment(comment);
                appointments.add(iAppointments);
            }
            rs.close();
            bookedID.setCellValueFactory(new PropertyValueFactory<>("id"));
            firstNAME.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            lastNAME.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            appointmentDATE.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
            appointmentTIME.setCellValueFactory(new PropertyValueFactory<>("appointmentTime"));
            doctorCOMMENT.setCellValueFactory(new PropertyValueFactory<>("comment"));
            myAppointment.setItems(appointments);
        } catch (SQLException ex) {
            Logger.getLogger(MyAppointmentsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
