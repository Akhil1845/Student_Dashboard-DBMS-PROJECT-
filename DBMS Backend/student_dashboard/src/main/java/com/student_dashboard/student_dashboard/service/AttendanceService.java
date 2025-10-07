package com.student_dashboard.student_dashboard.service;

import com.student_dashboard.student_dashboard.entity.Attendance;
import com.student_dashboard.student_dashboard.repository.AttendanceRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;

    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    public Attendance getAttendanceById(int id) {
        return attendanceRepository.findById(id).orElse(null);
    }

    public List<Attendance> getAttendanceByStudentId(int studentId) {
        return attendanceRepository.findByStudentStudentId(studentId);
    }

    public List<Attendance> getAttendanceByCourseId(int courseId) {
        return attendanceRepository.findByCourseCourseId(courseId);
    }

    public List<Attendance> getAttendanceByDate(Date date) {
        return attendanceRepository.findByDate(date);
    }

    public Attendance saveAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    public void deleteAttendance(int id) {
        attendanceRepository.deleteById(id);
    }
}
