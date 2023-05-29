/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Admin;

import Model.BookedAppointment;
import Model.DB;
import Model.Functions;
import View.ViewManager;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author Masterz
 */
public class BookedAppointmentsController implements Initializable {

    @FXML
    private Button patientsButton;
    @FXML
    private Button appointmentsButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button backButton;
    @FXML
    private Button newAppointment;
    @FXML
    private TableColumn<BookedAppointment, Integer> id;
    @FXML
    private TableColumn<BookedAppointment, Integer> appointmentID;
    @FXML
    private TableColumn<BookedAppointment, Integer> userID;
    @FXML
    private TableColumn<BookedAppointment, String> status;
    @FXML
    private TableColumn<BookedAppointment, String> comment;
    @FXML
    private TextField searchBar;
    @FXML
    private Button searchButton;
    @FXML
    private TextField commentTextField;
    @FXML
    private Button addCommentButton;
    @FXML
    private TableView<BookedAppointment> bookedAppointments;

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
            bookedAppointments.setItems(appointments);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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
    private void backButtonAction(ActionEvent event) {
        ViewManager.switcher.openAppointments();
    }

    @FXML
    private void newAppointmentAction(ActionEvent event) {
        ViewManager.switcher.openNewAppointment();
    }

    @FXML
    private void searchButtonAction(ActionEvent event) {
        String firstName = searchBar.getText().trim();
        if (firstName.isEmpty()) {
            // If the search field is empty, show all appointments
            initialize(null, null);
        } else {
            try {
                Connection connection = DB.getInstance().getConnection();
                String sql = "SELECT b.* FROM booked_appointments b "
                        + "JOIN users u ON b.user_id = u.id "
                        + "WHERE u.firstname LIKE ?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, "%" + firstName + "%");
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
                bookedAppointments.setItems(appointments);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    private void addCommentButtonAction(ActionEvent event) {
        BookedAppointment bookedAppointment = bookedAppointments.getSelectionModel().getSelectedItem();

        try {
            Connection connection = DB.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE booked_appointments SET status = ?, doctor_comment = ? WHERE id = ?");
            if (commentTextField.getText().length() > 0) {
                statement.setString(1, "Finished");
            } else {
                statement.setString(1, "Waiting");
            }
            statement.setString(2, commentTextField.getText());
            statement.setInt(3, bookedAppointment.getId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                bookedAppointment.setDoctorComment(commentTextField.getText());
                bookedAppointment.setStatus("Finished");
                Functions.UpdatedAlert();
                bookedAppointments.refresh();
            } else {
                Functions.ErrorAlert();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Functions.ErrorAlert();
        }
    }

}
