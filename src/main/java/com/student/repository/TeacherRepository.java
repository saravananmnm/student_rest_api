package com.student.repository;

import com.student.entity.Teacher;
import org.hibernate.internal.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface TeacherRepository extends CrudRepository<Teacher, Long> {
    Teacher findByTeacherCode(String teacherCode);
}
