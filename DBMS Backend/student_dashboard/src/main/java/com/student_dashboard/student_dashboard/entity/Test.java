package com.student_dashboard.student_dashboard.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "test")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id")
    private int testId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "test_date")
    private LocalDate testDate;

    @Column(name = "total_marks")
    private int totalMarks;

    // ---------- Relations ----------

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Result> results = new ArrayList<>();

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    // ---------- Getters & Setters ----------
    public int getTestId() { return testId; }
    public void setTestId(int testId) { this.testId = testId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getTestDate() { return testDate; }
    public void setTestDate(LocalDate testDate) { this.testDate = testDate; }

    public int getTotalMarks() { return totalMarks; }
    public void setTotalMarks(int totalMarks) { this.totalMarks = totalMarks; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public Faculty getFaculty() { return faculty; }
    public void setFaculty(Faculty faculty) { this.faculty = faculty; }

    public List<Result> getResults() { return results; }
    public void setResults(List<Result> results) {
        this.results.clear();
        if (results != null) {
            results.forEach(r -> r.setTest(this));
            this.results.addAll(results);
        }
    }

    public List<Question> getQuestions() { return questions; }
    public void setQuestions(List<Question> questions) {
        this.questions.clear();
        if (questions != null) {
            questions.forEach(q -> q.setTest(this));
            this.questions.addAll(questions);
        }
    }

    // ---------- Convenience Methods ----------
    public void addQuestion(Question question) {
        if (question != null) {
            question.setTest(this);
            this.questions.add(question);
        }
    }

    public void removeQuestion(Question question) {
        if (question != null) {
            question.setTest(null);
            this.questions.remove(question);
        }
    }

    public void addResult(Result result) {
        if (result != null) {
            result.setTest(this);
            this.results.add(result);
        }
    }

    public void removeResult(Result result) {
        if (result != null) {
            result.setTest(null);
            this.results.remove(result);
        }
    }
}
