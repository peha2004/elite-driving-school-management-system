package org.example.elite_driving_school_management_system.dto;

import java.time.LocalDate;

public class LessonDTO {
    private String lessonId;
    private LocalDate lessonDate;
    private String lessonTime;  // HH:mm:ss format
    private int duration;
    private String courseId;
    private String studentId;
    private String instructorId;

    public LessonDTO(String lessonId, LocalDate lessonDate, String lessonTime, int duration, String courseId, String studentId, String instructorId) {
        this.lessonId = lessonId;
        this.lessonDate = lessonDate;
        this.lessonTime = lessonTime;
        this.duration = duration;
        this.courseId = courseId;
        this.studentId = studentId;
        this.instructorId = instructorId;


    }
    public String getLessonId() {return lessonId;}
    public void setLessonId(String lessonId) {this.lessonId = lessonId;}
    public LocalDate getLessonDate() {return lessonDate;}
    public void setLessonDate(LocalDate lessonDate) {this.lessonDate = lessonDate;}
    public String getLessonTime() {return lessonTime;}
    public void setLessonTime(String lessonTime) {this.lessonTime = lessonTime;}
    public int getDuration() {return duration;}
    public void setDuration(int duration) {this.duration = duration;}
    public String getCourseId() {return courseId;}
    public void setCourseId(String courseId) {this.courseId = courseId;}
    public String getStudentId() {return studentId;}
    public void setStudentId(String studentId) {this.studentId = studentId;}
    public String getInstructorId() {return instructorId;}
    public void setInstructorId(String instructorId) {this.instructorId = instructorId;}

}
