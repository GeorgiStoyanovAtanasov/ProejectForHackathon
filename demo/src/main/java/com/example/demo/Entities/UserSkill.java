package com.example.demo.Entities;

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
    @ManyToOne
    private Skill skill;
}
