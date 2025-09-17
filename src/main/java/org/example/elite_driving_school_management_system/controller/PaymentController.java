package org.example.elite_driving_school_management_system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.elite_driving_school_management_system.bo.BOFactory;
import org.example.elite_driving_school_management_system.bo.custom.PaymentBO;
import org.example.elite_driving_school_management_system.dto.PaymentDTO;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {
    public TextField txtPaymentID;
    public Button btnPaymentAdd;
    public Button btnPaymentUpdate;
    public Button btnPaymentDelete;
    public TextField txtPaymentAmount;
    public TextField txtPaymentDate;
    public TextField txtPaymentStudent;
    public ComboBox<String> txtPaymentCourse;
    public ComboBox<String> txtPaymentStatus;
    public TableView<PaymentDTO> tablePayment;
    public TableColumn <PaymentDTO, String> columnPaymentId;
    public TableColumn<PaymentDTO, String> columnPaymentCourse;
    public TableColumn<PaymentDTO, String> columnPaymentAmount;
    public TableColumn<PaymentDTO, String> columnPaymentDate;
    public TableColumn<PaymentDTO, String> columnPaymentStudent;
    public TableColumn<PaymentDTO, String> columnPaymentStatus;

    private final PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadPayments();
        txtPaymentStatus.setItems(FXCollections.observableArrayList("PAID", "PENDING"));
    }

    private void loadPayments() {
        try {
            List<PaymentDTO> list = paymentBO.getAllPayments();
            ObservableList<PaymentDTO> observableList = FXCollections.observableArrayList(list);
            tablePayment.setItems(observableList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void btnPaymentAddOnAction(ActionEvent actionEvent) {
        try {
            PaymentDTO dto = new PaymentDTO(
                    txtPaymentID.getText(),
                    txtPaymentCourse.getValue(),
                    Double.parseDouble(txtPaymentAmount.getText()),
                    txtPaymentDate.getText(),
                    txtPaymentStudent.getText(),
                    txtPaymentStatus.getValue()
            );
            if (paymentBO.savePayment(dto)) {
                loadPayments();
                clearForm();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnPaymentUpdateOnAction(ActionEvent actionEvent) {
        try {
            PaymentDTO dto = new PaymentDTO(
                    txtPaymentID.getText(),
                    txtPaymentCourse.getValue(),
                    Double.parseDouble(txtPaymentAmount.getText()),
                    txtPaymentDate.getText(),
                    txtPaymentStudent.getText(),
                    txtPaymentStatus.getValue()
            );
            if (paymentBO.updatePayment(dto)) {
                loadPayments();
                clearForm();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnPaymentDeleteOnAction(ActionEvent actionEvent) {
        try {
            if (paymentBO.deletePayment(txtPaymentID.getText())) {
                loadPayments();
                clearForm();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void clearForm() {
        txtPaymentID.clear();
        txtPaymentAmount.clear();
        txtPaymentDate.clear();
        txtPaymentStudent.clear();
        txtPaymentCourse.setValue(null);
        txtPaymentStatus.setValue(null);
    }


}
