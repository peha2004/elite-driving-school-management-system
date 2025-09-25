package org.example.elite_driving_school_management_system.dto;

public class UserDTO {
    private String userid;
    private String username;
    private String password;
    private String role;
    public UserDTO(String userid, String username, String password, String role) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.role = role;

    }
    public String getUserid() {return userid;}
    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public String getRole() {return role;}
}

