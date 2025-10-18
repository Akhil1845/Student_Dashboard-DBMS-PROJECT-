package com.student_dashboard.student_dashboard.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private int studentId;

    @Column(name = "full_name")
    private String fullName;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(name = "roll_number")
    @JsonProperty("rollNumber")
    private String rollNumber;

    private String gender;

    private Date dob;

    @Column(name = "contact_no")
    @JsonProperty("contactNo")
    private String contactNo;

    @Column(name = "class_year")
    @JsonProperty("classYear")
    private String classYear;

    private String department;
    private String program;
    private String skills;

    @Column(name = "last_sem_cgpa")
    @JsonProperty("lastSemCgpa")
    private Double lastSemCgpa;

    @Column(name = "tests_attempted")
    @JsonProperty("testsAttempted")
    private Integer testsAttempted;

    @Column(name = "test_average")
    @JsonProperty("testAverage")
    private Double testAverage;

    @Column(name = "hackathons_participated")
    @JsonProperty("hackathonsParticipated")
    private Integer hackathonsParticipated;

    @Column(name = "hackathon_rating")
    @JsonProperty("hackathonRating")
    private Double hackathonRating;

    // ================= Getters & Setters =================

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRollNumber() { return rollNumber; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public Date getDob() { return dob; }
    public void setDob(Date dob) { this.dob = dob; }

    public String getContactNo() { return contactNo; }
    public void setContactNo(String contactNo) { this.contactNo = contactNo; }

    public String getClassYear() { return classYear; }
    public void setClassYear(String classYear) { this.classYear = classYear; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getProgram() { return program; }
    public void setProgram(String program) { this.program = program; }

    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    public Double getLastSemCgpa() { return lastSemCgpa; }
    public void setLastSemCgpa(Double lastSemCgpa) { this.lastSemCgpa = lastSemCgpa; }

    public Integer getTestsAttempted() { return testsAttempted; }
    public void setTestsAttempted(Integer testsAttempted) { this.testsAttempted = testsAttempted; }

    public Double getTestAverage() { return testAverage; }
    public void setTestAverage(Double testAverage) { this.testAverage = testAverage; }

    public Integer getHackathonsParticipated() { return hackathonsParticipated; }
    public void setHackathonsParticipated(Integer hackathonsParticipated) { this.hackathonsParticipated = hackathonsParticipated; }

    public Double getHackathonRating() { return hackathonRating; }
    public void setHackathonRating(Double hackathonRating) { this.hackathonRating = hackathonRating; }
}
