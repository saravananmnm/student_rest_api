package com.student.service;

import com.student.dto.Course_DTO;
import com.student.dto.Department_DTO;
import com.student.entity.Course;
import com.student.entity.Department;
import com.student.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {

    final DepartmentRepository departmentRepository;
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

   public List<Department_DTO> findAll() {
       List<Department_DTO>  response = new ArrayList<>();
        List<Department> list = (List<Department>) departmentRepository.findAll();
       return list.stream().map(this::toResponse).toList();
   }

   public Department_DTO create(Department_DTO request) {
       Department depart = new Department();
       depart.setId(request.getDepartment_id());
       depart.setName(request.getDepartment_name());
       depart.setCode(request.getDepartment_code());
       depart.setCreationAt(request.getDepartment_create_at());
       depart.setUpdateAt(request.getDepartment_update_at());
       Department department = departmentRepository.save(depart);
       return toResponse(department);
   }
    public Department_DTO findById(Long id) {
        Department department = departmentRepository.findById(id).get();
        return toResponse(department);
    }

    private Department_DTO toResponse(Department department) {

        return new Department_DTO(
                department.getId(),
                department.getName(),
                department.getCode(),
                department.isActive(),
                department.getCreationAt(),
                department.getUpdateAt()
        );
    }
}
