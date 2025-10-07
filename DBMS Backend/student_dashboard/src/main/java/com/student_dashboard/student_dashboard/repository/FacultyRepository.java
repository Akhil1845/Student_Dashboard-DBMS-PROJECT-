package com.student_dashboard.student_dashboard.repository;

import com.student_dashboard.student_dashboard.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Integer> {

    // Find faculty by email
    Optional<Faculty> findByEmail(String email);

    // Find faculty by email and password (used for login)
    Optional<Faculty> findByEmailAndPassword(String email, String password);
}
