package org.example.elite_driving_school_management_system.dto;



import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class StudentDTO {
    private String studentID;
    private String name;
    private String email;
    private String contact;
    private LocalDate registrationDate;
    private List<String> enrolledCourses;


    public StudentDTO(String studentID, String name, String email, String contact, LocalDate registrationDate,List<String> enrolledCourses) {
       this.studentID = studentID;
       this.name = name;
       this.email = email;
       this.contact = contact;
       this.registrationDate = registrationDate;
       this.enrolledCourses = enrolledCourses;

    }
    public StudentDTO(String studentID) {
        this.studentID = studentID;
    }

       public String getStudentID() {return studentID;}
    public String getName() {return name;}
    public String getEmail() {return email;}
    public String getContact() {return contact;}
    public LocalDate getRegistrationDate() {return registrationDate;}
    public List<String> getEnrolledCourses() {return enrolledCourses;}

}
