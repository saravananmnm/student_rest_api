package com.student.controller;

import com.student.dto.Enrollment_DTO;
import com.student.service.EnrollmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }
    @PostMapping("/create")
    public Enrollment_DTO createEnrollment(@RequestBody Enrollment_DTO enrollment_DTO) {
        return enrollmentService.createEnrollment(enrollment_DTO);
    }
    @GetMapping("/getAll")
    public List<Enrollment_DTO> findAllEnrollments() {
        return enrollmentService.getEnrollment();
    }
    @GetMapping("/getById/{id}")
    public Enrollment_DTO getEnrollmentById(@PathVariable Long id) {
        return enrollmentService.findById(id);
    }

    @GetMapping("/getStudentById/{id}")
    public List<Enrollment_DTO> getStudentById(@PathVariable Long id) {
        return enrollmentService.findByStudentId(id);
    }
}
