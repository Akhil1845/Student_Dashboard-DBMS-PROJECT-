package com.student_dashboard.student_dashboard.service;

import com.student_dashboard.student_dashboard.entity.Question;
import com.student_dashboard.student_dashboard.entity.Test;
import com.student_dashboard.student_dashboard.repository.TestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TestService {

    private final TestRepository testRepository;

    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    // ---------- GET ALL TESTS ----------
    public List<Test> getAllTests() {
        return testRepository.findAll();
    }

    // ---------- GET TEST BY ID ----------
    public Test getTestById(int id) {
        return testRepository.findById(id).orElse(null);
    }

    // ---------- GET TESTS BY COURSE ----------
    public List<Test> getTestsByCourseId(int courseId) {
        return testRepository.findByCourseCourseId(courseId);
    }

    // ---------- GET TESTS BY FACULTY ----------
    public List<Test> getTestsByFacultyId(int facultyId) {
        return testRepository.findByFacultyFacultyId(facultyId);
    }

    // ---------- SAVE OR UPDATE TEST ----------
    @Transactional
    public Test saveTest(Test test) {
        // Link each question to the test safely
        if (test.getQuestions() != null && !test.getQuestions().isEmpty()) {
            test.getQuestions().forEach(q -> q.setTest(test));
        }
        return testRepository.save(test);
    }

    // ---------- DELETE TEST ----------
    @Transactional
    public boolean deleteTest(int id) {
        if (testRepository.existsById(id)) {
            testRepository.deleteById(id);
            return true;
        }
        return false; // could be used to respond with 404 in controller
    }
}
