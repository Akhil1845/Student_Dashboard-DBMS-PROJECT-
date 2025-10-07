package com.student_dashboard.student_dashboard.service;

import com.student_dashboard.student_dashboard.entity.Parent;
import com.student_dashboard.student_dashboard.repository.ParentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParentService {
    private final ParentRepository parentRepository;

    public ParentService(ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }

    public List<Parent> getAllParents() {
        return parentRepository.findAll();
    }

    public Parent getParentById(int id) {
        return parentRepository.findById(id).orElse(null);
    }

    public List<Parent> getParentsByStudentId(int studentId) {
        return parentRepository.findByStudentStudentId(studentId);
    }

    public Parent saveParent(Parent parent) {
        return parentRepository.save(parent);
    }

    public void deleteParent(int id) {
        parentRepository.deleteById(id);
    }
}
