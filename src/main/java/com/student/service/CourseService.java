package com.student.service;

import com.student.dto.Course_DTO;
import com.student.entity.Course;
import com.student.entity.Department;
import com.student.entity.Enrollment;
import com.student.exception.ResourceNotFoundException;
import com.student.repository.CourseRepository;
import com.student.repository.DepartmentRepository;
import com.student.repository.EnrollmentRepository;
import org.hibernate.internal.util.Optional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course findById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    public List<Course_DTO> findAll() {
        List<Course_DTO> list = new ArrayList<>();
        List<Course> course = (List<Course>) courseRepository.findAll();
        return course.stream().map(this::toResponse).toList();
    }

    public Course_DTO create(Course_DTO course_DTO) {
        Course course = new Course();
        course.setCourseName(course_DTO.getCourseName());
        course.setCourseCode(course_DTO.getCourseCode());
        course.setCredit(course_DTO.getCredit());
        Course saved = courseRepository.save(course);
        return toResponse(saved);
    }

    public Course_DTO findByCourseCode(String courseCode) {
        Course course = courseRepository.findByCourseCode(courseCode);
        if (course == null) {
            throw new ResourceNotFoundException("Course not found");
        }else {
            return toResponse(course);
        }

    }
    public List<Course_DTO> getByDepartment(Long id) {
        List<Course_DTO> list = new ArrayList<>();
        List<Course> course = (List<Course>) courseRepository.findByDepartmentId(id);
        if(course==null || course.isEmpty()) {
            throw new ResourceNotFoundException("Department not found");
        }else {
            return courseRepository
                    .findByDepartmentId(id)
                    .stream()
                    .map(this::toResponse)
                    .toList();
        }
    }
    private Course_DTO toResponse(Course course) {
        Department department = course.getDepartment();

        return new Course_DTO(
                course.getId(),
                course.getCourseCode(),
                course.getCourseName(),
                course.getCredit(),
                department.getId(),
                department.getName()
        );
    }
}
