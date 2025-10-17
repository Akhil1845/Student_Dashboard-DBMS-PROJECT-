package com.student_dashboard.student_dashboard.service;

import com.student_dashboard.student_dashboard.entity.Question;
import com.student_dashboard.student_dashboard.entity.Test;
import com.student_dashboard.student_dashboard.repository.QuestionRepository;
import com.student_dashboard.student_dashboard.repository.TestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final TestRepository testRepository;

    public QuestionService(QuestionRepository questionRepository, TestRepository testRepository) {
        this.questionRepository = questionRepository;
        this.testRepository = testRepository;
    }

    // Save a single question linked to a test
    @Transactional
    public Question saveQuestion(Integer testId, Question question) {
        if (question == null)
            throw new IllegalArgumentException("Question cannot be null");

        if (question.getQuestionText() == null || question.getQuestionText().trim().isEmpty())
            throw new IllegalArgumentException("Question text cannot be empty");

        if (question.getCorrectOption() == null || question.getCorrectOption().trim().isEmpty())
            throw new IllegalArgumentException("Correct option cannot be empty");

        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test not found with ID: " + testId));

        question.setTest(test); // Link question to test
        return questionRepository.save(question);
    }

    // Save multiple questions for a test
    @Transactional
    public List<Question> saveQuestions(Integer testId, List<Question> questions) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test not found with ID: " + testId));

        for (Question q : questions) {
            if (q.getQuestionText() == null || q.getQuestionText().trim().isEmpty())
                continue; // Skip empty questions

            if (q.getCorrectOption() == null || q.getCorrectOption().trim().isEmpty())
                throw new IllegalArgumentException("Correct option cannot be empty for question: " + q.getQuestionText());

            q.setTest(test); // Link to parent test
        }

        return questionRepository.saveAll(questions);
    }

    // Get all questions
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    // Get questions by test
    public List<Question> getQuestionsByTest(Integer testId) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test not found with ID: " + testId));
        return questionRepository.findAllByTest(test);
    }

    // Optional: Get a question by ID
    public Optional<Question> getQuestionById(Integer id) {
        return questionRepository.findById(id);
    }
}
