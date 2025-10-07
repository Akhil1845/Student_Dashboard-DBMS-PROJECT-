package com.student_dashboard.student_dashboard.repository;

import com.student_dashboard.student_dashboard.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    List<Enrollment> findByStudentStudentId(int studentId);
    List<Enrollment> findByCourseCourseId(int courseId);
}
