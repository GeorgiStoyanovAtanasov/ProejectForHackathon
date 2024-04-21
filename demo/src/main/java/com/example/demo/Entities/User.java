package com.example.demo.Entities;

import com.example.demo.Constants.Role;
import com.example.demo.Constants.UserSkills;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Validated
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    @Length(min = 8)
    private String password;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    private String job;
    private String school;
    private String aboutUser;
    @Enumerated(EnumType.STRING)
    private Role role;
    @ElementCollection(targetClass = UserSkills.class)
    @Enumerated(EnumType.STRING)
    private List<UserSkills> skills = new ArrayList<>();
    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getAboutUser() {
        return aboutUser;
    }

    public void setAboutUser(String aboutUser) {
        this.aboutUser = aboutUser;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserSkills> getSkills() {
        return skills;
    }

    public void setSkills(List<UserSkills> skills) {
        this.skills = skills;
    }
}
