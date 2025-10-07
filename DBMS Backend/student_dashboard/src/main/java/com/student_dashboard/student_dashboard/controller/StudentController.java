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

    // ---------- Existing Endpoints ----------

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable int id) {
        return studentService.getStudentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Student> getStudentByEmail(@PathVariable String email) {
        return studentService.getStudentByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public ResponseEntity<Student> registerStudent(@RequestBody Student student) {
        Student saved = studentService.saveStudent(student);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<Student> loginStudent(@RequestBody Student loginRequest) {
        return studentService.findByEmailAndPassword(
                        loginRequest.getEmail(),
                        loginRequest.getPassword())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student student) {
        student.setStudentId(id);
        Student updated = studentService.saveStudent(student);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable int id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    // ---------- NEW: Update only student stats ----------
    @PutMapping("/{id}/stats")
    public ResponseEntity<Student> updateStudentStats(
            @PathVariable int id,
            @RequestBody Student statsRequest) {

        Optional<Student> optionalStudent = studentService.getStudentById(id);
        if (optionalStudent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Student student = optionalStudent.get();

        // Only update stats fields (leave others untouched)
        student.setTestsAttempted(statsRequest.getTestsAttempted());
        student.setTestAverage(statsRequest.getTestAverage());
        student.setHackathonsParticipated(statsRequest.getHackathonsParticipated());
        student.setHackathonRating(statsRequest.getHackathonRating());

        Student updated = studentService.saveStudent(student);
        return ResponseEntity.ok(updated);
    }
}
