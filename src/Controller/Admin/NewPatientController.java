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
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author Masterz
 */
public class NewPatientController implements Initializable {

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
    private Button newPatientButton;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private RadioButton maleChoice;
    @FXML
    private RadioButton femaleChoice;
    @FXML
    private ToggleGroup toggle;
    @FXML
    private Label addStatus;
    @FXML
    private Button clearButton;
    @FXML
    private AnchorPane anchorPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    private void newPatientButtonAction(ActionEvent event) throws SQLException {
        try {
            Connection connection = DB.getInstance().getConnection();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();
            int age = Integer.parseInt(ageField.getText());
            String email = emailField.getText();
            String phone = phoneField.getText();
            String gender = ((RadioButton) toggle.getSelectedToggle()).getText();
            String role = "patient";
            User user = new User(firstName, lastName, username, password, age, email, phone, gender, role);
            user.save(connection);
            Functions.SuccessAlert();

        } catch (Exception ex) {
            Functions.ErrorAlert();
        }
    }

    @FXML
    private void maleChoiceAction(ActionEvent event) {
    }

    @FXML
    private void femaleChoiceAction(ActionEvent event) {
    }

    @FXML
    private void clearButtonAction(ActionEvent event) {
        Functions.Clear(anchorPane);
    }

}
