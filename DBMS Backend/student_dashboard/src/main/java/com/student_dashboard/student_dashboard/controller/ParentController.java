package com.student_dashboard.student_dashboard.controller;

import com.student_dashboard.student_dashboard.entity.Parent;
import com.student_dashboard.student_dashboard.service.ParentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parents")
@CrossOrigin(origins = "*")
public class ParentController {

    private final ParentService parentService;
    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    @GetMapping
    public List<Parent> getAllParents() {
        return parentService.getAllParents();
    }

    @GetMapping("/{id}")
    public Parent getParentById(@PathVariable int id) {
        return parentService.getParentById(id);
    }

    @GetMapping("/student/{studentId}")
    public List<Parent> getParentsByStudent(@PathVariable int studentId) {
        return parentService.getParentsByStudentId(studentId);
    }

    @PostMapping
    public Parent createParent(@RequestBody Parent parent) {
        return parentService.saveParent(parent);
    }

    @PutMapping("/{id}")
    public Parent updateParent(@PathVariable int id, @RequestBody Parent parent) {
        parent.setParentId(id);
        return parentService.saveParent(parent);
    }

    @DeleteMapping("/{id}")
    public void deleteParent(@PathVariable int id) {
        parentService.deleteParent(id);
    }
}
