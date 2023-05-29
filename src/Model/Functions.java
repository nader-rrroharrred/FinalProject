/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.Functions;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 *
 * @author Masterz
 */
public class Functions {

    public static void Clear(Pane container) {

        for (Node node : container.getChildren()) {
            if (node instanceof TextField) {
                ((TextField) node).setText("");
            } else if (node instanceof PasswordField) {
                ((PasswordField) node).setText("");
            } else if (node instanceof RadioButton) {
                ToggleGroup group = ((RadioButton) node).getToggleGroup();
                if (group != null) {
                    group.selectToggle(null);
                }
            } else if (node instanceof Pane) {
                Clear((Pane) node);
            }
        }

    }

    public static void SuccessAlert() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success Message");
        alert.setHeaderText(null);
        alert.setContentText("Added Successfully.");
        ImageView icon = new ImageView("images/success-icon.png");
        icon.setFitWidth(48);
        icon.setFitHeight(48);
        icon.setPreserveRatio(true);
        alert.getDialogPane().setGraphic(icon);
        alert.showAndWait();
    }

    public static void UpdatedAlert() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success Message");
        alert.setHeaderText(null);
        alert.setContentText("Updated Successfully.");
        ImageView icon = new ImageView("images/success-icon.png");
        icon.setFitWidth(48);
        icon.setFitHeight(48);
        icon.setPreserveRatio(true);
        alert.getDialogPane().setGraphic(icon);
        alert.showAndWait();
    }

    public static void DoctorLoginAlert() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login");
        alert.setHeaderText(null);
        alert.setContentText("Logged in, Welcome Doctor!");
        ImageView icon = new ImageView("images/success-icon.png");
        icon.setFitWidth(48);
        icon.setFitHeight(48);
        icon.setPreserveRatio(true);
        alert.getDialogPane().setGraphic(icon);
        alert.showAndWait();
    }

    public static void PatientLoginAlert() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login");
        alert.setHeaderText(null);
        alert.setContentText("Logged in, Welcome!");
        ImageView icon = new ImageView("images/success-icon.png");
        icon.setFitWidth(48);
        icon.setFitHeight(48);
        icon.setPreserveRatio(true);
        alert.getDialogPane().setGraphic(icon);
        alert.showAndWait();
    }

    public static void DoctorLoginErrorAlert() {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Something Went Wrong! or you are not a Doctor");
        alert.showAndWait();
    }

    public static void PatientLoginErrorAlert() {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Something Went Wrong!");
        alert.showAndWait();
    }

    public static void ErrorAlert() {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Something Went Wrong!");
        alert.showAndWait();
    }

    public static void SignUpAlert() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sign Up");
        alert.setHeaderText(null);
        alert.setContentText("It's Done, Login Now!");
        ImageView icon = new ImageView("images/signup-icon.png");
        icon.setFitWidth(48);
        icon.setPreserveRatio(true);
        alert.getDialogPane().setGraphic(icon);
        alert.showAndWait();
    }

    public static void WarningAlert() {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText("The item will be deleted!");
        alert.showAndWait();

    }

}
