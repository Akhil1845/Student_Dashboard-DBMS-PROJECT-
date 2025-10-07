package com.student_dashboard.student_dashboard.controller;

import com.student_dashboard.student_dashboard.entity.Course;
import com.student_dashboard.student_dashboard.entity.Faculty;
import com.student_dashboard.student_dashboard.service.CourseService;
import com.student_dashboard.student_dashboard.service.FacultyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/faculty")
@CrossOrigin(origins = "*")
public class FacultyController {

    private final FacultyService facultyService;
    private final CourseService courseService;

    public FacultyController(FacultyService facultyService, CourseService courseService) {
        this.facultyService = facultyService;
        this.courseService = courseService;
    }

    // -------------------- DTO CLASSES --------------------
    public static class CourseDTO {
        public Integer courseId;
        public String courseName;
        public Integer credits;

        public CourseDTO(Course c) {
            this.courseId = c.getCourseId();
            this.courseName = c.getCourseName();
            this.credits = c.getCredits();
        }
    }

    public static class FacultyDTO {
        public Integer facultyId;
        public String name;
        public String email;
        public String department;
        public String designation;
        public String contactNo;
        public List<CourseDTO> courses;

        public FacultyDTO(Faculty f) {
            this.facultyId = f.getFacultyId();
            this.name = f.getName();
            this.email = f.getEmail();
            this.department = f.getDepartment();
            this.designation = f.getDesignation();
            this.contactNo = f.getContactNo();
            this.courses = f.getCourses() != null
                    ? f.getCourses().stream().map(CourseDTO::new).collect(Collectors.toList())
                    : new ArrayList<>();
        }
    }

    // -------------------- LOGIN --------------------
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        try {
            String email = loginRequest.get("email");
            String password = loginRequest.get("password");

            Faculty faculty = facultyService.getFacultyByEmailAndPassword(email, password);
            return ResponseEntity.ok(new FacultyDTO(faculty));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }

    // -------------------- GET FACULTY BY ID --------------------
    @GetMapping("/{id}")
    public ResponseEntity<?> getFacultyById(@PathVariable Integer id) {
        try {
            Faculty faculty = facultyService.getFacultyById(id);
            return ResponseEntity.ok(new FacultyDTO(faculty));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Faculty not found");
        }
    }

    // -------------------- UPDATE FACULTY PROFILE --------------------
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFaculty(@PathVariable Integer id, @RequestBody FacultyDTO payload) {
        try {
            Faculty faculty = facultyService.getFacultyById(id);

            // Update basic info
            faculty.setName(payload.name);
            faculty.setDepartment(payload.department);
            faculty.setDesignation(payload.designation);
            faculty.setContactNo(payload.contactNo);

            // Update courses
            if (payload.courses != null) {
                // Clear old courses and add/update new ones
                faculty.getCourses().clear();

                for (CourseDTO cDto : payload.courses) {
                    Course course;
                    if (cDto.courseId != null) {
                        course = courseService.getCourseById(cDto.courseId);
                        course.setCourseName(cDto.courseName);
                        course.setCredits(cDto.credits);
                    } else {
                        course = new Course();
                        course.setCourseName(cDto.courseName);
                        course.setCredits(cDto.credits);
                        course.setFaculty(faculty);
                    }
                    courseService.saveCourse(course);
                    faculty.getCourses().add(course);
                }
            }

            Faculty savedFaculty = facultyService.saveFaculty(faculty);
            return ResponseEntity.ok(new FacultyDTO(savedFaculty));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error updating faculty: " + e.getMessage());
        }
    }
}
