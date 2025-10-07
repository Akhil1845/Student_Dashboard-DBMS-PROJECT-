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

    public List<Result> getAllResults() {
        return resultRepository.findAll();
    }

    public Result getResultById(int id) {
        return resultRepository.findById(id).orElse(null);
    }

    public List<Result> getResultsByStudentId(int studentId) {
        return resultRepository.findByStudentStudentId(studentId);
    }

    public List<Result> getResultsByTestId(int testId) {
        return resultRepository.findByTestTestId(testId);
    }

    public Result saveResult(Result result) {
        return resultRepository.save(result);
    }

    public void deleteResult(int id) {
        resultRepository.deleteById(id);
    }
}
