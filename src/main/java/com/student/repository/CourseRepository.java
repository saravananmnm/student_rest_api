package com.student.repository;

import com.student.entity.Course;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course, Long> {
    Course findByCourseCode(String courseCode);
    List<Course> findByDepartmentId(Long departmentId);
}
