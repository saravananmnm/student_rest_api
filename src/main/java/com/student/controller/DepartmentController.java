package com.student.controller;

import com.student.dto.Department_DTO;
import com.student.entity.Department;
import com.student.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
    @GetMapping("/getAll")
    public List<Department_DTO> getAllDepartments() {
       return departmentService.findAll();
    }

    @PostMapping("/create")
    public Department_DTO createDepartment(@Valid @RequestBody Department_DTO department) {
        return departmentService.create(department);
    }
    @GetMapping("/findById/{id}")
    public Department_DTO findById(Long id) {
        return departmentService.findById(id);
    }
}
