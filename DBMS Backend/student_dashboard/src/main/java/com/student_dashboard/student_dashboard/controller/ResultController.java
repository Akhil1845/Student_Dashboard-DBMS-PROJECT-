package com.student_dashboard.student_dashboard.controller;

import com.student_dashboard.student_dashboard.entity.Result;
import com.student_dashboard.student_dashboard.service.ResultService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/results")
@CrossOrigin(origins = "*")
public class ResultController {

    private final ResultService resultService;
    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping
    public List<Result> getAllResults() {
        return resultService.getAllResults();
    }

    @GetMapping("/{id}")
    public Result getResultById(@PathVariable int id) {
        return resultService.getResultById(id);
    }

    @GetMapping("/student/{studentId}")
    public List<Result> getResultsByStudent(@PathVariable int studentId) {
        return resultService.getResultsByStudentId(studentId);
    }

    @GetMapping("/test/{testId}")
    public List<Result> getResultsByTest(@PathVariable int testId) {
        return resultService.getResultsByTestId(testId);
    }

    @PostMapping
    public Result createResult(@RequestBody Result result) {
        return resultService.saveResult(result);
    }

    @PutMapping("/{id}")
    public Result updateResult(@PathVariable int id, @RequestBody Result result) {
        result.setResultId(id);
        return resultService.saveResult(result);
    }

    @DeleteMapping("/{id}")
    public void deleteResult(@PathVariable int id) {
        resultService.deleteResult(id);
    }
}
