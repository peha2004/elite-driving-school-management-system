package org.example.elite_driving_school_management_system.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.elite_driving_school_management_system.bo.BOFactory;
import org.example.elite_driving_school_management_system.bo.custom.CourseBO;
import org.example.elite_driving_school_management_system.bo.custom.StudentBO;
import org.example.elite_driving_school_management_system.dto.CourseDTO;
import org.example.elite_driving_school_management_system.dto.StudentDTO;
import org.example.elite_driving_school_management_system.util.RegexValidator;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class StudentController implements Initializable {
    public TextField txtStudentId;
    public TextField txtStudentName;
    public Button btnStudentAdd;
    public Button btnStudentUpdate;
    public Button btnStudentDelete;
    public ListView<String> txtStudentCourseList;
    public TextField txtStudentEmail;
    public TextField txtStudentContact;
    public TextField txtStudentDate;
    public TableView<StudentDTO> tableStudent;
    public TableColumn<StudentDTO,String> columnStudentId;
    public TableColumn<StudentDTO,String>  columnStudentName;
    public TableColumn<StudentDTO,String>  columnStudentEmail;
    public TableColumn<StudentDTO,String>  columnStudentContact;
    public TableColumn<StudentDTO,String>  columnStudentDate;
    public TableColumn<StudentDTO,String>  columnStudentCourses;

    private final StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBO(BOFactory.BOType.STUDENT);
    private final CourseBO courseBO = (CourseBO) BOFactory.getInstance().getBO(BOFactory.BOType.COURSE);



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnStudentId.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStudentID()));
        columnStudentName.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        columnStudentEmail.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEmail()));
        columnStudentContact.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getContact()));
        columnStudentDate.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getRegistrationDate().toString()));
        columnStudentCourses.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getEnrolledCourses() != null ?
                        String.join(", ", cellData.getValue().getEnrolledCourses()) :
                        ""
        ));

        txtStudentCourseList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        txtStudentId.setEditable(false);
        txtStudentDate.setEditable(false);

        loadAllCourses();
        loadAllStudents();


        setNewStudentId();
        txtStudentDate.setText(LocalDate.now().toString());

        tableStudent.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtStudentId.setText(newSelection.getStudentID());
                txtStudentName.setText(newSelection.getName());
                txtStudentEmail.setText(newSelection.getEmail());
                txtStudentContact.setText(newSelection.getContact());
                txtStudentDate.setText(newSelection.getRegistrationDate().toString());

                txtStudentCourseList.getItems().clear();
                if (newSelection.getEnrolledCourses() != null) {
                    for (String courseName : newSelection.getEnrolledCourses()) {
                        for (int i = 0; i < txtStudentCourseList.getItems().size(); i++) {
                            if (txtStudentCourseList.getItems().get(i).equals(courseName)) {
                                txtStudentCourseList.getSelectionModel().select(i);
                            }
                        }
                    }
//                    newSelection.getEnrolledCourses().forEach(c -> txtStudentCourseList.getItems().add(c.getCourseName()));
                }
            }
        });
    }
    private void loadAllStudents() {
        try {
            tableStudent.getItems().clear();
            tableStudent.getItems().addAll(studentBO.getAllStudents());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnStudentDeleteOnAction(ActionEvent actionEvent) {
        try {
            if (studentBO.deleteStudent(txtStudentId.getText())) {
                new Alert(Alert.AlertType.INFORMATION, "Student Deleted!").show();
                loadAllStudents();
                setNewStudentId();
                clearForm();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    public void btnStudentUpdateOnAction(ActionEvent actionEvent) {
        try {
            if (!validateInputs()) return;

            var selectedNames = txtStudentCourseList.getSelectionModel().getSelectedItems();

            ArrayList<String> courseNames = new ArrayList<>(txtStudentCourseList.getSelectionModel().getSelectedItems());

            StudentDTO dto = new StudentDTO(
                    txtStudentId.getText(),
                    txtStudentName.getText(),
                    txtStudentEmail.getText(),
                    txtStudentContact.getText(),
                    LocalDate.parse(txtStudentDate.getText()),
                    courseNames
            );

            if (studentBO.updateStudent(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Student Updated!").show();
                loadAllStudents();
                clearForm();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnStudentAddOnAction(ActionEvent actionEvent) {
        try {
            if (!validateInputs()) return;
            var selectedNames = txtStudentCourseList.getSelectionModel().getSelectedItems();

            ArrayList<String> courseNames = new ArrayList<>(txtStudentCourseList.getSelectionModel().getSelectedItems());


            StudentDTO dto = new StudentDTO(
                    txtStudentId.getText(),
                    txtStudentName.getText(),
                    txtStudentEmail.getText(),
                    txtStudentContact.getText(),
                    LocalDate.now(),
                    courseNames
            );

            if (studentBO.saveStudent(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Student Saved!").show();
                loadAllStudents();
                setNewStudentId();
                clearForm();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadAllCourses() {
        try {
            txtStudentCourseList.getItems().clear();
            txtStudentCourseList.getItems().addAll(
                    courseBO.getAllCourses().stream()
                            .map(CourseDTO::getCourseName)
                            .toList()
            );
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load courses: " + e.getMessage()).show();
        }
    }




    private boolean validateInputs() {
        if (txtStudentName.getText().isEmpty() || txtStudentEmail.getText().isEmpty() ||
                txtStudentContact.getText().isEmpty() || txtStudentCourseList.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "All fields must be filled including at least one course!").show();
            return false;
        }

        if (!RegexValidator.isValidEmail(txtStudentEmail.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid Email format!").show();
            return false;
        }
        if (!RegexValidator.isValidContact(txtStudentContact.getText())) {
            new Alert(Alert.AlertType.ERROR, "Contact number must be exactly 10 digits!").show();
            return false;
        }
        if (txtStudentCourseList.getSelectionModel().getSelectedItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select at least one course!").show();
            return false;
        }
        return true;
    }

    private void clearForm() {
        txtStudentName.clear();
        txtStudentEmail.clear();
        txtStudentContact.clear();
        txtStudentCourseList.getSelectionModel().clearSelection();
        txtStudentDate.setText(LocalDate.now().toString());
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