package com.student_dashboard.student_dashboard.controller;

import com.student_dashboard.student_dashboard.entity.Student;
import com.student_dashboard.student_dashboard.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // ---------- Register ----------
    @PostMapping("/register")
    public ResponseEntity<Student> registerStudent(@RequestBody Student student) {
        Student saved = studentService.saveStudent(student);
        return ResponseEntity.ok(saved);
    }

    // ---------- Login ----------
    @PostMapping("/login")
    public ResponseEntity<?> loginStudent(@RequestBody Student loginRequest) {
        Optional<Student> student = studentService.login(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );

        return student.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(401).body("Invalid email or password"));
    }

    // ---------- Get All Students ----------
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // ---------- Get Student by ID ----------
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable int id) {
        Optional<Student> student = studentService.getStudentById(id);
        return student.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("Student not found"));
    }
}
