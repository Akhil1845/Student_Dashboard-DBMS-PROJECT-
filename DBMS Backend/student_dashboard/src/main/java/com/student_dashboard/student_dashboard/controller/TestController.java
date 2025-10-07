package com.student_dashboard.student_dashboard.controller;

import com.student_dashboard.student_dashboard.entity.Course;
import com.student_dashboard.student_dashboard.entity.Faculty;
import com.student_dashboard.student_dashboard.entity.Question;
import com.student_dashboard.student_dashboard.entity.Test;
import com.student_dashboard.student_dashboard.service.CourseService;
import com.student_dashboard.student_dashboard.service.FacultyService;
import com.student_dashboard.student_dashboard.service.TestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tests")
@CrossOrigin(origins = "*")
public class TestController {

    private final TestService testService;
    private final CourseService courseService;
    private final FacultyService facultyService;

    public TestController(TestService testService, CourseService courseService, FacultyService facultyService) {
        this.testService = testService;
        this.courseService = courseService;
        this.facultyService = facultyService;
    }

    @GetMapping
    public ResponseEntity<List<Test>> getAllTests(@RequestParam(required = false) Integer facultyId) {
        if(facultyId != null) {
            return ResponseEntity.ok(testService.getTestsByFacultyId(facultyId));
        }
        return ResponseEntity.ok(testService.getAllTests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Test> getTestById(@PathVariable int id) {
        Test test = testService.getTestById(id);
        if (test == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(test);
    }

    @PostMapping
    public ResponseEntity<Test> createTest(@RequestBody Test test) {
        // Map course and faculty safely
        if(test.getCourse() != null && test.getCourse().getCourseId() != 0) {
            Course course = courseService.getCourseById(test.getCourse().getCourseId());
            test.setCourse(course);
        }
        if(test.getFaculty() != null && test.getFaculty().getFacultyId() != 0) {
            Faculty faculty = facultyService.getFacultyById(test.getFaculty().getFacultyId());
            test.setFaculty(faculty);
        }

        // Validate questions
        if(test.getQuestions() != null) {
            for(Question q : test.getQuestions()) {
                q.setTest(test);
                if(q.getCorrectOption() == null || q.getCorrectOption().isEmpty()) {
                    throw new RuntimeException("Each question must have a correct answer");
                }
            }
        }

        Test savedTest = testService.saveTest(test);
        return ResponseEntity.ok(savedTest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Test> updateTest(@PathVariable int id, @RequestBody Test test) {
        Test existing = testService.getTestById(id);
        if(existing == null) return ResponseEntity.notFound().build();

        test.setTestId(id);

        if(test.getQuestions() != null) {
            for(Question q : test.getQuestions()) {
                q.setTest(test);
                if(q.getCorrectOption() == null || q.getCorrectOption().isEmpty()) {
                    throw new RuntimeException("Each question must have a correct answer");
                }
            }
        }

        Test updated = testService.saveTest(test);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTest(@PathVariable int id) {
        Test existing = testService.getTestById(id);
        if(existing == null) return ResponseEntity.notFound().build();
        testService.deleteTest(id);
        return ResponseEntity.noContent().build();
    }
}
