package com.student_dashboard.student_dashboard.controller;

import com.student_dashboard.student_dashboard.entity.Course;
import com.student_dashboard.student_dashboard.entity.Faculty;
import com.student_dashboard.student_dashboard.service.CourseService;
import com.student_dashboard.student_dashboard.service.FacultyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "*")
public class CourseController {

    private final CourseService courseService;
    private final FacultyService facultyService;

    public CourseController(CourseService courseService, FacultyService facultyService) {
        this.courseService = courseService;
        this.facultyService = facultyService;
    }

    // -------------------- DTO --------------------
    public static class CourseDTO {
        private Integer courseId;
        private String courseName;
        private Integer credits;
        private String facultyName;

        public CourseDTO(Course c) {
            this.courseId = c.getCourseId();
            this.courseName = c.getCourseName();
            this.credits = c.getCredits();
            this.facultyName = c.getFaculty() != null ? c.getFaculty().getName() : "Unknown";
        }

        // getters
        public Integer getCourseId() { return courseId; }
        public String getCourseName() { return courseName; }
        public Integer getCredits() { return credits; }
        public String getFacultyName() { return facultyName; }
    }

    // -------------------- GET ALL COURSES --------------------
    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        List<CourseDTO> dtoList = courses.stream()
                .map(CourseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    // -------------------- GET COURSE BY ID --------------------
    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable int id) {
        try {
            Course course = courseService.getCourseById(id);
            return ResponseEntity.ok(new CourseDTO(course));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // -------------------- GET COURSES BY FACULTY --------------------
    @GetMapping("/faculty/{facultyId}")
    public ResponseEntity<List<CourseDTO>> getCoursesByFaculty(@PathVariable int facultyId) {
        Faculty faculty = facultyService.getFacultyById(facultyId);
        if (faculty == null) return ResponseEntity.notFound().build();

        List<CourseDTO> courses = courseService.getCoursesByFaculty(facultyId)
                .stream()
                .map(CourseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(courses);
    }

    // -------------------- CREATE COURSE --------------------
    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@RequestBody Course course) {
        if (course.getFaculty() == null || course.getFaculty().getFacultyId() == null) {
            return ResponseEntity.badRequest().build();
        }
        Faculty faculty = facultyService.getFacultyById(course.getFaculty().getFacultyId());
        if (faculty == null) return ResponseEntity.badRequest().build();

        course.setFaculty(faculty);
        Course saved = courseService.saveCourse(course);
        return ResponseEntity.ok(new CourseDTO(saved));
    }

    // -------------------- UPDATE COURSE --------------------
    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable int id, @RequestBody Course course) {
        try {
            Course existing = courseService.getCourseById(id);

            if (course.getCourseName() != null && !course.getCourseName().isEmpty())
                existing.setCourseName(course.getCourseName());

            if (course.getCredits() != null)
                existing.setCredits(course.getCredits());

            if (course.getFaculty() != null && course.getFaculty().getFacultyId() != null) {
                Faculty faculty = facultyService.getFacultyById(course.getFaculty().getFacultyId());
                if (faculty != null) existing.setFaculty(faculty);
            }

            Course updated = courseService.saveCourse(existing);
            return ResponseEntity.ok(new CourseDTO(updated));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // -------------------- DELETE COURSE --------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable int id) {
        try {
            courseService.deleteCourse(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
