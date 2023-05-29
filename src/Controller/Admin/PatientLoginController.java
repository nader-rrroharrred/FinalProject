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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author Masterz
 */
public class PatientLoginController implements Initializable {

    @FXML
    private Rectangle rectangle;
    @FXML
    private Circle circle;
    @FXML
    private Label welcomeLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Label loginLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private Button signupButton;
    @FXML
    private Button loginAdminButton;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginStatusLabel;

    /**
     * Initializes the controller class.
     */
    public static User currentUser = new User();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void signupButtonAction(ActionEvent event) {
        ViewManager.switcher.openRegister();
    }

    @FXML
    private void loginAdminButtonAction(ActionEvent event) {
        ViewManager.switcher.openAdmin();
    }

    @FXML
    private void loginButtonAction(ActionEvent event) throws SQLException {
        Connection connection = DB.getInstance().getConnection();
        String username = usernameTextField.getText();
        String password = passwordPasswordField.getText();
        String sql = "SELECT * FROM users WHERE username = ? AND password = ? AND role = 'patient'";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            currentUser.setId(rs.getInt("id"));
            currentUser.setUsername(rs.getString("username"));
            currentUser.setPassword(rs.getString("password"));
            currentUser.setFirstname(rs.getString("firstname"));
            currentUser.setLastname(rs.getString("lastname"));
            currentUser.setAge(rs.getInt("age"));
            currentUser.setEmail(rs.getString("email"));
            currentUser.setPhone(rs.getString("phone"));
            currentUser.setGender(rs.getString("gender"));
            currentUser.setRole(rs.getString("role"));
            usernameTextField.setText("");
            passwordPasswordField.setText("");
            ViewManager.switcher.openPatientDashboard();
            Functions.PatientLoginAlert();
        } else {
            Functions.ErrorAlert();
        }
    }
}
