/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Admin;

import Model.Appointment;
import Model.DB;
import Model.Functions;
import Model.User;
import View.ViewManager;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author Masterz
 */
public class AppointmentsPageController implements Initializable {

    @FXML
    private TableColumn<Appointment, Integer> appointmentID;
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
    private DatePicker DateSelected;
    @FXML
    private ComboBox<String> TimeSelected;
    @FXML
    private ComboBox<String> statusSelected;

    ObservableList<String> Time = FXCollections.observableArrayList("12:00 AM", "01:00 AM",
            "02:00 AM", "03:00 AM", "04:00 AM", "05:00 AM", "06:00 AM", "07:00 AM", "08:00 AM", "09:00 AM", "10:00 AM", "11:00 AM",
            "12:00 PM", "01:00 PM",
            "02:00 PM", "03:00 PM", "04:00 PM", "05:00 PM", "06:00 PM", "07:00 PM", "08:00 PM", "09:00 PM", "10:00 PM", "11:00 PM"
    );
    ObservableList<String> Status = FXCollections.observableArrayList("Free", "Booked");
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
    private Button DeleteButton;
    @FXML
    private Button newAppointment;
    @FXML
    private Button BookedButton;
    @FXML
    private Button UpdateButton;
    @FXML
    private Button ResetButton;

    /**
     * Initializes the controller class.
     */
    @Override

    public void initialize(URL url, ResourceBundle rb) {
        TimeSelected.getItems().addAll(Time);
        TimeSelected.setValue("Time");
        statusSelected.getItems().addAll(Status);
        statusSelected.setValue("Status");
        appointmentTableView.setEditable(true);
        try {
            Connection connection = DB.getInstance().getConnection();
            String sql = "SELECT * FROM appointments";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            appointmentTableView.refresh();
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

        appointmentTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                int id = newSelection.getId();
                LocalDate appointmentDate = newSelection.getAppointmentDate();
                String appointmentDay = appointmentDate.getDayOfWeek().toString();
                String appointmentTime = newSelection.getAppointmentTime();
                String status = newSelection.getStatus();

                DateSelected.setValue(appointmentDate);
                TimeSelected.setValue(appointmentTime);
                statusSelected.setValue(status);
                newSelection.setAppointmentDay(appointmentDay);
            }
        });
    }

    @FXML
    private void patientsButtonAction(ActionEvent event) {
        ViewManager.switcher.openAdminDashboard();
    }

    @FXML
    private void logoutButtonAction(ActionEvent event) {
        ViewManager.switcher.openAdmin();
    }

    @FXML
    private void DeleteButtonAction(ActionEvent event) {
        Appointment selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            appointmentTableView.getItems().remove(selectedAppointment);
            try {
                Connection connection = DB.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement("DELETE FROM appointments WHERE id = ?");
                statement.setInt(1, selectedAppointment.getId());
                statement.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(DoctorDashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void newAppointmentAction(ActionEvent event) {
        ViewManager.switcher.openNewAppointment();
    }

    @FXML
    private void BookedButtonAction(ActionEvent event) {
        ViewManager.switcher.openBookedAppointments();
    }

    @FXML
    private void appointmentsButtonAction(ActionEvent event) {
        ViewManager.switcher.openAppointments();
    }

    @FXML
    private void UpdateButtonAction(ActionEvent event) {
        Appointment selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            try {
                Connection connection = DB.getInstance().getConnection();

                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE appointments SET appointment_date = ?, appointment_day = ?, appointment_time = ?, status = ?  WHERE id = ?");

                statement.setDate(1, Date.valueOf(DateSelected.getValue()));
                statement.setString(2, DateSelected.getValue().getDayOfWeek().toString());
                statement.setString(3, TimeSelected.getValue());
                statement.setString(4, statusSelected.getValue());
                statement.setInt(5, selectedAppointment.getId());
                int rowsUpdated = statement.executeUpdate();
                appointmentTableView.refresh();
                if (rowsUpdated > 0) {
                    selectedAppointment.setAppointmentDate(DateSelected.getValue());
                    selectedAppointment.setAppointmentDay(DateSelected.getValue().getDayOfWeek().toString()); // Update the appointmentDay property with the correct day name
                    selectedAppointment.setAppointmentTime(TimeSelected.getValue());
                    selectedAppointment.setStatus(statusSelected.getValue());
                    appointmentTableView.refresh();
                    Functions.UpdatedAlert();
                } else {
                    Functions.ErrorAlert();
                }
            } catch (SQLException e) {
                Functions.ErrorAlert();
            }
        }
    }

    public void reSET() throws SQLException {
        Connection connection = DB.getInstance().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM appointments");
        ObservableList<Appointment> updatedAppointmentsList = FXCollections.observableArrayList();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            LocalDate appointmentDate = resultSet.getDate("appointment_date").toLocalDate();
            String appointmentDay = resultSet.getString("appointment_day");
            String appointmentTime = resultSet.getString("appointment_time");
            String status = resultSet.getString("status");
            Appointment appointment = new Appointment(appointmentDate, appointmentDay, appointmentTime, status);
            appointment.setId(id);
            updatedAppointmentsList.add(appointment);
        }
        appointmentTableView.getItems().clear();
        appointmentTableView.setItems(updatedAppointmentsList);
        appointmentTableView.refresh();
    }

    @FXML
    private void ResetButtonAction(ActionEvent event) throws SQLException {
        ViewManager.switcher.openAppointments();
        reSET();
    }

}
