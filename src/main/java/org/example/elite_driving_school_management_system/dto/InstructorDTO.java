package org.example.elite_driving_school_management_system.dto;

public class InstructorDTO {
    private String instructorId;
    private String name;
    private String email;
    private String contact;
    private String availability;
    private String courseId;

    public InstructorDTO(String instructorId, String name, String email, String contact, String availability, String courseId) {
         this.instructorId = instructorId;
         this.name = name;
         this.email = email;
         this.contact = contact;
         this.availability = availability;
         this.courseId = courseId;
    }
    public String getInstructorId() {return instructorId;}
    public void setInstructorId(String instructorId) {this.instructorId = instructorId;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getContact() {return contact;}
    public void setContact(String contact) {this.contact = contact;}
    public String getAvailability() {return availability;}
    public void setAvailability(String availability) {this.availability = availability;}
    public String getCourseId() {return courseId;}
    public void setCourseId(String courseId) {this.courseId = courseId;}

}
