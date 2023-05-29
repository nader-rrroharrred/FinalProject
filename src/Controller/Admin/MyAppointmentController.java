/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Admin;
import Controller.Admin.PatientLoginController;

import Model.Appointment;
import Model.BookedAppointment;
import Model.DB;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Masterz
 */
public class MyAppointmentController implements Initializable {

    @FXML
    private TableColumn<BookedAppointment, Integer> appointmentID;
    @FXML
    private TableColumn<BookedAppointment, String> comment;
    @FXML
    private Button backButton;
    @FXML
    private Button logOutButton;
    @FXML
    private TableView<BookedAppointment> patientsTableView;
    @FXML
    private TableColumn<BookedAppointment, Integer> id;
    @FXML
    private TableColumn<BookedAppointment, Integer> userID;
    @FXML
    private TableColumn<BookedAppointment, String> status;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      try {
            Connection connection = DB.getInstance().getConnection();
            String sql = "SELECT * FROM booked_appointments";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            ObservableList<BookedAppointment> appointments = FXCollections.observableArrayList();
            while (rs.next()) {
                int id = rs.getInt("id");
                int appointmentID = rs.getInt("appointment_id");
                int userID = rs.getInt("user_id");
                String status = rs.getString("status");
                String doctorComment = rs.getString("doctor_comment");
                BookedAppointment appointment = new BookedAppointment();
                appointment.setId(id);
                appointment.setAppointmentID(appointmentID);
                appointment.setUserID(userID);
                appointment.setStatus(status);
                appointment.setDoctorComment(doctorComment);
                appointments.add(appointment);
            }
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            userID.setCellValueFactory(new PropertyValueFactory<>("userID"));
            status.setCellValueFactory(new PropertyValueFactory<>("status"));
            comment.setCellValueFactory(new PropertyValueFactory<>("doctorComment"));
            patientsTableView.setItems(appointments);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void backButtonAction(ActionEvent event) {
        ViewManager.switcher.openPatientDashboard();
    }

    @FXML
    private void logOutButtonAction(ActionEvent event) {
        ViewManager.switcher.openLogin();
    }
}
