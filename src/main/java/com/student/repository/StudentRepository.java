package com.student.repository;

import com.student.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByDepartmentId(Long departmentId);
    @Query("""
    SELECT s FROM Student s
    WHERE LOWER(s.firstName) LIKE LOWER(CONCAT('%', :keyword, '%'))
       OR LOWER(s.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))
       OR LOWER(s.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
""")
    Page<Student> searchStudents(
            @Param("keyword") String keyword,
            Pageable pageable
    );

}
