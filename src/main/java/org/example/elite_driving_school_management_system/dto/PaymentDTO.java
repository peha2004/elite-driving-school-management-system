package org.example.elite_driving_school_management_system.dto;

public class PaymentDTO {
    private String paymentId;
    private String courseId;
    private double amount;
    private String paymentDate;
    private String studentId;
    private String status;

    public PaymentDTO(String paymentId, String courseId, double amount, String paymentDate, String studentId, String status) {

        this.paymentId = paymentId;
        this.courseId = courseId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.studentId = studentId;
        this.status = status;

    }
    public String getPaymentId() {return paymentId;}
    public String setPaymentId(String paymentId) {return paymentId;}
    public String getCourseId() {return courseId;}
    public String setCourseId(String courseId) {return courseId;}
    public double getAmount() {return amount;}
    public double setAmount(double amount) {return amount;}
    public String getPaymentDate() {return paymentDate;}
    public String setPaymentDate(String paymentDate) {return paymentDate;}
    public String getStudentId() {return studentId;}
    public String setStudentId(String studentId) {return studentId;}
    public String getStatus() {return status;}
    public String setStatus(String status) {return status;}

}
