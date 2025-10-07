package com.student_dashboard.student_dashboard.service;

import com.student_dashboard.student_dashboard.entity.Student;
import com.student_dashboard.student_dashboard.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // -------- Basic CRUD --------

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(int id) {
        return studentRepository.findById(id);
    }

    public Optional<Student> getStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(int id) {
        studentRepository.deleteById(id);
    }

    public Optional<Student> findByEmailAndPassword(String email, String password) {
        return studentRepository.findByEmailAndPassword(email, password);
    }

    // -------- NEW: Update only student stats --------
    public Optional<Student> updateStudentStats(
            int id,
            Integer testsAttempted,
            Double testAverage,
            Integer hackathonsParticipated,
            Double hackathonRating) {

        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isEmpty()) {
            return Optional.empty();
        }

        Student student = optionalStudent.get();

        // Update only the stats fields
        if (testsAttempted != null) student.setTestsAttempted(testsAttempted);
        if (testAverage != null) student.setTestAverage(testAverage);
        if (hackathonsParticipated != null) student.setHackathonsParticipated(hackathonsParticipated);
        if (hackathonRating != null) student.setHackathonRating(hackathonRating);

        Student saved = studentRepository.save(student);
        return Optional.of(saved);
    }
}
