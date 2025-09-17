package org.example.elite_driving_school_management_system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.elite_driving_school_management_system.bo.BOFactory;
import org.example.elite_driving_school_management_system.bo.custom.CourseBO;
import org.example.elite_driving_school_management_system.dto.CourseDTO;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class CourseController implements Initializable {
    public TextField txtCourseID;
    public TextField txtCourseName;
    public Button btnCourseAdd;
    public Button btnCourseUpdate;
    public Button btnCourseDelete;
    public TextField txtCourseDuration;
    public TextField txtCourseFee;
    public TextField txtCourseDescription;
    public ComboBox <String>txtCourseInstructor;
    public TableView<CourseDTO> tableCourse;
    public TableColumn<CourseDTO,String> columnCourseId;
    public TableColumn<CourseDTO,String> columnCourseName;
    public TableColumn<CourseDTO,String> columnCourseDuration;
    public TableColumn<CourseDTO,String> columnCourseFee;
    public TableColumn<CourseDTO,String> columnCourseDescription;
    public TableColumn<CourseDTO,String> columnCourseInstructor;
    public TableColumn<CourseDTO,String> columnCourseEnrollmentCount;
    public Button btnViewEnrolledStudents;

    private final CourseBO courseBO = (CourseBO) BOFactory.getInstance().getBO(BOFactory.BOType.COURSE);
    public void btnCourseAddOnAction(ActionEvent actionEvent) {
        if (!validateCourseForm()) return;
        try {
            CourseDTO dto = new CourseDTO(
                    txtCourseID.getText(),
                    txtCourseName.getText(),
                    txtCourseDuration.getText(),
                    Double.parseDouble(txtCourseFee.getText()),
                    txtCourseDescription.getText(),
                    Collections.singletonList(txtCourseInstructor.getValue())
            );
            if (courseBO.saveCourse(dto)) {
                showAlert("Course Added Successfully!");
                loadAllCourses();
                generateId();
                clearForm();
            }
        } catch (Exception e) {
            showAlert("Error Adding Course: " + e.getMessage());
        }
    }

    public void btnCourseUpdateOnAction(ActionEvent actionEvent) {
        if (!validateCourseForm()) return;
        try {
            CourseDTO dto = new CourseDTO(
                    txtCourseID.getText(),
                    txtCourseName.getText(),
                    txtCourseDuration.getText(),
                    Double.parseDouble(txtCourseFee.getText()),
                    txtCourseDescription.getText(),
                    Collections.singletonList(txtCourseInstructor.getValue())
            );
            if (courseBO.updateCourse(dto)) {
                showAlert("Course Updated Successfully!");
                loadAllCourses();
            } else {
                showAlert("Course not found, update failed!");
            }
        } catch (Exception e) {
            showAlert("Error Updating Course: " + e.getMessage());
        }
    }

    public void btnCourseDeleteOnAction(ActionEvent actionEvent) {
        try {
            String id = txtCourseID.getText();
            if (courseBO.deleteCourse(id)) {
                showAlert("Course Deleted Successfully!");
                loadAllCourses();
                generateId();
            }
        } catch (Exception e) {
            showAlert("Error Deleting Course: " + e.getMessage());
        }
    }

    public void btnViewEnrolledStudentsOnAction(ActionEvent actionEvent) {
        showAlert("Feature to display enrolled students is not yet implemented.");
    }

    private void showAlert(String message) {
        new Alert(Alert.AlertType.INFORMATION, message).show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnCourseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        columnCourseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        columnCourseDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        columnCourseFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
        columnCourseDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnCourseInstructor.setCellValueFactory(new PropertyValueFactory<>("instructorId"));

        loadAllCourses();
        generateId();
    }

    private void loadAllCourses() {
        try {
            List<CourseDTO> courseList = courseBO.getAllCourses();
            ObservableList<CourseDTO> obList = FXCollections.observableArrayList(courseList);
            tableCourse.setItems(obList);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error loading courses: " + e.getMessage());
        }
    }
    private void generateId() {
        try {
            txtCourseID.setText(courseBO.generateNewId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateCourseForm() {
        if (txtCourseName.getText().isEmpty() ||
                txtCourseDuration.getText().isEmpty() ||
                txtCourseFee.getText().isEmpty() ||
                txtCourseDescription.getText().isEmpty() ||
                txtCourseInstructor.getValue() == null) {
            showAlert("Please fill in all fields before saving!");
            return false;
        }
        try {
            Double.parseDouble(txtCourseFee.getText());
        } catch (NumberFormatException e) {
            showAlert("Fee must be a valid number!");
            return false;
        }
        return true;
    }

    private void clearForm() {
        txtCourseName.clear();
        txtCourseDuration.clear();
        txtCourseFee.clear();
        txtCourseDescription.clear();
        txtCourseInstructor.setValue(null);
    }




}
