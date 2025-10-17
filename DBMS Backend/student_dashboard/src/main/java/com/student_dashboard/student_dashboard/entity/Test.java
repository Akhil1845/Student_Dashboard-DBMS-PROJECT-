package com.student_dashboard.student_dashboard.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "test")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id")
    private int testId;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(name = "test_date", nullable = false)
    private LocalDate testDate;

    @Column(name = "total_marks", nullable = false)
    private int totalMarks;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    @JsonIgnoreProperties({"enrollments", "attendanceList", "tests"})
    private Course course;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "faculty_id", nullable = false)
    @JsonIgnoreProperties({"tests", "password"})
    private Faculty faculty;

    // ✅ Correct bidirectional relationship
    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("test")
    private List<Question> questions = new ArrayList<>();

    // ---------------- Getters & Setters ----------------

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getTestDate() {
        return testDate;
    }

    public void setTestDate(LocalDate testDate) {
        this.testDate = testDate;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    // ✅ Simplified — this automatically sets parent ref
    public void setQuestions(List<Question> questions) {
        this.questions.clear();
        if (questions != null) {
            for (Question q : questions) {
                q.setTest(this);
                this.questions.add(q);
            }
        }
    }

    // ✅ Helper methods for adding/removing
    public void addQuestion(Question question) {
        question.setTest(this);
        this.questions.add(question);
    }

    public void removeQuestion(Question question) {
        this.questions.remove(question);
        question.setTest(null);
    }
}
