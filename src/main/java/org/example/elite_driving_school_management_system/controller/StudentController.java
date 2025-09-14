package org.example.elite_driving_school_management_system.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.elite_driving_school_management_system.bo.BOFactory;
import org.example.elite_driving_school_management_system.bo.custom.StudentBO;
import org.example.elite_driving_school_management_system.dto.StudentDTO;
import org.example.elite_driving_school_management_system.util.RegexValidator;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StudentController implements Initializable {
    public TextField txtStudentId;
    public TextField txtStudentName;
    public Button btnStudentAdd;
    public Button btnStudentUpdate;
    public Button btnStudentDelete;
    public ListView txtStudentCourseList;
    public TextField txtStudentEmail;
    public TextField txtStudentContact;
    public TextField txtStudentDate;
    public TextField txtStudentCourseId;
    public TableView tableStudent;
    public TableColumn columnStudentId;
    public TableColumn columnStudentName;
    public TableColumn columnStudentEmail;
    public TableColumn columnStudentContact;
    public TableColumn columnStudentDate;
    public TableColumn columnStudentCourseId;
    public TableColumn columnStudentCourses;
    private final StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBO(BOFactory.BOType.STUDENT);

    public void btnStudentDeleteOnAction(ActionEvent actionEvent) {
        try {
            if (studentBO.deleteStudent(txtStudentId.getText())) {
                new Alert(Alert.AlertType.INFORMATION, "Student Deleted!").show();
                loadAllStudents();
                setNewStudentId();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    public void btnStudentUpdateOnAction(ActionEvent actionEvent) {
        try {
            if (!validateInputs()) return;

            StudentDTO dto = new StudentDTO(
                    txtStudentId.getText(),
                    txtStudentName.getText(),
                    txtStudentEmail.getText(),
                    txtStudentContact.getText(),
                    LocalDate.parse(txtStudentDate.getText()),
                    new ArrayList<>(txtStudentCourseList.getSelectionModel().getSelectedItems())
            );

            if (studentBO.updateStudent(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Student Updated!").show();
                loadAllStudents();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnStudentAddOnAction(ActionEvent actionEvent) {
        try {
            if (!validateInputs()) return;

            StudentDTO dto = new StudentDTO(
                    txtStudentId.getText(),
                    txtStudentName.getText(),
                    txtStudentEmail.getText(),
                    txtStudentContact.getText(),
                    LocalDate.now(), // today’s date
                    new ArrayList<>(txtStudentCourseList.getSelectionModel().getSelectedItems())
            );

            if (studentBO.saveStudent(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Student Saved!").show();
                loadAllStudents();
                setNewStudentId();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    private void loadAllStudents() {
        try {
            tableStudent.getItems().clear();
            tableStudent.getItems().addAll(studentBO.getAllStudents());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private boolean validateInputs() {
        if (!RegexValidator.isValidEmail(txtStudentEmail.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid Email!").show();
            return false;
        }
        if (!RegexValidator.isValidContact(txtStudentContact.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid Contact (only digits allowed)!").show();
            return false;
        }
        return true;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtStudentId.setEditable(false);
        txtStudentDate.setEditable(false);

        setNewStudentId();
        txtStudentDate.setText(LocalDate.now().toString());

        loadAllStudents();
    }

    private void setNewStudentId() {
        try {
            String id = studentBO.generateNewStudentId();
            txtStudentId.setText(id);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate ID: " + e.getMessage()).show();
        }
    }
}