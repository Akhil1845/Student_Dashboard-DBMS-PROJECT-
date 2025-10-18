package com.student_dashboard.student_dashboard.repository;

import com.student_dashboard.student_dashboard.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Integer> {
    List<Result> findByStudent_StudentId(int studentId);
    List<Result> findByTest_TestId(int testId);
}
