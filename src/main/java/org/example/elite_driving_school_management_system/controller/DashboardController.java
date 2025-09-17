package org.example.elite_driving_school_management_system.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Button btnHome;
    public Button btnStudents;
    public Button btnCourses;
    public Button btnInstructors;
    public Button btnLessons;
    public Button btnPayments;
    public Button btnUsers;
    public Button btnLogOut;
    public Label lblStudents;
    public Label lblCourses;
    public Label lblInstructors;
    public Label lblUsers;
    public Label lblTotalPayments;
    public AnchorPane ancHomeHide1;
    public AnchorPane ancHomeHide2;
    public AreaChart incomeChart;
    public AnchorPane ancMainContainer;

    public void setUserRole(String role) {
        if ("Admin".equalsIgnoreCase(role)) {
            btnUsers.setVisible(true);
            btnPayments.setVisible(true);
        } else if ("Receptionist".equalsIgnoreCase(role)) {
            btnUsers.setVisible(false);
            btnPayments.setVisible(true);
        } else {
            btnUsers.setVisible(false);
            btnPayments.setVisible(false);
        }
    }

    private void navigateTo(String path) {

        ancHomeHide1.setVisible(false);
        ancHomeHide2.setVisible(false);
        ancMainContainer.setVisible(true);
        ancMainContainer.getChildren().clear();

        try {
            ancMainContainer.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancMainContainer.widthProperty());
            anchorPane.prefHeightProperty().bind(ancMainContainer.heightProperty());

            ancMainContainer.getChildren().add(anchorPane);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Page not found..!").show();
            e.printStackTrace();
        }
    }

    public void btnHomeOnAction(ActionEvent actionEvent) {

    }

    public void btnStudentsOnAction(ActionEvent actionEvent) {
        ancHomeHide1.setVisible(false);
        ancHomeHide2.setVisible(false);
        ancMainContainer.setVisible(true);
        navigateTo("/view/student.fxml");
    }

    public void btnCoursesOnAction(ActionEvent actionEvent) {
        ancHomeHide1.setVisible(false);
        ancHomeHide2.setVisible(false);
        ancMainContainer.setVisible(true);
        navigateTo("/view/course.fxml");
    }

    public void btnInstructorsOnAction(ActionEvent actionEvent) {
        ancHomeHide1.setVisible(false);
        ancHomeHide2.setVisible(false);
        ancMainContainer.setVisible(true);
        navigateTo("/view/Instructor.fxml");
    }

    public void btnLessonsOnAction(ActionEvent actionEvent) {
        ancHomeHide1.setVisible(false);
        ancHomeHide2.setVisible(false);
        ancMainContainer.setVisible(true);
        navigateTo("/view/Lesson.fxml");
    }

    public void btnPaymentsOnAction(ActionEvent actionEvent) {
        ancHomeHide1.setVisible(false);
        ancHomeHide2.setVisible(false);
        ancMainContainer.setVisible(true);
        navigateTo("/view/payment.fxml");

    }

    public void btnUsersOnAction(ActionEvent actionEvent) {

    }

    public void btnLogOutOnAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
