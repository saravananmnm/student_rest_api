package com.student.controller;

import com.student.dto.Mark_DTO;
import com.student.dto.Student_Result_DTO;
import com.student.entity.Mark;
import com.student.service.MarkService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/marks")
public class MarksController {
    private final MarkService marksService;

    public MarksController(MarkService marksService) {
        this.marksService = marksService;
    }
    @GetMapping("/getAll")
    public List<Mark_DTO> getMarks() {
        return marksService.getAllMarks();
    }

    @PostMapping("/create")
    public Mark_DTO createMark(@RequestBody Mark_DTO mark) {
        return  marksService.createMark(mark);
    }

    @PutMapping("/update")
    public Mark_DTO updateMark(@RequestBody Mark_DTO mark) {
        return marksService.updateMark(mark);
    }

    @GetMapping("/getMarkById/{markId}")
    public Mark_DTO getMarkByMarkId(Long markId) {
        return marksService.getMarkById(markId);
    }

    @GetMapping("/getByStudentId/{studentId}")
    public Student_Result_DTO getByStudentId(Long studentId) {
        return marksService.getByStudentId(studentId);
    }
}
