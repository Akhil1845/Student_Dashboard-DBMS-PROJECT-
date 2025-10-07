package com.student_dashboard.student_dashboard.controller;

import com.student_dashboard.student_dashboard.entity.Hackathon;
import com.student_dashboard.student_dashboard.repository.HackathonRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hackathons")
@CrossOrigin(origins = "http://127.0.0.1:5500") // allow your frontend
public class HackathonController {

    private final HackathonRepository repo;

    public HackathonController(HackathonRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Hackathon> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public Hackathon create(@RequestBody Hackathon h) {
        return repo.save(h);
    }
}
