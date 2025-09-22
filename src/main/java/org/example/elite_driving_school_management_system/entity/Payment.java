package org.example.elite_driving_school_management_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "payment")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Payment {
    @Id
    private String paymentId;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "total_fee", nullable = false)
    private double totalFee;

    @Column(name = "paid_amount", nullable = false)
    private double paidAmount;

    @Column(nullable = false)
    private double balance;

    private String status;

    @Temporal(TemporalType.DATE)
    private Date paymentDate;


}
