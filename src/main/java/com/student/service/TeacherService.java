package com.student.service;

import com.student.dto.Teacher_DTO;
import com.student.entity.Department;
import com.student.entity.Teacher;
import com.student.repository.DepartmentRepository;
import com.student.repository.TeacherRepository;
import org.hibernate.internal.util.Optional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final DepartmentRepository departmentRepository;

    public TeacherService(TeacherRepository teacherRepository, DepartmentRepository departmentRepository) {
        this.teacherRepository = teacherRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<Teacher_DTO> findAll() {
        List<Teacher> teacher = (List<Teacher>) teacherRepository.findAll();
        return teacher.stream().map(this::toDTO).toList();
    }

    public Teacher_DTO create(Teacher_DTO teacherDTO) {
        Department department = departmentRepository
                .findById(teacherDTO.getDepartmentId())
                .orElseThrow(() ->
                        new RuntimeException("Department not found"));
        Teacher teacher = new Teacher();
        teacher.setTeacherCode(teacherDTO.getTeacherCode());
        teacher.setFirstName(teacherDTO.getFirstName());
        teacher.setLastName(teacherDTO.getLastName());
        teacher.setEmail(teacherDTO.getEmail());
        teacher.setPhone(teacherDTO.getPhone());
        teacher.setActive(teacherDTO.isActive());
        teacher.setJoiningDate(teacherDTO.getJoiningDate());
        teacher.setSpecialization(teacherDTO.getSpecialization());
        teacher.setDepartment(department);
        Teacher saved = teacherRepository.save(teacher);
        return toDTO(saved);
    }

    public Teacher_DTO findByTeacherCode(String teacherCode) {
        Teacher teacher = teacherRepository.findByTeacherCode(teacherCode);
        if(teacher == null) {
            throw new RuntimeException("Teacher not found");
        }else{
            return toDTO(teacher);
        }
    }

    public Teacher_DTO toDTO(Teacher teacher) {
        return new Teacher_DTO(teacher.getId(),teacher.getTeacherCode(),
                teacher.getFirstName(),teacher.getLastName(),teacher.getEmail(),teacher.getPhone(),teacher.getJoiningDate(),teacher.getSpecialization(),teacher.isActive(),teacher.getDepartment().getId(),teacher.getDepartment().getName());
    }
}
