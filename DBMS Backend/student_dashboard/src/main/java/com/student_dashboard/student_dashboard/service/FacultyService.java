package com.student_dashboard.student_dashboard.service;

import com.student_dashboard.student_dashboard.entity.Course;
import com.student_dashboard.student_dashboard.entity.Faculty;
import com.student_dashboard.student_dashboard.repository.FacultyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    /** Save or update faculty, ensures courses reference faculty */
    public Faculty saveFaculty(Faculty faculty) {
        if (faculty.getCourses() != null) {
            List<Course> validCourses = faculty.getCourses().stream()
                    .filter(c -> c.getCourseName() != null && !c.getCourseName().trim().isEmpty())
                    .peek(c -> c.setFaculty(faculty)) // maintain bidirectional mapping
                    .collect(Collectors.toList());
            faculty.setCourses(validCourses);
        }
        return facultyRepository.save(faculty);
    }

    /** Get faculty by ID, throws if not found */
    public Faculty getFacultyById(int id) {
        return facultyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Faculty not found with ID: " + id));
    }

    /** Get faculty by email & password (for login) */
    public Faculty getFacultyByEmailAndPassword(String email, String password) {
        return facultyRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
    }

    /** Get all faculty */
    public List<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }

    /** Delete faculty by ID, throws if not found */
    public void deleteFaculty(int id) {
        if (!facultyRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete — faculty not found with ID: " + id);
        }
        facultyRepository.deleteById(id);
    }
}
