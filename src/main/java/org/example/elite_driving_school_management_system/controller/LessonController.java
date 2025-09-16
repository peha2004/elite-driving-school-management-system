package org.example.elite_driving_school_management_system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.elite_driving_school_management_system.bo.BOFactory;
import org.example.elite_driving_school_management_system.bo.custom.LessonBO;
import org.example.elite_driving_school_management_system.dto.LessonDTO;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class LessonController implements Initializable {
    public TextField txtLessonID;
    public TextField txtLessonDate;
    public Button btnLessonAdd;
    public Button btnLessonUpdate;
    public Button btnLessonDelete;
    public TextField txtLessonTime;
    public TextField txtLessonDuration;
    public ComboBox<String> txtLessonStudent;
    public ComboBox<String> txtLessonCourse;
    public ComboBox<String> txtLessonInstructor;
    public TableView<LessonDTO> tableLesson;
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
        txtLessonStudent.setItems(FXCollections.observableArrayList("S001","S002"));
        txtLessonCourse.setItems(FXCollections.observableArrayList("C001","C002"));
        txtLessonInstructor.setItems(FXCollections.observableArrayList("I001","I002"));
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
                    LocalDate.parse(txtLessonDate.getText()),
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
                    LocalDate.parse(txtLessonDate.getText()),
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
