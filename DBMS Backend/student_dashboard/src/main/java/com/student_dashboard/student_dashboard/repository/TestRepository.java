package com.student_dashboard.student_dashboard.repository;

import com.student_dashboard.student_dashboard.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Integer> {
    List<Test> findByCourseCourseId(int courseId);
    List<Test> findByFacultyFacultyId(int facultyId);


}
