package org.example.elite_driving_school_management_system.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.example.elite_driving_school_management_system.bo.BOFactory;
import org.example.elite_driving_school_management_system.bo.custom.UserBO;
import org.example.elite_driving_school_management_system.dto.UserDTO;

import java.net.URL;
import java.util.ResourceBundle;

public class PopUserController implements Initializable {
    public TextField txtUserName;
    public ComboBox<String> txtRole;
    public TextField txtPassword;
    public Button btnUserAdd;
    public Button btnUserUpdate;
    public TextField txtUserId;

    private final UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        txtRole.getItems().addAll("Admin", "Receptionist");
    }
        public void prepareForAdd() {
            try {
                txtUserId.setText(userBO.generateNewUserId());
                txtUserName.clear();
                txtPassword.clear();
                txtRole.setValue(null);

                btnUserAdd.setDisable(false);
                btnUserUpdate.setDisable(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }



    public void btnUserAddOnAction(ActionEvent actionEvent) {
        try {
            UserDTO dto = new UserDTO(
                    txtUserId.getText(),
                    txtUserName.getText(),
                    txtPassword.getText(),
                    txtRole.getValue()
            );
            boolean saved = userBO.saveUser(dto);
            if (saved) {
                txtUserId.clear();
                txtUserName.clear();
                txtPassword.clear();
                txtRole.setValue(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnUserUpdateOnAction(ActionEvent actionEvent) {
        try {
            UserDTO dto = new UserDTO(
                    txtUserId.getText(),
                    txtUserName.getText(),
                    txtPassword.getText(),
                    txtRole.getValue()
            );
            boolean updated = userBO.updateUser(dto);
            if (updated) {
                txtUserId.clear();
                txtUserName.clear();
                txtPassword.clear();
                txtRole.setValue(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setUserData(UserDTO user) {
        txtUserId.setText(user.getUserid());
        txtUserName.setText(user.getUsername());
        txtPassword.setText("");
        txtRole.setValue(user.getRole());

        btnUserAdd.setDisable(true);
        btnUserUpdate.setDisable(false);
    }

}
