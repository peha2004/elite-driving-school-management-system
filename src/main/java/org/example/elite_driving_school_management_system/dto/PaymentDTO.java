package org.example.elite_driving_school_management_system.dto;

public class PaymentDTO {
    public String paymentId;
    public String studentId;
    public double totalFee;
    public double paidAmount;
    public double balance;
    public String status;
    public String paymentDate;

    public PaymentDTO(String paymentId, String studentId, double totalFee, double paidAmount, double balance, String status, String paymentDate) {

        this.paymentId = paymentId;
        this.studentId = studentId;
        this.totalFee = totalFee;
        this.paidAmount = paidAmount;
        this.balance = balance;
        this.status = status;
        this.paymentDate = paymentDate;

    }
   public String getPaymentId() {return paymentId;}
    public String setPaymentId(String paymentId) {this.paymentId = paymentId; return paymentId;}
    public String getStudentId() {return studentId;}
    public String setStudentId(String studentId) {this.studentId = studentId; return studentId;}
    public double getTotalFee() {return totalFee;}
    public double setTotalFee(double totalFee) {this.totalFee = totalFee; return totalFee;}
    public double getPaidAmount() {return paidAmount;}
    public double setPaidAmount(double paidAmount) {this.paidAmount = paidAmount;return paidAmount;}
    public double getBalance() {return balance;}
    public double setBalance(double balance) {this.balance = balance;return balance;}
    public String getStatus() {return status;}
    public String setStatus(String status) {this.status = status; return status;}
    public String getPaymentDate() {return paymentDate;}
    public void setPaymentDate(String paymentDate) {this.paymentDate = paymentDate;}


}
