package com.student_dashboard.student_dashboard.controller;

import com.student_dashboard.student_dashboard.entity.Question;
import com.student_dashboard.student_dashboard.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    // Create a new question for a test
    @PostMapping("/test/{testId}")
    public ResponseEntity<Question> createQuestion(@PathVariable Integer testId,
                                                   @RequestBody Question question) {
        Question savedQuestion = questionService.saveQuestion(testId, question);
        return ResponseEntity.ok(savedQuestion);
    }

    // Get all questions
    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    // Get questions by test
    @GetMapping("/test/{testId}")
    public ResponseEntity<List<Question>> getQuestionsByTest(@PathVariable Integer testId) {
        List<Question> questions = questionService.getQuestionsByTest(testId);
        return ResponseEntity.ok(questions);
    }
}
