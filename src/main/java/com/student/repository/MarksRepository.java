package com.student.repository;

import com.student.entity.Mark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarksRepository extends JpaRepository<Mark,Long> {
    List<Mark> findByStudentId(Long studentId);
}
