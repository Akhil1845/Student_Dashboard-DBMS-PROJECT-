package com.student_dashboard.student_dashboard.entity;

import jakarta.persistence.*;

@Entity
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int resultId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    private int marksObtained;
    private String grade;

    // Getters and Setters
    public int getResultId() { return resultId; }
    public void setResultId(int resultId) { this.resultId = resultId; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public Test getTest() { return test; }
    public void setTest(Test test) { this.test = test; }
    public int getMarksObtained() { return marksObtained; }
    public void setMarksObtained(int marksObtained) { this.marksObtained = marksObtained; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
}
