package org.example.elite_driving_school_management_system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.elite_driving_school_management_system.bo.BOFactory;
import org.example.elite_driving_school_management_system.bo.custom.LessonBO;
import org.example.elite_driving_school_management_system.dto.LessonDTO;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class LessonController implements Initializable {
    public TextField txtLessonID;
    public Button btnLessonAdd;
    public Button btnLessonUpdate;
    public Button btnLessonDelete;
    public TextField txtLessonTime;
    public TextField txtLessonDuration;
    public ComboBox<String> txtLessonStudent;
    public ComboBox<String> txtLessonCourse;
    public ComboBox<String> txtLessonInstructor;
    public TableView<LessonDTO> tableLesson;
    public DatePicker txtLessonDate;
    public TableColumn<LessonDTO, String> columnLessonId;
    public TableColumn <LessonDTO, String>columnLessonDate;
    public TableColumn <LessonDTO, String>columnLessonTime;
    public TableColumn <LessonDTO, String>columnLessonDuration;
    public TableColumn <LessonDTO, String>columnLessonCourse;
    public TableColumn <LessonDTO, String>columnLessonStudent;
    public TableColumn<LessonDTO, String> columnLessonInstructor;

    private final LessonBO lessonBO = (LessonBO) BOFactory.getInstance().getBO(BOFactory.BOType.LESSON);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        columnLessonId.setCellValueFactory(new PropertyValueFactory<>("lessonId"));
        columnLessonDate.setCellValueFactory(new PropertyValueFactory<>("lessonDate"));
        columnLessonTime.setCellValueFactory(new PropertyValueFactory<>("lessonTime"));
        columnLessonDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        columnLessonCourse.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        columnLessonStudent.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        columnLessonInstructor.setCellValueFactory(new PropertyValueFactory<>("instructorId"));

        loadAllLessons();
        generateNextLessonId();
        loadComboData();

    }

    private void generateNextLessonId() {
        try {
            txtLessonID.setText(lessonBO.generateNextLessonId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadComboData() {
        try {
            List<String> students = lessonBO.getAllStudentIds();
            txtLessonStudent.setItems(FXCollections.observableArrayList(students));
            List<String> courses = lessonBO.getAllCourseIds();
            txtLessonCourse.setItems(FXCollections.observableArrayList(courses));

            List<String> instructors = lessonBO.getAllInstructorIds();
            txtLessonInstructor.setItems(FXCollections.observableArrayList(instructors));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAllLessons() {
        try {
            tableLesson.getItems().clear();
            List<LessonDTO> lessons = lessonBO.getAllLessons();
            tableLesson.getItems().addAll(lessons);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnLessonAddOnAction(ActionEvent actionEvent) {
        try {
            LessonDTO dto = new LessonDTO(
                    txtLessonID.getText(),
                    txtLessonDate.getValue(),
                    txtLessonTime.getText(),
                    Integer.parseInt(txtLessonDuration.getText()),
                    txtLessonCourse.getValue(),
                    txtLessonStudent.getValue(),
                    txtLessonInstructor.getValue()
            );
            if (lessonBO.saveLesson(dto)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Lesson Saved!").show();
                loadAllLessons();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error saving lesson!").show();
        }
    }

    public void btnLessonUpdateOnAction(ActionEvent actionEvent) {
        try {
            LessonDTO dto = new LessonDTO(
                    txtLessonID.getText(),
                    txtLessonDate.getValue(),
                    txtLessonTime.getText(),
                    Integer.parseInt(txtLessonDuration.getText()),
                    txtLessonCourse.getValue(),
                    txtLessonStudent.getValue(),
                    txtLessonInstructor.getValue()
            );
            if (lessonBO.updateLesson(dto)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Lesson Updated!").show();
                loadAllLessons();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error updating lesson!").show();
        }
    }

    public void btnLessonDeleteOnAction(ActionEvent actionEvent) {
        try {
            String id = txtLessonID.getText();
            if (lessonBO.deleteLesson(id)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Lesson Deleted!").show();
                loadAllLessons();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error deleting lesson!").show();
        }
    }


}
