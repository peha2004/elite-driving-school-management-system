package org.example.elite_driving_school_management_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "lesson")
public class Lesson {
    @Id
    private String lessonId;

    @Column(nullable = false)
    private Timestamp lessonDate;

    @Column(nullable = false)
    private int duration;


    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;


    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;


    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;




}
