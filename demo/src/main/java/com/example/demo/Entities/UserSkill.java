package com.example.demo.Entities;

import com.example.demo.Constants.UserSkills;
import jakarta.persistence.*;
import org.springframework.validation.annotation.Validated;

@Validated
@Entity
public class UserSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @Enumerated(EnumType.STRING)
    private UserSkills userSkills;

    public UserSkill() {
    }

    public UserSkill(User user, UserSkills userSkills) {
        this.user = user;
        this.userSkills = userSkills;
    }
}
