package com.student_dashboard.student_dashboard.service;

import com.student_dashboard.student_dashboard.entity.Result;
import com.student_dashboard.student_dashboard.repository.ResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultService {

    private final ResultRepository resultRepository;

    public ResultService(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    // Get all results
    public List<Result> getAllResults() {
        return resultRepository.findAll();
    }

    // Get a single result by ID
    public Result getResultById(int id) {
        return resultRepository.findById(id).orElse(null);
    }

    // Get all results for a specific student
    public List<Result> getResultsByStudentId(int studentId) {
        return resultRepository.findByStudent_StudentId(studentId);
    }

    // Get all results for a specific test
    public List<Result> getResultsByTestId(int testId) {
        return resultRepository.findByTest_TestId(testId);
    }

    // Save or update a result
    public Result saveResult(Result result) {
        if (result == null) return null;
        return resultRepository.save(result);
    }

    // Delete a result by ID
    public void deleteResult(int id) {
        resultRepository.deleteById(id);
    }
}
