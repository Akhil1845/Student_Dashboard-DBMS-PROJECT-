package com.student_dashboard.student_dashboard.service;

import com.student_dashboard.student_dashboard.entity.Question;
import com.student_dashboard.student_dashboard.entity.Test;
import com.student_dashboard.student_dashboard.repository.TestRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {

    private final TestRepository testRepository;

    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    // ---------------- Get All Tests ----------------
    @Transactional(readOnly = true)
    public List<Test> getAllTests() {
        List<Test> tests = testRepository.findAll();
        tests.forEach(this::initializeTest);
        return tests;
    }

    // ---------------- Get Tests by Faculty ID ----------------
    @Transactional(readOnly = true)
    public List<Test> getTestsByFacultyId(int facultyId) {
        List<Test> tests = testRepository.findByFaculty_FacultyId(facultyId);
        tests.forEach(this::initializeTest);
        return tests;
    }

    // ---------------- Get Single Test by ID ----------------
    @Transactional(readOnly = true)
    public Test getTestById(int id) {
        Test test = testRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Test not found with ID: " + id));
        initializeTest(test);
        return test;
    }

    // ---------------- Save Test with Questions ----------------
    @Transactional
    public Test saveTest(Test test) {
        if (test == null) {
            throw new IllegalArgumentException("Test cannot be null");
        }

        // Filter valid questions and link them to test
        List<Question> validQuestions = new ArrayList<>();
        if (test.getQuestions() != null) {
            for (Question q : test.getQuestions()) {
                if (q.getQuestionText() == null || q.getQuestionText().trim().isEmpty()) continue;
                if (q.getCorrectOption() == null || q.getCorrectOption().trim().isEmpty()) {
                    throw new IllegalArgumentException("Each question must have a correct option: " + q.getQuestionText());
                }
                q.setTest(test); // Set bidirectional relationship
                validQuestions.add(q);
            }
        }
        test.setQuestions(validQuestions);

        // Save test (CascadeType.ALL ensures questions are saved)
        return testRepository.save(test);
    }

    // ---------------- Delete Test ----------------
    @Transactional
    public boolean deleteTest(int id) {
        if (!testRepository.existsById(id)) return false;
        testRepository.deleteById(id);
        return true;
    }

    // ---------------- Helper to Initialize Test ----------------
    private void initializeTest(Test test) {
        // Ensure questions list is initialized
        if (test.getQuestions() == null) {
            test.setQuestions(new ArrayList<>());
        } else {
            test.getQuestions().size(); // Force lazy load
        }

        // Avoid null issues when serializing to frontend
        if (test.getCourse() != null && test.getCourse().getCourseName() == null) {
            test.setCourse(null);
        }


        }
    }

