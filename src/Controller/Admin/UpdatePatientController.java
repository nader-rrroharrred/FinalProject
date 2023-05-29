/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Admin;

import Model.DB;
import Model.Functions;
import Model.User;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Masterz
 */
public class UpdatePatientController implements Initializable {

    private User oldUser;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button saveInfoButton;
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
    private ToggleGroup toggle;
    @FXML
    private RadioButton femaleChoice;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //get selected user data from UsersManagmentController updatedUser var
        this.oldUser = Controller.Admin.DoctorDashboardController.selectedUserToUpdate;
        firstNameField.setText(oldUser.getFirstname());
        lastNameField.setText(oldUser.getLastname());
        usernameField.setText(oldUser.getUsername());
        passwordField.setText(oldUser.getPassword());
        ageField.setText(String.valueOf(oldUser.getAge()));
        emailField.setText(oldUser.getEmail());
        phoneField.setText(oldUser.getPhone());
        if (oldUser.getGender().equals("Female")) {
            toggle.selectToggle(femaleChoice);
        } else if (oldUser.getGender().equals("Male")) {
            toggle.selectToggle(maleChoice);
        }

    }

    @FXML
    private void saveInfoButtonAction(ActionEvent event) {
        String gender = ((RadioButton) toggle.getSelectedToggle()).getText();
        try {
            Connection connection = DB.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE users SET username = ? , password = ? , firstname= ?, lastname = ? ,age = ? , email = ?, phone = ?, gender = ? WHERE id = ?");
            statement.setString(1, usernameField.getText());
            statement.setString(2, passwordField.getText());
            statement.setString(3, firstNameField.getText());
            statement.setString(4, lastNameField.getText());
            statement.setInt(5, Integer.parseInt(ageField.getText()));
            statement.setString(6, emailField.getText());
            statement.setString(7, phoneField.getText());
            statement.setString(8, gender);
            statement.setInt(9, oldUser.getId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                oldUser.setUsername(usernameField.getText());
                oldUser.setPassword(passwordField.getText());
                oldUser.setFirstname(firstNameField.getText());
                oldUser.setLastname(lastNameField.getText());
                oldUser.setAge(Integer.parseInt(ageField.getText()));
                oldUser.setEmail(emailField.getText());
                oldUser.setPhone(phoneField.getText());
                oldUser.setGender(gender);
                Functions.UpdatedAlert();
            } else {
                Functions.ErrorAlert();
            }
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

}
