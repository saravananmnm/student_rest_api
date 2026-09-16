package com.student.service;

import com.student.dto.Enrollment_DTO;
import com.student.entity.Course;
import com.student.entity.Enrollment;
import com.student.entity.Student;
import com.student.exception.ResourceNotFoundException;
import com.student.repository.CourseRepository;
import com.student.repository.EnrollmentRepository;
import com.student.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnrollmentService {
private final EnrollmentRepository enrollmentRepository;
private final StudentRepository studentRepository;
private final CourseRepository courseRepository;
public EnrollmentService(EnrollmentRepository enrollmentRepository,StudentRepository studentRepository,CourseRepository courseRepository) {
    this.enrollmentRepository = enrollmentRepository;
    this.courseRepository = courseRepository;
    this.studentRepository = studentRepository;
}
public Enrollment_DTO findById(Long id) {
    Enrollment e =
            enrollmentRepository.findById(id)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Enrollment with id "
                                            + id
                                            + " not found"
                            ));
    return new Enrollment_DTO(e.getId(),e.getStudent().getId(),e.getCourse().getId(),e.getCourse().getCourseName(),e.getStudent().getFirstName(),e.getEnrollment_date(),e.getStatus(),e.getGrade());
}
public Enrollment_DTO createEnrollment(Enrollment_DTO re) {
    Student student = studentRepository.findById(re.getStudent_id())
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Student with id "
                                    + re.getStudent_id()
                                    + " not found"
                    ));

    Course course = courseRepository.findById(re.getCourse_id())
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Course with id "
                                    + re.getCourse_id()
                                    + " not found"
                    ));

    Enrollment enrollment = new Enrollment();

    enrollment.setStudent(student);
    enrollment.setCourse(course);
    enrollment.setEnrollment_date(re.getEnrollment_date());
    enrollment.setStatus(re.getStatus());
    enrollment.setGrade(re.getGrade());
    Enrollment saved = enrollmentRepository.save(enrollment);
    return new Enrollment_DTO(saved.getId(),student.getId(),course.getId(),course.getCourseName(),student.getFirstName(),saved.getEnrollment_date(),saved.getStatus(),saved.getGrade());

}

public List<Enrollment_DTO> getEnrollment() {
    List<Enrollment_DTO> enrollmentDTOS = new ArrayList<>();
    List<Enrollment> list = (List<Enrollment>) enrollmentRepository.findAll();
    for (Enrollment e : list) {
        enrollmentDTOS.add(new Enrollment_DTO(e.getId(),e.getStudent().getId(),e.getCourse().getId(),e.getCourse().getCourseName(),e.getStudent().getFirstName(),e.getEnrollment_date(),e.getStatus(),e.getGrade()));
    }
    return enrollmentDTOS;
}
public List<Enrollment_DTO> findByStudentId(Long studentId) {
    List<Enrollment_DTO> enrollmentDTOS = new ArrayList<>();
    Student student = studentRepository.findById(studentId)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Student with id "
                                    + studentId
                                    + " not found"
                    ));
    List<Enrollment> enrollments = enrollmentRepository.findByStudentId(student.getId());
    for (Enrollment e : enrollments) {
        enrollmentDTOS.add(new Enrollment_DTO(e.getId(),e.getStudent().getId(),e.getCourse().getId(),e.getCourse().getCourseName(),e.getStudent().getFirstName(),e.getEnrollment_date(),e.getStatus(),e.getGrade()));
    }
 return  enrollmentDTOS;
}
}
