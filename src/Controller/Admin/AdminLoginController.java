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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Masterz
 */
public class AdminLoginController implements Initializable {

    @FXML
    private TextField adminUsername;
    @FXML
    private PasswordField adminPassword;
    @FXML
    private Button adminLogin;
    @FXML
    private Label welcomeLabel1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void adminLoginAction(ActionEvent event) throws SQLException {
        Connection connection = DB.getInstance().getConnection();
        String username = adminUsername.getText();
        String password = adminPassword.getText();
        String role = "";

        String sql = "SELECT * FROM users WHERE username = ? && password = ? && role = 'admin'";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            role = rs.getString("role");
        }

        if (role.equals("admin")) {
            ViewManager.switcher.openAdminDashboard();
            adminUsername.setText("");
            adminPassword.setText("");
            Functions.DoctorLoginAlert();

        } else {
            Functions.DoctorLoginErrorAlert();
        }
    }

    @FXML
    private void back(MouseEvent event) {
        ViewManager.switcher.openLogin();
    }

}
