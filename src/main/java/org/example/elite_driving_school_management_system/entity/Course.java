package org.example.elite_driving_school_management_system.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "courses")
public class Course {
    @Id
    private String courseId;
    private String courseName;
    private String duration;
    private double fee;
    private String description;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Instructor> instructors;

    @ManyToMany(mappedBy = "enrolledCourses")
    private List<Student> students;

    public Course(String courseId, String courseName, String duration, double fee, String description) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.duration = duration;
        this.fee = fee;
        this.description = description;

    }
    public Course(String courseId) {
        this.courseId = courseId;
    }


}
