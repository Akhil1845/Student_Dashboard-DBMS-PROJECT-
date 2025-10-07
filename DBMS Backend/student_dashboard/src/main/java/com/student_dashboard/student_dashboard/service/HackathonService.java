package com.student_dashboard.student_dashboard.service;

import com.student_dashboard.student_dashboard.entity.Hackathon;
import com.student_dashboard.student_dashboard.repository.HackathonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for Hackathon CRUD operations.
 */
@Service
public class HackathonService {

    private final HackathonRepository repo;

    public HackathonService(HackathonRepository repo) {
        this.repo = repo;
    }

    // ---------- Create or Update ----------
    public Hackathon saveHackathon(Hackathon hackathon) {
        return repo.save(hackathon);
    }

    // ---------- Read ----------
    public List<Hackathon> getAllHackathons() {
        return repo.findAll();
    }

    public Hackathon getHackathonById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Hackathon not found with id " + id));
    }

    // ---------- Delete ----------
    public void deleteHackathon(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Hackathon not found with id " + id);
        }
        repo.deleteById(id);
    }
}

