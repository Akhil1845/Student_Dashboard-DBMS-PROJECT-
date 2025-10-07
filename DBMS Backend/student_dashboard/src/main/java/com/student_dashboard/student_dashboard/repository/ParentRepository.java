package com.student_dashboard.student_dashboard.repository;

import com.student_dashboard.student_dashboard.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParentRepository extends JpaRepository<Parent, Integer> {
    List<Parent> findByStudentStudentId(int studentId);
}
