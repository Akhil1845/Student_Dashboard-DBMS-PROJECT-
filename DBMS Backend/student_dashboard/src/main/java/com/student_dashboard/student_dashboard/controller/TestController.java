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

import java.util.ArrayList;
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

    // ✅ Save Test along with its Questions
    @PostMapping
    public ResponseEntity<?> saveTest(@RequestBody Test test) {
        try {
            if (test == null)
                return ResponseEntity.badRequest().body("Test data cannot be null");

            // Validate and attach course
            if (test.getCourse() == null || test.getCourse().getCourseId() == 0)
                return ResponseEntity.badRequest().body("Course ID is required");
            Course course = courseService.getCourseById(test.getCourse().getCourseId());
            if (course == null)
                return ResponseEntity.badRequest().body("Invalid course ID");
            test.setCourse(course);

            // Validate and attach faculty
            if (test.getFaculty() == null || test.getFaculty().getFacultyId() == 0)
                return ResponseEntity.badRequest().body("Faculty ID is required");
            Faculty faculty = facultyService.getFacultyById(test.getFaculty().getFacultyId());
            if (faculty == null)
                return ResponseEntity.badRequest().body("Invalid faculty ID");
            test.setFaculty(faculty);

            // ✅ Validate and link questions
            List<Question> validQuestions = new ArrayList<>();
            if (test.getQuestions() != null && !test.getQuestions().isEmpty()) {
                for (Question q : test.getQuestions()) {
                    if (q.getQuestionText() == null || q.getQuestionText().trim().isEmpty()) continue;

                    if (q.getCorrectOption() == null || q.getCorrectOption().trim().isEmpty())
                        return ResponseEntity.badRequest()
                                .body("Each question must have a correct option for: " + q.getQuestionText());

                    q.setTest(test); // important link back
                    validQuestions.add(q);
                }
            }

            // ✅ Ensure questions are attached before saving
            test.setQuestions(validQuestions);

            // Save (CascadeType.ALL in Test ensures questions persist)
            Test savedTest = testService.saveTest(test);
            return ResponseEntity.ok(savedTest);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error saving test: " + e.getMessage());
        }
    }

    // ✅ Get tests by faculty ID
    @GetMapping
    public ResponseEntity<?> getTestsByFaculty(@RequestParam int facultyId) {
        try {
            List<Test> tests = testService.getTestsByFacultyId(facultyId);
            if (tests.isEmpty()) {
                return ResponseEntity.ok(List.of()); // return empty list safely
            }
            return ResponseEntity.ok(tests);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error fetching tests: " + e.getMessage());
        }
    }

    // ✅ Get single test by ID (with questions)
    @GetMapping("/{testId}")
    public ResponseEntity<?> getTestById(@PathVariable int testId) {
        try {
            Test test = testService.getTestById(testId);
            if (test == null)
                return ResponseEntity.status(404).body("Test not found with ID: " + testId);
            return ResponseEntity.ok(test);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error fetching test: " + e.getMessage());
        }
    }
}
