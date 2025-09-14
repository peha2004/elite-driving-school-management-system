package org.example.elite_driving_school_management_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "instructors")
public class Instructor {
@Id
    private String instructorId;

    private String name;

    private String email;

    private String contact;

    private String availability;


    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
}
