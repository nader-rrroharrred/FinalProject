/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Admin;

import Model.DB;
import Model.Functions;
import Model.User;
import View.ViewManager;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Masterz
 */
public class DoctorDashboardController implements Initializable {

    public static User selectedUserToUpdate;
    public static Stage updateStage;

    ObservableList<User> userList;
    @FXML
    private Button patientsButton;
    @FXML
    private Button logoutButton;
    @FXML
    private TableView<User> patientsTableView;
    @FXML
    private TableColumn<User, Integer> patientID;
    @FXML
    private TableColumn<User, String> patientFirstName;
    @FXML
    private TableColumn<User, String> PatientLastName;
    @FXML
    private TableColumn<User, Integer> PatientAge;
    @FXML
    private TableColumn<User, String> PatientEmail;
    @FXML
    private TableColumn<User, String> PatientPhone;
    @FXML
    private TableColumn<User, String> PatientGender;
    @FXML
    private TextField searchBar;
    @FXML
    private Button appointmentsButton;
    @FXML
    private Button deletePatient;
    @FXML
    private Button UpdateButton;
    @FXML
    private Button newPatientButton;
    @FXML
    private Button searchButton;
    @FXML
    private TableColumn<User, String> patientUsername;
    @FXML
    private Button ResetButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            Connection connection = DB.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE role = 'patient'");
            userList = FXCollections.observableArrayList();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                int age = resultSet.getInt("age");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String gender = resultSet.getString("gender");
                String role = resultSet.getString("role");
                User user = new User();
                user.setId(id);
                user.setUsername(username);
                user.setPassword(password);
                user.setFirstname(firstname);
                user.setLastname(lastname);
                user.setAge(age);
                user.setEmail(email);
                user.setPhone(phone);
                user.setGender(gender);
                user.setRole(role);
                userList.add(user);
            }

            patientID.setCellValueFactory(new PropertyValueFactory<>("id"));
            patientUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
            patientFirstName.setCellValueFactory(new PropertyValueFactory<>("firstname"));
            PatientLastName.setCellValueFactory(new PropertyValueFactory<>("lastname"));
            PatientAge.setCellValueFactory(new PropertyValueFactory<>("age"));
            PatientEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            PatientPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            PatientGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
            patientsTableView.setItems(userList);

        } catch (SQLException ex) {
            Logger.getLogger(DoctorDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        patientsTableView.setEditable(true);
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
    private void newPatientButtonAction(ActionEvent event) {
        ViewManager.switcher.openNewPatient();
    }

    @FXML
    private void searchButtonAction(ActionEvent event) {
        String searchTerm = searchBar.getText();
        ObservableList<User> matchingUsers = FXCollections.observableArrayList();
        for (User user : userList) {
            if (user.getFirstname().equals(searchTerm)) {
                matchingUsers.add(user);
            }
        }
        patientsTableView.setItems(matchingUsers);
    }

    @FXML
    private void appointmentsButtonAction(ActionEvent event) {
        ViewManager.switcher.openAppointments();
    }

    @FXML
    private void deletePatientAction(ActionEvent event) {

        User selectedUser = patientsTableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            //show a confirmation alert and make the deletion on confirm event
            Alert deleteConfirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            deleteConfirmAlert.setTitle("User delete");
            deleteConfirmAlert.setContentText("Are you sure to delete this patient?");
            deleteConfirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try (Connection connection = DB.getInstance().getConnection();
                        PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
                        statement.setInt(1, selectedUser.getId());
                        statement.executeUpdate();
                        patientsTableView.getItems().remove(selectedUser);
                    } catch (Exception ex) {
                        // if the user has been booked an appointment.
                        Functions.ErrorAlert();
                    }
                }
            });
        }
    }

    @FXML
    private void UpdateButtonAction(ActionEvent event) throws SQLException, IOException {
        //check if there is an user selected from the TableView
        if (patientsTableView.getSelectionModel().getSelectedItem() != null) {
            //store the selected user from the TableView in our global var user selectedUserToUpdate   
            selectedUserToUpdate = patientsTableView.getSelectionModel().getSelectedItem();
            //load update page fxml
            FXMLLoader loaderUpdate = new FXMLLoader(getClass().getResource("/View/AdminFXML/UpdatePatient.fxml"));
            Parent rootUpdate = loaderUpdate.load();
            Scene updateUserScene = new Scene(rootUpdate);
            //store loaded fxml in our global stage updateStage and show it
            updateStage = new Stage();
            updateStage.setScene(updateUserScene);
            updateStage.setTitle("Patient Info " + selectedUserToUpdate.getUsername());
            updateStage.show();
        }

    }

    public void reSET() throws SQLException {
        Connection connection = DB.getInstance().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE role = 'patient'");
        ObservableList<User> updatedUserList = FXCollections.observableArrayList();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String firstname = resultSet.getString("firstname");
            String lastname = resultSet.getString("lastname");
            int age = resultSet.getInt("age");
            String email = resultSet.getString("email");
            String phone = resultSet.getString("phone");
            String gender = resultSet.getString("gender");
            String role = resultSet.getString("role");
            User user = new User();
            user.setId(id);
            user.setUsername(username);
            user.setPassword(password);
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setAge(age);
            user.setEmail(email);
            user.setPhone(phone);
            user.setGender(gender);
            user.setRole(role);
            updatedUserList.add(user);
        }
        patientsTableView.getItems().clear();
        patientsTableView.setItems(updatedUserList);
        // refresh the TableView to update the UI
        patientsTableView.refresh();
    }

    @FXML
    private void ResetButtonAction(ActionEvent event) throws SQLException {
        ViewManager.switcher.openAdminDashboard();
        reSET();
    }

}
