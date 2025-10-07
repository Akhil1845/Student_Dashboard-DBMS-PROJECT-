package com.student_dashboard.student_dashboard.repository;

import com.student_dashboard.student_dashboard.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    Optional<Student> findByEmail(String email);

    Optional<Student> findByEmailAndPassword(String email, String password);
}
