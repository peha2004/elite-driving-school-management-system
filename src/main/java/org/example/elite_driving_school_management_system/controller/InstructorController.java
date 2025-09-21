package org.example.elite_driving_school_management_system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.elite_driving_school_management_system.bo.BOFactory;
import org.example.elite_driving_school_management_system.bo.custom.CourseBO;
import org.example.elite_driving_school_management_system.bo.custom.InstructorBO;
import org.example.elite_driving_school_management_system.bo.custom.impl.InstructorBOImpl;
import org.example.elite_driving_school_management_system.dto.InstructorDTO;
import org.example.elite_driving_school_management_system.util.RegexValidator;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class InstructorController implements Initializable {
    public TextField txtInstructorID;
    public TextField txtInstructorName;
    public Button btnInstructorAdd;
    public Button btnInstructorUpdate;
    public Button btnInstructorDelete;
    public TextField txtInstructorEmail;
    public TextField txtInstructorContact;
    public ComboBox<String> txtInstructorAvailability;
    public TableView<InstructorDTO> tableInstructor;
    public TableColumn<InstructorDTO,String> columnInstructorId;
    public TableColumn<InstructorDTO,String> columnInstructorName;
    public TableColumn<InstructorDTO,String> columnInstructorEmail;
    public TableColumn<InstructorDTO,String> columnInstructorContact;
    public TableColumn<InstructorDTO,String> columnInstructorAvailability;
    public ComboBox <String> txtInstructorCourse;
    public TableColumn<InstructorDTO,String> columnInstructorCourse;

    private final InstructorBO instructorBO =  (InstructorBO) BOFactory.getInstance().getBO(BOFactory.BOType.INSTRUCTOR);
    private final CourseBO courseBO = (CourseBO) BOFactory.getInstance().getBO(BOFactory.BOType.COURSE);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnInstructorId.setCellValueFactory(new PropertyValueFactory<>("instructorId"));
        columnInstructorName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnInstructorEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnInstructorContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        columnInstructorAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));
        columnInstructorCourse.setCellValueFactory(new PropertyValueFactory<>("courseId"));

        txtInstructorAvailability.setItems(FXCollections.observableArrayList("Available", "On Leave", "Busy"));
        loadCourseIds();
        loadAllInstructors();
        txtInstructorID.setText(instructorBO.generateInstructorId());
        tableInstructor.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                fillForm(newVal);
            }
        });
    }
    private void loadAllInstructors() {
        try {
            List<InstructorDTO> list = instructorBO.getAllInstructors();
            ObservableList<InstructorDTO> obList = FXCollections.observableArrayList(list);
            tableInstructor.setItems(obList);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load instructors: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    public void btnInstructorAddOnAction(ActionEvent actionEvent) {
        if (!validateInputs()) return;

        InstructorDTO dto = new InstructorDTO(
                txtInstructorID.getText(),
                txtInstructorName.getText(),
                txtInstructorEmail.getText(),
                txtInstructorContact.getText(),
                txtInstructorAvailability.getValue(),
                txtInstructorCourse.getValue()
        );

        try {
            if (instructorBO.saveInstructor(dto)) {
                showAlert("Success", "Instructor added successfully!", Alert.AlertType.INFORMATION);
                clearForm();
                txtInstructorID.setText(instructorBO.generateInstructorId());
                loadAllInstructors();
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to add instructor: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void btnInstructorUpdateOnAction(ActionEvent actionEvent) {
        if (!validateInputs()) return;

        InstructorDTO dto = new InstructorDTO(
                txtInstructorID.getText(),
                txtInstructorName.getText(),
                txtInstructorEmail.getText(),
                txtInstructorContact.getText(),
                txtInstructorAvailability.getValue(),
                txtInstructorCourse.getValue()
        );

        try {
            if (instructorBO.updateInstructor(dto)) {
                showAlert("Success", "Instructor updated successfully!", Alert.AlertType.INFORMATION);
                clearForm();
                loadAllInstructors();
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to update instructor: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void btnInstructorDeleteOnAction(ActionEvent actionEvent) {
        String id = txtInstructorID.getText();
        if (id.isEmpty()) {
            showAlert("Validation", "Please enter an Instructor ID to delete.", Alert.AlertType.WARNING);
            return;
        }

        try {
            if (instructorBO.deleteInstructor(id)) {
                showAlert("Success", "Instructor deleted successfully!", Alert.AlertType.INFORMATION);
                clearForm();
                loadAllInstructors();
            } else {
                showAlert("Not Found", "No instructor found with ID: " + id, Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to delete instructor: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private boolean validateInputs() {
        if (txtInstructorID.getText().isEmpty() || txtInstructorName.getText().isEmpty() ||
                txtInstructorEmail.getText().isEmpty() || txtInstructorContact.getText().isEmpty() ||
                txtInstructorAvailability.getValue() == null || txtInstructorCourse.getValue() == null) {
            showAlert("Validation", "Please fill all fields.", Alert.AlertType.WARNING);
            return false;
        }

        if (!RegexValidator.isValidEmail(txtInstructorEmail.getText())) {
            showAlert("Validation", "Invalid email format.", Alert.AlertType.WARNING);
            return false;
        }

        if (!RegexValidator.isValidContact(txtInstructorContact.getText())) {
            showAlert("Validation", "Invalid contact number.", Alert.AlertType.WARNING);
            return false;
        }

        return true;
    }
    private void loadCourseIds() {
        try {
            List<String> courseIds = courseBO.getAllCourseIds();
            txtInstructorCourse.setItems(FXCollections.observableArrayList(courseIds));
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load courses: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    private void clearForm() {
//        txtInstructorID.clear();
        txtInstructorName.clear();
        txtInstructorEmail.clear();
        txtInstructorContact.clear();
        txtInstructorAvailability.setValue(null);
        txtInstructorCourse.setValue(null);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void fillForm(InstructorDTO dto) {
        txtInstructorID.setText(dto.getInstructorId());
        txtInstructorName.setText(dto.getName());
        txtInstructorEmail.setText(dto.getEmail());
        txtInstructorContact.setText(dto.getContact());
        txtInstructorAvailability.setValue(dto.getAvailability());
        txtInstructorCourse.setValue(dto.getCourseId());
    }
}
