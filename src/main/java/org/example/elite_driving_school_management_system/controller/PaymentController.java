package org.example.elite_driving_school_management_system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.elite_driving_school_management_system.bo.BOFactory;
import org.example.elite_driving_school_management_system.bo.custom.CourseBO;
import org.example.elite_driving_school_management_system.bo.custom.PaymentBO;
import org.example.elite_driving_school_management_system.bo.custom.StudentBO;
import org.example.elite_driving_school_management_system.dto.CourseDTO;
import org.example.elite_driving_school_management_system.dto.PaymentDTO;
import org.example.elite_driving_school_management_system.dto.StudentDTO;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {
    @FXML
    private Button btnAddPayment;

    @FXML
    private Button btnPaymentDeleteTransaction;

    @FXML
    private TableColumn<PaymentDTO, Double> columnPaymentBalance;

    @FXML
    private TableColumn<PaymentDTO, String> columnPaymentDate;

    @FXML
    private TableColumn<PaymentDTO, Double> columnPaymentPaidAmount;

    @FXML
    private TableColumn<PaymentDTO, String> columnTransactionID;

    @FXML
    private TableView<PaymentDTO> tablePayment;

    @FXML
    private Label txtPaymentAlradyPaid;

    @FXML
    private Label txtPaymentBalance;

    @FXML
    private DatePicker txtPaymentDate;

    @FXML
    private TextField txtPaymentID;

    @FXML
    private TextField txtPaymentPayNow;

    @FXML
    private Label txtPaymentStatus;

    @FXML
    private ComboBox<String> txtPaymentStudent;

    @FXML
    private Label txtPaymentTotalFee;

    private double totalFee = 0.0;
    private double alreadyPaid = 0.0;

    private final CourseBO courseBO = (CourseBO) BOFactory.getInstance().getBO(BOFactory.BOType.COURSE);
    private final StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBO(BOFactory.BOType.STUDENT);
    private final PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadStudents();
        txtPaymentDate.setValue(LocalDate.now());

        loadPayments();
        txtPaymentStatus.setText("PAID");

        try {

            txtPaymentID.setText(paymentBO.generatePaymentId());

            loadPayments();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadStudents() {
        try {
            List<String> studentIds = studentBO.getAllStudentIds();
            txtPaymentStudent.getItems().setAll(studentIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onStudentSelected(ActionEvent actionEvent) {
        try {
            String studentId = txtPaymentStudent.getValue();
            System.out.println("Selected Student: " + studentId);
            List<String> courseIds = studentBO.getEnrolledCourses(studentId);
            System.out.println("Courses: " + courseIds);
            totalFee = 0.0;

            for (String courseId : courseIds) {
                CourseDTO course = courseBO.searchWithoutInstructors(courseId);
                totalFee += course.getFee();
            }

            txtPaymentTotalFee.setText(String.valueOf(totalFee));

            List<PaymentDTO> payments = paymentBO.getPaymentsByStudent(studentId);
            payments.forEach(p -> System.out.println("Payment: " + p.getPaymentId() + " Paid: " + p.getPaidAmount()));
            alreadyPaid = payments.stream().mapToDouble(PaymentDTO::getPaidAmount).sum();

            txtPaymentAlradyPaid.setText(String.valueOf(alreadyPaid));

            double balance = totalFee - alreadyPaid;
            txtPaymentBalance.setText(String.valueOf(balance));

            if (balance <= 0) {
                txtPaymentStatus.setText("Completed");
            } else if (alreadyPaid > 0) {
                txtPaymentStatus.setText("Half Paid");
            } else {
                txtPaymentStatus.setText("Pending");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnAddPaymentOnAction(ActionEvent actionEvent) {
        try {
            String studentId = txtPaymentStudent.getValue();
            double payNow = Double.parseDouble(txtPaymentPayNow.getText());

            alreadyPaid += payNow;
            double balance = totalFee - alreadyPaid;

            String status = (balance <= 0) ? "Completed" : "Half Paid";

            PaymentDTO dto = new PaymentDTO(
                    paymentBO.generatePaymentId(),
                    studentId,
                    totalFee,
                    payNow,
                    balance,
                    status,
                    txtPaymentDate.getValue().toString()
            );
            if (paymentBO.savePayment(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Payment Added!").show();
                txtPaymentAlradyPaid.setText(String.valueOf(alreadyPaid));
                txtPaymentBalance.setText(String.valueOf(balance));
                txtPaymentStatus.setText(status);
                loadPayments();

            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error while adding payment").show();
        }
    }

    public void btnPaymentDeleteTransactionOnAction(ActionEvent actionEvent) {

        PaymentDTO selectedPayment = tablePayment.getSelectionModel().getSelectedItem();

        if (selectedPayment == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a payment to delete.").show();
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete Payment ID: " + selectedPayment.getPaymentId() + "?",
                ButtonType.YES, ButtonType.NO);

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    boolean deleted = paymentBO.deletePayment(selectedPayment.getPaymentId());
                    if (deleted) {
                        new Alert(Alert.AlertType.INFORMATION, "Payment deleted successfully!").show();
                        loadPayments();
                        clearForm();
                        txtPaymentID.setText(paymentBO.generatePaymentId());
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to delete payment.").show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Error while deleting payment: " + e.getMessage()).show();
                }
            }
        });
    }

    private void loadPayments() {
        try {
            List<PaymentDTO> list = paymentBO.getAllPayments();
            ObservableList<PaymentDTO> observableList = FXCollections.observableArrayList(list);

            columnTransactionID.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
            columnPaymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
            columnPaymentPaidAmount.setCellValueFactory(new PropertyValueFactory<>("paidAmount"));
            columnPaymentBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));

            tablePayment.getSelectionModel().clearSelection();
            tablePayment.setItems(observableList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void clearForm() {
        txtPaymentID.clear();
        txtPaymentPayNow.clear();
        txtPaymentTotalFee.setText("");
        txtPaymentAlradyPaid.setText("");
        txtPaymentBalance.setText("");
        txtPaymentDate.setValue(null);
        txtPaymentStudent.setValue(null);
        txtPaymentStatus.setText("");
    }
}


