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

    // ✅ Fetch all tests
    public List<Test> getAllTests() {
        return testRepository.findAll();
    }

    // ✅ Fetch single test by ID
    public Test getTestById(int id) {
        return testRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Test not found with ID: " + id));
    }

    // ✅ Fetch tests by faculty ID
    public List<Test> getTestsByFacultyId(int facultyId) {
        return testRepository.findByFaculty_FacultyId(facultyId);
    }

    // ✅ Save test along with its questions
    @Transactional
    public Test saveTest(Test test) {
        if (test == null)
            throw new IllegalArgumentException("Test cannot be null");

        // Validate questions
        List<Question> validQuestions = new ArrayList<>();
        if (test.getQuestions() != null) {
            for (Question q : test.getQuestions()) {
                if (q.getQuestionText() == null || q.getQuestionText().trim().isEmpty()) continue;

                if (q.getCorrectOption() == null || q.getCorrectOption().trim().isEmpty()) {
                    throw new IllegalArgumentException("Question must have a correct option: " + q.getQuestionText());
                }

                // 🔗 Ensure bidirectional link
                q.setTest(test);
                validQuestions.add(q);
            }
        }

        // Attach only valid questions
        test.setQuestions(validQuestions);

        // ✅ Save (Test entity must have CascadeType.ALL on questions)
        Test saved = testRepository.save(test);

        // Optional: flush to ensure immediate write to DB
        // testRepository.flush();

        return saved;
    }

    // ✅ Delete test safely
    @Transactional
    public boolean deleteTest(int id) {
        if (!testRepository.existsById(id))
            return false;

        testRepository.deleteById(id);
        return true;
    }
}
