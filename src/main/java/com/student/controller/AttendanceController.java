package com.student.controller;

import com.student.dto.Attendance_DTO;
import com.student.dto.Attendance_Summary_DTO;
import com.student.entity.Attendance;
import com.student.service.AttandanceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attandence")
public class AttendanceController {
    private AttandanceService attandanceService;
    public AttendanceController(AttandanceService attandanceService) {
        this.attandanceService = attandanceService;
    }
    @GetMapping("/getAll")
    public List<Attendance_DTO> findAll() {
        return attandanceService.findAll();
    }
    @PostMapping("/create")
    public Attendance_DTO create(@RequestBody  Attendance_DTO attendance) {
        return attandanceService.create(attendance);
    }
    @GetMapping("/findByStudentId/{student_Id}")
    public List<Attendance_DTO> findByStudentId(@PathVariable Long student_Id) {
        return attandanceService.findByStudentId(student_Id);
    }
    @PutMapping("/update")
    public Attendance_DTO update(@RequestBody Attendance_DTO attendance) {
        return attandanceService.update(attendance);
    }

    @GetMapping("/getAttandenceSummaryByStudentId/{student_Id}")
    public Attendance_Summary_DTO getAttandenceSummaryByStudentId(@PathVariable Long student_Id) {
        return attandanceService.getAttendanceSummaryByStudentId(student_Id);
    }
}
