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

/**
 * FXML Controller class
 *
 * @author Masterz
 */
public class PatientRegisterController implements Initializable {

    String male, female;
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
    private ToggleGroup toggleGroup;
    @FXML
    private RadioButton femaleChoice;
    @FXML
    private Button signupButton;
    @FXML
    private Button homebutton;
    @FXML
    private AnchorPane registerAnchor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void maleChoiceAction(ActionEvent event) {
        if (maleChoice.isSelected()) {
            male = maleChoice.getText();
        }
    }

    @FXML
    private void femaleChoiceAction(ActionEvent event) {
        if (femaleChoice.isSelected()) {
            female = femaleChoice.getText();
        }
    }

    @FXML
    private void signupButtonAction(ActionEvent event) throws SQLException {
        
        try {
            Connection connection = DB.getInstance().getConnection();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();
            int age = Integer.parseInt(ageField.getText());
            String email = emailField.getText();
            String phone = phoneField.getText();
            String gender = ((RadioButton) toggleGroup.getSelectedToggle()).getText();
            String role = "patient";
            User user = new User(firstName, lastName, username, password, age, email, phone, gender, role);
            user.save(connection);
            Functions.SignUpAlert();
            ViewManager.switcher.openLogin();
            Functions.Clear(registerAnchor);

        } catch (Exception ex) {
            Functions.ErrorAlert();
        }
    }

    @FXML
    private void homebuttonAction(ActionEvent event) {
        ViewManager.switcher.openLogin();
    }

}
