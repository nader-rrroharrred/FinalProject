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
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author Masterz
 */
public class NewAppointmentController implements Initializable {

    @FXML
    private Rectangle rectangle;
    @FXML
    private Label doctorDashboardLabel;
    @FXML
    private Button patientsButton;
    @FXML
    private Button appointmentsButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button BookedButton;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button addAppointment;
    @FXML
    private ComboBox<String> status;
    @FXML
    private ComboBox<String> time;
    @FXML
    private Label addStatus;
    @FXML
    private Button BackButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Status 
        ObservableList<String> Status = FXCollections.observableArrayList("Free", "Booked");
        status.getItems().addAll(Status);
        status.setValue("Status");
        //Time
        ObservableList<String> Time = FXCollections.observableArrayList("12:00 AM", "01:00 AM",
                "02:00 AM", "03:00 AM", "04:00 AM", "05:00 AM", "06:00 AM", "07:00 AM", "08:00 AM", "09:00 AM", "10:00 AM", "11:00 AM",
                "12:00 PM", "01:00 PM",
                "02:00 PM", "03:00 PM", "04:00 PM", "05:00 PM", "06:00 PM", "07:00 PM", "08:00 PM", "09:00 PM", "10:00 PM", "11:00 PM"
        );
        time.getItems().addAll(Time);
        time.setValue("Time");
    }

    @FXML
    private void patientsButtonAction(ActionEvent event) {
        ViewManager.switcher.openAdminDashboard();
    }

    @FXML
    private void appointmentsButtonAction(ActionEvent event) {
        ViewManager.switcher.openAppointments();
    }

    @FXML
    private void logoutButtonAction(ActionEvent event) {
        ViewManager.switcher.openAdmin();
    }

    @FXML
    private void BookedButtonAction(ActionEvent event) {
        ViewManager.switcher.openBookedAppointments();
    }

    @FXML
    private void addAppointmentAction(ActionEvent event) {
        try {
            Connection connection = DB.getInstance().getConnection();
            LocalDate selectedDate = datePicker.getValue();
            String Day = datePicker.getValue().getDayOfWeek().toString();
            String timeValue = time.getValue();
            String statusValue = status.getValue();
            Appointment appointment = new Appointment(selectedDate, Day, timeValue, statusValue);
            appointment.save();
            Functions.SuccessAlert();

        } catch (SQLException ex) {
            Functions.ErrorAlert();
        }

    }

    @FXML
    private void BackButtonAction(ActionEvent event) {
        ViewManager.switcher.openAppointments();
    }

}
