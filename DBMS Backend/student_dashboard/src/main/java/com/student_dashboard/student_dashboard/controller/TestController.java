package com.student_dashboard.student_dashboard.controller;

import com.student_dashboard.student_dashboard.entity.*;
import com.student_dashboard.student_dashboard.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/tests")
@CrossOrigin(origins = "*")
public class TestController {

    private final TestService testService;
    private final CourseService courseService;
    private final FacultyService facultyService;
    private final ResultService resultService;

    public TestController(TestService testService, CourseService courseService,
                          FacultyService facultyService, ResultService resultService) {
        this.testService = testService;
        this.courseService = courseService;
        this.facultyService = facultyService;
        this.resultService = resultService;
    }

    // ------------------ Save Test ------------------
    @PostMapping
    public ResponseEntity<?> saveTest(@RequestBody Test test) {
        try {
            if (test == null) return ResponseEntity.badRequest().body("Test data cannot be null");

            // ✅ Validate and fetch Faculty
            if (test.getFaculty() == null || test.getFaculty().getFacultyId() == 0)
                return ResponseEntity.badRequest().body("Faculty ID is required");

            Faculty faculty = facultyService.getFacultyById(test.getFaculty().getFacultyId());
            if (faculty == null) return ResponseEntity.badRequest().body("Invalid Faculty ID");
            test.setFaculty(faculty);

            // ✅ Validate and fetch Course
            if (test.getCourse() == null || test.getCourse().getCourseId() == 0)
                return ResponseEntity.badRequest().body("Course ID is required");

            Course course = courseService.getCourseById(test.getCourse().getCourseId());
            if (course == null) return ResponseEntity.badRequest().body("Invalid Course ID");
            test.setCourse(course);

            // ✅ Validate questions and link to test
            List<Question> validQuestions = new ArrayList<>();
            if (test.getQuestions() != null) {
                for (Question q : test.getQuestions()) {
                    if (q.getQuestionText() == null || q.getQuestionText().trim().isEmpty()) continue;
                    if (q.getCorrectOption() == null || q.getCorrectOption().trim().isEmpty())
                        return ResponseEntity.badRequest()
                                .body("Each question must have a correct option for: " + q.getQuestionText());
                    q.setTest(test);
                    validQuestions.add(q);
                }
            }
            test.setQuestions(validQuestions);

            Test saved = testService.saveTest(test);
            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error saving test: " + e.getMessage());
        }
    }

    // ------------------ Get All Tests ------------------
    @GetMapping
    public ResponseEntity<?> getTests(@RequestParam(required = false) Integer facultyId) {
        try {
            List<Test> tests = (facultyId != null) ?
                    testService.getTestsByFacultyId(facultyId) :
                    testService.getAllTests();

            List<Map<String, Object>> response = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            for (Test t : tests) {
                Map<String, Object> map = new HashMap<>();
                map.put("testId", t.getTestId());
                map.put("title", t.getTitle());
                map.put("description", t.getDescription());
                map.put("testDate", t.getTestDate() != null ? t.getTestDate().format(formatter) : null);
                map.put("totalMarks", t.getTotalMarks());
                map.put("courseName", t.getCourse() != null ? t.getCourse().getCourseName() : "N/A");
                map.put("questionsCount", t.getQuestions() != null ? t.getQuestions().size() : 0);
                response.add(map);
            }

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error fetching tests: " + e.getMessage());
        }
    }

    // ------------------ Get Single Test ------------------
    @GetMapping("/{testId}")
    public ResponseEntity<?> getTestById(@PathVariable int testId) {
        try {
            Test test = testService.getTestById(testId);
            if (test == null) return ResponseEntity.status(404).body("Test not found with ID: " + testId);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            Map<String, Object> map = new HashMap<>();
            map.put("testId", test.getTestId());
            map.put("title", test.getTitle());
            map.put("description", test.getDescription());
            map.put("testDate", test.getTestDate() != null ? test.getTestDate().format(formatter) : null);
            map.put("totalMarks", test.getTotalMarks());
            map.put("courseName", test.getCourse() != null ? test.getCourse().getCourseName() : "N/A");
            map.put("questions", test.getQuestions() != null ? test.getQuestions() : Collections.emptyList());

            return ResponseEntity.ok(map);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error fetching test: " + e.getMessage());
        }
    }

    // ------------------ Submit Test (FIXED) ------------------
    @PostMapping("/{testId}/submit")
    public ResponseEntity<?> submitTest(
            @PathVariable int testId,
            @RequestBody Map<String, Object> payload) {

        try {
            Test test = testService.getTestById(testId);
            if (test == null) return ResponseEntity.status(404).body("Test not found");

            // ✅ Ensure faculty is fully loaded (prevents transient null faculty errors)
            Faculty faculty = facultyService.getFacultyById(test.getFaculty().getFacultyId());
            test.setFaculty(faculty);

            Object studentIdObj = payload.get("studentId");
            Object answersObj = payload.get("answers");
            if (studentIdObj == null || answersObj == null)
                return ResponseEntity.badRequest().body("Missing studentId or answers");

            int studentId = (Integer) studentIdObj;

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> answersList = (List<Map<String, Object>>) answersObj;
            if (answersList.size() != test.getQuestions().size())
                return ResponseEntity.badRequest().body("Number of answers does not match number of questions");

            int score = 0;
            List<Question> questions = test.getQuestions();
            for (int i = 0; i < questions.size(); i++) {
                Question q = questions.get(i);
                Map<String, Object> ansMap = answersList.get(i);
                String selectedOption = (String) ansMap.get("selectedOption");
                if (selectedOption != null && q.getCorrectOption().equalsIgnoreCase(selectedOption)) {
                    score++;
                }
            }

            Result result = new Result();
            Student studentRef = new Student();
            studentRef.setStudentId(studentId);
            result.setStudent(studentRef);

            result.setTest(test);
            result.setMarksObtained(score);
            result.setMaxMarks(questions.size());
            result.setGrade(calculateGrade(score, questions.size()));
            result.setCreatedAt(LocalDateTime.now());

            resultService.saveResult(result); // ✅ Faculty now safely attached

            Map<String, Object> response = new HashMap<>();
            response.put("score", score);
            response.put("totalMarks", questions.size());
            response.put("grade", result.getGrade());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error submitting test: " + e.getMessage());
        }
    }

    // ------------------ Helper to calculate grade ------------------
    private String calculateGrade(int score, int total) {
        double percentage = ((double) score / total) * 100;
        if (percentage >= 90) return "A+";
        else if (percentage >= 80) return "A";
        else if (percentage >= 70) return "B+";
        else if (percentage >= 60) return "B";
        else if (percentage >= 50) return "C";
        else return "F";
    }
}
