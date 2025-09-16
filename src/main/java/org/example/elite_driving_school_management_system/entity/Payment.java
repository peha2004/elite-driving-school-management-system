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
    @JoinColumn(name = "courseId")
    private Course course;

    private double amount;
    private Date paymentDate;


    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private String status;


}
