package com.student_dashboard.student_dashboard.service;

import com.student_dashboard.student_dashboard.entity.Course;
import com.student_dashboard.student_dashboard.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    /** Save or update a course */
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    /** Get course by ID, throws RuntimeException if not found */
    public Course getCourseById(int id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + id));
    }

    /** Get all courses */
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    /** Get courses by faculty ID */
    public List<Course> getCoursesByFaculty(int facultyId) {
        return courseRepository.findByFaculty_FacultyId(facultyId);
    }

    /** Delete course by ID, throws RuntimeException if not found */
    public void deleteCourse(int id) {
        if (!courseRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete — course not found with ID: " + id);
        }
        courseRepository.deleteById(id);
    }
}
