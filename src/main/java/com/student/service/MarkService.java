package com.student.service;

import com.student.dto.Mark_DTO;
import com.student.dto.Student_Result_DTO;
import com.student.entity.Course;
import com.student.entity.Mark;
import com.student.entity.Student;
import com.student.repository.CourseRepository;
import com.student.repository.MarksRepository;
import com.student.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarkService {
    private final MarksRepository marksRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public MarkService(MarksRepository marksRepository, CourseRepository courseRepository, StudentRepository studentRepository) {
        this.marksRepository = marksRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    public List<Mark_DTO> getAllMarks() {
        List<Mark> marks = marksRepository.findAll();
        return marks.stream().map(this::toMarkDTO).toList();
    }

    public Mark_DTO createMark(Mark_DTO mark) {
        Student student = studentRepository.findById(mark.getStudent_Id()).orElseThrow(() -> new RuntimeException("Student Not Found"));
        Course course = courseRepository.findById(mark.getCourse_Id()).orElseThrow(() -> new RuntimeException("Course Not Found"));
        if (mark.getInternalMarks() < 0 || mark.getInternalMarks() > 30) {
            throw new RuntimeException("Internal marks must be between 0 and 30");
        }

        if (mark.getExternalMarks() < 0 || mark.getExternalMarks() > 70) {
            throw new RuntimeException("External marks must be between 0 and 70");
        }
        Mark markEntity = new Mark();
        markEntity.setStudent(student);
        markEntity.setCourse(course);
        double totalMark = (mark.getInternalMarks() + mark.getExternalMarks());
        markEntity.setGrade(calculateGrade(totalMark));
        markEntity.setExternalMarks(mark.getExternalMarks());
        markEntity.setInternalMarks(mark.getInternalMarks());
        markEntity.setTotalMarks(totalMark);
        Mark saved = marksRepository.save(markEntity);
        return toMarkDTO(saved);
    }

    public Mark_DTO updateMark(Mark_DTO mark) {
        Mark mark1 = marksRepository.findById(mark.getId()).orElse(null);
        // Validate marks
        if (mark.getInternalMarks() < 0 ||
                mark.getInternalMarks() > 30) {

            throw new RuntimeException(
                    "Internal marks must be between 0 and 30");
        }

        if (mark.getExternalMarks() < 0 ||
                mark.getExternalMarks() > 70) {

            throw new RuntimeException(
                    "External marks must be between 0 and 70");
        }
        Student student = studentRepository.findById(mark.getStudent_Id()).orElseThrow(() -> new RuntimeException("Student Not Found"));
        Course course = courseRepository.findById(mark.getCourse_Id()).orElseThrow(() -> new RuntimeException("Course Not Found"));
        if (mark1 != null) {
            mark1.setCourse(course);
            mark1.setStudent(student);
            double totalMark = (mark.getInternalMarks() + mark.getExternalMarks());
            mark1.setGrade(calculateGrade(totalMark));
            mark1.setExternalMarks(mark.getExternalMarks());
            mark1.setInternalMarks(mark.getInternalMarks());
            mark1.setTotalMarks(totalMark);
            Mark saved = marksRepository.save(mark1);
            return toMarkDTO(saved);
        } else {
            throw new RuntimeException("Mark not found");
        }
    }

    public Mark_DTO getMarkById(Long markId) {
        Mark mark = marksRepository.findById(markId).orElseThrow(() -> new RuntimeException("Mark not found"));
        return toMarkDTO(mark);
    }

    public Student_Result_DTO getByStudentId(Long studentId) {
        Optional<Student> mark = studentRepository.findById(studentId);
        if (mark ==null || mark.isEmpty()) {
            throw new RuntimeException("Student Not Found");
        }
        List<Mark> marks = marksRepository.findByStudentId(studentId);
        if (marks==null||marks.isEmpty()) {
            throw new RuntimeException("Mark not found");
        }
        double total = 0;
        for (Mark m : marks) {
            total += m.getTotalMarks();
        }
        int subjects = marks.size();
        double average = total / subjects;
        String overallGrade = calculateGrade(total);
        return new Student_Result_DTO(studentId,mark.get().getFirstName(),subjects,total,average,average,overallGrade);
    }


    private Mark_DTO toMarkDTO(Mark mark) {
        Mark_DTO markDTO = new Mark_DTO();
        markDTO.setId(mark.getId());
        markDTO.setStudent_Id(mark.getStudent().getId());
        markDTO.setCourse_Id(mark.getCourse().getId());
        markDTO.setStudent_name(mark.getStudent().getFirstName());
        markDTO.setCourse_code(mark.getCourse().getCourseCode());
        markDTO.setCourse_name(mark.getCourse().getCourseName());
        markDTO.setExternalMarks(mark.getExternalMarks());
        markDTO.setInternalMarks(mark.getInternalMarks());
        markDTO.setGrade(mark.getGrade());
        markDTO.setTotalMarks(mark.getTotalMarks());
        return markDTO;
    }

    private String calculateGrade(double total) {

        if (total >= 90) {
            return "A+";
        } else if (total >= 80) {
            return "A";
        } else if (total >= 70) {
            return "B";
        } else if (total >= 60) {
            return "C";
        } else if (total >= 50) {
            return "D";
        } else if (total >= 40) {
            return "E";
        } else {
            return "F";
        }
    }
}
