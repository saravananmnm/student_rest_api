package com.student.service;

import com.student.dto.PageResponse_DTO;
import com.student.dto.Student_DTO;
import com.student.entity.Department;
import com.student.entity.Student;
import com.student.exception.ResourceNotFoundException;
import com.student.repository.DepartmentRepository;
import com.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;

    public StudentService(StudentRepository studentRepository, DepartmentRepository departmentRepository) {
        this.studentRepository = studentRepository;
        this.departmentRepository = departmentRepository;
    }

    public PageResponse_DTO<Student_DTO> getAll( int page,int size,String sortBy,String direction) {
        if (page < 0) {
           throw new IllegalArgumentException(
                    "Page cannot be negative"
            );
        }
        Sort sort = null;
        if(direction.equalsIgnoreCase("asc")) {
            sort = Sort.by(Sort.Direction.ASC,sortBy);
        }else if(direction.equalsIgnoreCase("desc")) {
            sort = Sort.by(Sort.Direction.DESC,sortBy);
        }

        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Student> studentPage = studentRepository.findAll(pageable);
        List<Student_DTO> studentDTOList = studentPage.stream().map(this::toDTO).toList();
        return new PageResponse_DTO(studentDTOList,studentPage.getNumber(),studentPage.getSize(),studentPage.getTotalElements(),studentPage.getTotalPages(),studentPage.isLast());
    }

    public Student_DTO getStudentById(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Student not found with id: " + id));
        return toDTO(student);
    }

    public Student_DTO createStudent(Student_DTO request) {
        Department department = departmentRepository
                .findById(request.getDepartmentId())
                .orElseThrow(() ->
                        new RuntimeException("Department not found"));

        Student student = new Student();

        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setDepartment(department);
        student.setAdmissionDate(request.getAdmissionDate());
        student.setGender(request.getGender());
        student.setCity(request.getCity());

        Student savedStudent = studentRepository.save(student);

        return toDTO(savedStudent);
    }

    public String deleteStudentById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        student.ifPresent(value -> studentRepository.deleteAllById(Collections.singleton(value.getId())));
        return student.isPresent() ? "Student with id " + id + " was deleted" : "Student with id " + id + " was not found";
    }

    public Student_DTO updateStudentById(Student updatedStudent) {
        Student existingStudent = studentRepository.findById(updatedStudent.getId()).orElseThrow(()-> new ResourceNotFoundException("Student not found with id: " + updatedStudent.getId()));

        if (existingStudent == null) {
            return null;
        }
        Department department = departmentRepository.findById(existingStudent.getDepartment().getId()).orElseThrow(()-> new ResourceNotFoundException("Department not found"));

        existingStudent.setFirstName(updatedStudent.getFirstName());
        existingStudent.setLastName(updatedStudent.getLastName());
        existingStudent.setEmail(updatedStudent.getEmail());
        existingStudent.setPhone(updatedStudent.getPhone());
        existingStudent.setDateOfBirth(updatedStudent.getDateOfBirth());
        existingStudent.setGender(updatedStudent.getGender());
        existingStudent.setCity(updatedStudent.getCity());
        existingStudent.setAdmissionDate(updatedStudent.getAdmissionDate());
        existingStudent.setDepartment(department);
        Student saved =  studentRepository.save(existingStudent);
        return toDTO(saved);
    }

    public Student_DTO findDepartmentById(Long id) {
        Student student= studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
        return toDTO(student);
    }

    public PageResponse_DTO<Student_DTO> searchByName(int page,int size,String sortBy) {
        Pageable pageable = PageRequest.of(page,size,Sort.by("id").ascending());
        Page<Student> studentPage = studentRepository.searchStudents(sortBy,pageable);
        List<Student_DTO> studentDTOList = studentPage.stream().map(this::toDTO).toList();
        return new PageResponse_DTO(studentDTOList,studentPage.getNumber(),studentPage.getSize(),studentPage.getTotalElements(),studentPage.getTotalPages(),studentPage.isLast());
    }

    public Student_DTO toDTO(Student savedStudent) {
        return new Student_DTO(
                savedStudent.getId(),
                savedStudent.getFirstName(),
                savedStudent.getLastName(),
                savedStudent.getEmail(),
                savedStudent.getPhone(),
                savedStudent.getDateOfBirth(),
                savedStudent.getDepartment().getId(),
                savedStudent.getDepartment().getName(),
                savedStudent.getAdmissionDate(),
                savedStudent.getGender(),
                savedStudent.getCity()
        );
    }
}
