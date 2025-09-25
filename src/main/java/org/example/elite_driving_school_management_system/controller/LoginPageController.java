package org.example.elite_driving_school_management_system.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.elite_driving_school_management_system.bo.BOFactory;
import org.example.elite_driving_school_management_system.bo.custom.UserBO;
import javafx.scene.Node;
import org.example.elite_driving_school_management_system.dto.UserDTO;
import org.example.elite_driving_school_management_system.util.PasswordUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {
        public TextField userName;
        @FXML
    private PasswordField password;


    private final UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    @FXML
    void btnlogin(ActionEvent event) {

        try {
            String loginInput = userName.getText().trim();
            UserDTO user = userBO.findByUsername(loginInput);
            if (user != null && PasswordUtils.verifyPassword(password.getText(), user.getPassword())) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard.fxml"));
                Parent root = loader.load();

                DashboardController dashboardController = loader.getController();
                dashboardController.setUserRole(user.getRole());

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle(user.getRole() + " Dashboard");
                stage.centerOnScreen();
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid username or password!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong: " + e.getMessage()).show();
        }
    }


    @FXML
    void navSignUp(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/signUp.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Sign Up");
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
