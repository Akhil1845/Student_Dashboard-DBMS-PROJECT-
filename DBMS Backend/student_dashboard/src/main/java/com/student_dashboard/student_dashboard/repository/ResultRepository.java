package com.student_dashboard.student_dashboard.repository;

import com.student_dashboard.student_dashboard.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Integer> {
    List<Result> findByStudentStudentId(int studentId);
    List<Result> findByTestTestId(int testId);
}
