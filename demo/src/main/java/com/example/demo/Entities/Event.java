package com.example.demo.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Validated
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime starterTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;

    public Event() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStarterTime() {
        return starterTime;
    }

    public void setStarterTime(LocalDateTime starterTime) {
        this.starterTime = starterTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
