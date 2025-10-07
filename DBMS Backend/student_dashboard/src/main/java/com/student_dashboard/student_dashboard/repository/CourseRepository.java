package com.student_dashboard.student_dashboard.repository;

import com.student_dashboard.student_dashboard.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    // Get all courses for a specific faculty
    List<Course> findByFaculty_FacultyId(int facultyId);
}
