package com.student_dashboard.student_dashboard.repository;

import com.student_dashboard.student_dashboard.entity.Question;
import com.student_dashboard.student_dashboard.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByTest(Test test);
}
