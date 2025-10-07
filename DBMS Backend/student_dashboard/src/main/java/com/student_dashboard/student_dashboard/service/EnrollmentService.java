package com.student_dashboard.student_dashboard.service;

import com.student_dashboard.student_dashboard.entity.Enrollment;
import com.student_dashboard.student_dashboard.repository.EnrollmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Enrollment getEnrollmentById(int id) {
        return enrollmentRepository.findById(id).orElse(null);
    }

    public List<Enrollment> getEnrollmentsByStudentId(int studentId) {
        return enrollmentRepository.findByStudentStudentId(studentId);
    }

    public List<Enrollment> getEnrollmentsByCourseId(int courseId) {
        return enrollmentRepository.findByCourseCourseId(courseId);
    }

    public Enrollment saveEnrollment(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    public void deleteEnrollment(int id) {
        enrollmentRepository.deleteById(id);
    }
}
