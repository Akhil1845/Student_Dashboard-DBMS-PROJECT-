package com.student_dashboard.student_dashboard.repository;

import com.student_dashboard.student_dashboard.entity.Hackathon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HackathonRepository extends JpaRepository<Hackathon, Long> {
}
