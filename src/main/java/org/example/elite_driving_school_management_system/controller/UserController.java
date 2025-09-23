package org.example.elite_driving_school_management_system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.elite_driving_school_management_system.bo.BOFactory;
import org.example.elite_driving_school_management_system.bo.custom.UserBO;
import org.example.elite_driving_school_management_system.dto.UserDTO;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    public TableView<UserDTO> tableUser;
    public TableColumn<UserDTO, String> columnUserName;
    public TableColumn<UserDTO, String> columnRole;
    public Button btnAddUser;
    public TableColumn<UserDTO, String> columnUserId;

    private final UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnUserId.setCellValueFactory(new PropertyValueFactory<>("userid"));
        columnUserName.setCellValueFactory(new PropertyValueFactory<>("username"));
        columnRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        loadTableData();
        tableUser.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !tableUser.getSelectionModel().isEmpty()) {
                UserDTO selected = tableUser.getSelectionModel().getSelectedItem();
                openEditUserForm(selected);
            }
        });
    }

    private void openEditUserForm(UserDTO user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PopUser.fxml"));
            Parent root = loader.load();


            PopUserController controller = loader.getController();
            controller.setUserData(user);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit User");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            loadTableData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTableData() {
        try {
            List<UserDTO> list = userBO.getAllUsers();
            list.forEach(u -> System.out.println("DEBUG: " + u.getUserid() + " | " + u.getUsername() + " | " + u.getRole()));
            ObservableList<UserDTO> observableList = FXCollections.observableArrayList(list);
            tableUser.setItems(observableList);
            tableUser.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void btnAddUserOnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PopUser.fxml"));
            Parent root = loader.load();

            PopUserController controller = loader.getController();
            controller.prepareForAdd();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add User");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();


            loadTableData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
