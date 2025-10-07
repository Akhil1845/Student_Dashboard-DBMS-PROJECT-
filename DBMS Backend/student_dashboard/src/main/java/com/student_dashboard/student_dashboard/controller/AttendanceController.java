package com.student_dashboard.student_dashboard.controller;

import com.student_dashboard.student_dashboard.entity.Attendance;
import com.student_dashboard.student_dashboard.service.AttendanceService;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController {

    private final AttendanceService attendanceService;
    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping
    public List<Attendance> getAllAttendance() {
        return attendanceService.getAllAttendance();
    }

    @GetMapping("/{id}")
    public Attendance getAttendanceById(@PathVariable int id) {
        return attendanceService.getAttendanceById(id);
    }

    @GetMapping("/student/{studentId}")
    public List<Attendance> getAttendanceByStudent(@PathVariable int studentId) {
        return attendanceService.getAttendanceByStudentId(studentId);
    }

    @GetMapping("/course/{courseId}")
    public List<Attendance> getAttendanceByCourse(@PathVariable int courseId) {
        return attendanceService.getAttendanceByCourseId(courseId);
    }

    @GetMapping("/date/{date}")
    public List<Attendance> getAttendanceByDate(@PathVariable Date date) {
        return attendanceService.getAttendanceByDate(date);
    }

    @PostMapping
    public Attendance createAttendance(@RequestBody Attendance attendance) {
        return attendanceService.saveAttendance(attendance);
    }

    @PutMapping("/{id}")
    public Attendance updateAttendance(@PathVariable int id, @RequestBody Attendance attendance) {
        attendance.setAttendanceId(id);
        return attendanceService.saveAttendance(attendance);
    }

    @DeleteMapping("/{id}")
    public void deleteAttendance(@PathVariable int id) {
        attendanceService.deleteAttendance(id);
    }
}
