package com.student.repository;

import com.student.entity.Attendance;
import com.student.entity.Course;
import com.student.entity.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AttendanceRepository extends CrudRepository<Attendance, Long> {
    List<Attendance> findByStudentId(Long studentId);
}
