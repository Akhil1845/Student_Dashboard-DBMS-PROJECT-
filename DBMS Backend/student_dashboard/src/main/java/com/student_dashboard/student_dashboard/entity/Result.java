package com.student_dashboard.student_dashboard.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "test_results")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int resultId;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    @JsonBackReference
    private Student student;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    @JsonBackReference
    private Test test;

    @Column(name = "marks", nullable = false)
    private int marksObtained;

    @Column(name = "max_marks", nullable = false)
    private int maxMarks;

    @Column
    private String grade;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // ===== Constructors =====
    public Result() {
        this.createdAt = LocalDateTime.now();
    }

    public Result(Student student, Test test, int marksObtained, int maxMarks, String grade) {
        this.student = student;
        this.test = test;
        this.marksObtained = marksObtained;
        this.maxMarks = maxMarks;
        this.grade = grade;
        this.createdAt = LocalDateTime.now();
    }

    // ===== Getters and Setters =====
    public int getResultId() { return resultId; }
    public void setResultId(int resultId) { this.resultId = resultId; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Test getTest() { return test; }
    public void setTest(Test test) { this.test = test; }

    public int getMarksObtained() { return marksObtained; }
    public void setMarksObtained(int marksObtained) { this.marksObtained = marksObtained; }

    public int getMaxMarks() { return maxMarks; }
    public void setMaxMarks(int maxMarks) { this.maxMarks = maxMarks; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
