package com.student_dashboard.student_dashboard.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "hackathon")
public class Hackathon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Column(name = "registration_open")
    private LocalDate registrationOpen;

    @Column(name = "prize_pool")
    private String prizePool;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "created_at", updatable = false, insertable = false)
    private java.sql.Timestamp createdAt;

    // --- Getters & Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getRegistrationOpen() { return registrationOpen; }
    public void setRegistrationOpen(LocalDate registrationOpen) { this.registrationOpen = registrationOpen; }

    public String getPrizePool() { return prizePool; }
    public void setPrizePool(String prizePool) { this.prizePool = prizePool; }

    public Integer getCreatedBy() { return createdBy; }
    public void setCreatedBy(Integer createdBy) { this.createdBy = createdBy; }

    public java.sql.Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(java.sql.Timestamp createdAt) { this.createdAt = createdAt; }
}
