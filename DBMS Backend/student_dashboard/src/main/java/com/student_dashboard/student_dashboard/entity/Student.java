package com.student_dashboard.student_dashboard.entity;

import jakarta.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;

    private String fullName;
    private String rollNumber;
    private String gender;
    private Date dob;
    private String email;
    private String contactNo;
    private String classYear;
    private String department;
    private String program;
    private String skills;
    private double lastSemCgpa;
    private String password;

    // ✅ New optional performance fields
    @Column(nullable = true)
    private Integer testsAttempted;

    @Column(nullable = true)
    private Double testAverage;

    @Column(nullable = true)
    private Integer hackathonsParticipated;

    @Column(nullable = true)
    private Double hackathonRating;

    // ==== Relations ====
    @OneToMany(mappedBy = "student")
    private List<Parent> parents;

    @OneToMany(mappedBy = "student")
    private List<Enrollment> enrollments;

    @OneToMany(mappedBy = "student")
    private List<Attendance> attendanceList;

    @OneToMany(mappedBy = "student")
    private List<Result> results;

    // ===== Getters & Setters =====
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getClassYear() {
        return classYear;
    }

    public void setClassYear(String classYear) {
        this.classYear = classYear;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public double getLastSemCgpa() {
        return lastSemCgpa;
    }

    public void setLastSemCgpa(double lastSemCgpa) {
        this.lastSemCgpa = lastSemCgpa;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getTestsAttempted() {
        return testsAttempted;
    }

    public void setTestsAttempted(Integer testsAttempted) {
        this.testsAttempted = testsAttempted;
    }

    public Double getTestAverage() {
        return testAverage;
    }

    public void setTestAverage(Double testAverage) {
        this.testAverage = testAverage;
    }

    public Integer getHackathonsParticipated() {
        return hackathonsParticipated;
    }

    public void setHackathonsParticipated(Integer hackathonsParticipated) {
        this.hackathonsParticipated = hackathonsParticipated;
    }

    public Double getHackathonRating() {
        return hackathonRating;
    }

    public void setHackathonRating(Double hackathonRating) {
        this.hackathonRating = hackathonRating;
    }

    public List<Parent> getParents() {
        return parents;
    }

    public void setParents(List<Parent> parents) {
        this.parents = parents;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public List<Attendance> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<Attendance> attendanceList) {
        this.attendanceList = attendanceList;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
