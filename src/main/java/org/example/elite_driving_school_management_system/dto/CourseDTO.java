package org.example.elite_driving_school_management_system.dto;

import java.util.List;

public class CourseDTO {
    private String courseId;
    private String courseName;
    private String duration;
    private double fee;
    private String description;
    private List<String> instructorIds;


    public CourseDTO(String courseId, String courseName, String duration, double fee, String description, List<String> instructorIds) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.duration = duration;
        this.fee = fee;
        this.description = description;
        this.instructorIds = instructorIds;

    }
    public String getCourseId() {return courseId;}
    public String getCourseName() {return courseName;}
    public String getDuration() {return duration;}
    public double getFee() {return fee;}
    public String getDescription() {return description;}
    public List<String> getInstructorIds() {return instructorIds;}


}
