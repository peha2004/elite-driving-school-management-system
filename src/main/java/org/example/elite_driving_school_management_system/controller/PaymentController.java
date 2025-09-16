package org.example.elite_driving_school_management_system.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {
    public TextField txtPaymentID;
    public Button btnPaymentAdd;
    public Button btnPaymentUpdate;
    public Button btnPaymentDelete;
    public TextField txtPaymentAmount;
    public TextField txtPaymentDate;
    public TextField txtPaymentStudent;
    public ComboBox txtPaymentCourse;
    public ComboBox txtPaymentStatus;
    public TableView tablePayment;
    public TableColumn columnPaymentId;
    public TableColumn columnPaymentCourse;
    public TableColumn columnPaymentAmount;
    public TableColumn columnPaymentDate;
    public TableColumn columnPaymentStudent;
    public TableColumn columnPaymentStatus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void btnPaymentAddOnAction(ActionEvent actionEvent) {
    }

    public void btnPaymentUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnPaymentDeleteOnAction(ActionEvent actionEvent) {
    }


}
