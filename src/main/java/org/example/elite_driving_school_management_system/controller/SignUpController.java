package org.example.elite_driving_school_management_system.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import org.example.elite_driving_school_management_system.bo.BOFactory;
import org.example.elite_driving_school_management_system.bo.custom.UserBO;
import org.example.elite_driving_school_management_system.dto.UserDTO;
import org.example.elite_driving_school_management_system.entity.User;
import org.example.elite_driving_school_management_system.util.PasswordUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class SignUpController implements Initializable {
    public TextField txtuserName;
    public PasswordField txtpassword;
    public ChoiceBox choiceRole;
    public PasswordField txtConfirmPassword;

    private final UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceRole.getItems().addAll("Admin", "Receptionist");
    }

    public void navbtnlogin(ActionEvent actionEvent) {
        try {
            if (!txtpassword.getText().equals(txtConfirmPassword.getText())) {
                new Alert(Alert.AlertType.ERROR, "Passwords do not match!").show();
                return;
            }

            String username = txtuserName.getText().trim();
            UserDTO existingUser = userBO.findByUsername(username);
            if (existingUser != null) {
                new Alert(Alert.AlertType.ERROR, "Username already exists!").show();
                return;
            }
            String newUserId = userBO.generateNewUserId();
            String hashedPassword = PasswordUtils.hashPassword(txtpassword.getText());

            UserDTO userDTO = new UserDTO(
                    newUserId,
                    txtuserName.getText(),
                    txtpassword.getText(),
                    (String) choiceRole.getValue()
            );

            if (userBO.saveUser(userDTO)) {
                new Alert(Alert.AlertType.INFORMATION, "Account created successfully!").show();

                Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Login");
                stage.centerOnScreen();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to create account!").show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }


    public void navLogIn(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
