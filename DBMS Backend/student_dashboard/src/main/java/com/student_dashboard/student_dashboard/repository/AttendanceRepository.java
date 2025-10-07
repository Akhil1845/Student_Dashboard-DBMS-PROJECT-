package com.student_dashboard.student_dashboard.repository;

import com.student_dashboard.student_dashboard.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    List<Attendance> findByStudentStudentId(int studentId);
    List<Attendance> findByCourseCourseId(int courseId);
    List<Attendance> findByDate(Date date);
}
