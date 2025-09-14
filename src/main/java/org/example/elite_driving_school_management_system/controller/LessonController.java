package org.example.elite_driving_school_management_system.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class LessonController implements Initializable {
    public TextField txtLessonID;
    public TextField txtLessonDate;
    public Button btnLessonAdd;
    public Button btnLessonUpdate;
    public Button btnLessonDelete;
    public TextField txtLessonTime;
    public TextField txtLessonDuration;
    public ComboBox txtLessonStudent;
    public ComboBox txtLessonCourse;
    public ComboBox txtLessonInstructor;
    public TableView tableLesson;
    public TableColumn columnLessonId;
    public TableColumn columnLessonDate;
    public TableColumn columnLessonTime;
    public TableColumn columnLessonDuration;
    public TableColumn columnLessonCourse;
    public TableColumn columnLessonStudent;
    public TableColumn columnLessonInstructor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void btnLessonAddOnAction(ActionEvent actionEvent) {
    }

    public void btnLessonUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnLessonDeleteOnAction(ActionEvent actionEvent) {
    }


}
