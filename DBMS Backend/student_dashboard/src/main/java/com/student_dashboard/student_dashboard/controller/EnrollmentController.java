package com.student_dashboard.student_dashboard.controller;

import com.student_dashboard.student_dashboard.entity.Enrollment;
import com.student_dashboard.student_dashboard.service.EnrollmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
@CrossOrigin(origins = "*")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public List<Enrollment> getAllEnrollments() {
        return enrollmentService.getAllEnrollments();
    }

    @GetMapping("/{id}")
    public Enrollment getEnrollmentById(@PathVariable int id) {
        return enrollmentService.getEnrollmentById(id);
    }

    @GetMapping("/student/{studentId}")
    public List<Enrollment> getEnrollmentsByStudent(@PathVariable int studentId) {
        return enrollmentService.getEnrollmentsByStudentId(studentId);
    }

    @GetMapping("/course/{courseId}")
    public List<Enrollment> getEnrollmentsByCourse(@PathVariable int courseId) {
        return enrollmentService.getEnrollmentsByCourseId(courseId);
    }

    @PostMapping
    public Enrollment createEnrollment(@RequestBody Enrollment enrollment) {
        return enrollmentService.saveEnrollment(enrollment);
    }

    @PutMapping("/{id}")
    public Enrollment updateEnrollment(@PathVariable int id, @RequestBody Enrollment enrollment) {
        enrollment.setEnrollmentId(id);
        return enrollmentService.saveEnrollment(enrollment);
    }

    @DeleteMapping("/{id}")
    public void deleteEnrollment(@PathVariable int id) {
        enrollmentService.deleteEnrollment(id);
    }
}
