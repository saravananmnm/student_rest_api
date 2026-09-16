package com.student.service;

import com.student.dto.Attendance_DTO;
import com.student.dto.Attendance_Summary_DTO;
import com.student.entity.Attendance;
import com.student.entity.AttendanceStatus;
import com.student.entity.Student;
import com.student.repository.AttendanceRepository;
import com.student.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttandanceService {
    private AttendanceRepository attendanceRepository;
    private StudentRepository studentRepository;

    public AttandanceService(AttendanceRepository attendanceRepository, StudentRepository studentRepository) {
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
    }
    public List<Attendance_DTO> findAll() {
        List<Attendance> attendances = (List<Attendance>) attendanceRepository.findAll();
        return  attendances.stream().map(this::toDTO).toList();
    }

    public Attendance_DTO create(Attendance_DTO attendance) {
        Student student =  studentRepository.findById(attendance.getStudent_Id()).orElseThrow(() -> new RuntimeException("Student not found"));
        Attendance attendanceEntity = new Attendance();
        attendanceEntity.setStudent(student);
        attendanceEntity.setDate(attendance.getDate());
        attendanceEntity.setStatus(AttendanceStatus.valueOf(attendance.getStatus().trim().toUpperCase()));
        Attendance saved = attendanceRepository.save(attendanceEntity);
        return toDTO(saved);
    }
    public List<Attendance_DTO> findByStudentId(Long id) {
        List<Attendance> student =  attendanceRepository.findByStudentId(id);
        if(student == null || student.isEmpty()) {
            throw new RuntimeException("Student not found");
        }else{
            return student.stream().map(this::toDTO).toList();
        }

    }

    public Attendance_DTO update(Attendance_DTO attendance) {
        Attendance attendanceEntity = attendanceRepository.findById(attendance.getId()).orElseThrow(() -> new RuntimeException("Attendance not found"));
        attendanceEntity.setDate(attendance.getDate());
        attendanceEntity.setStatus(AttendanceStatus.valueOf(attendance.getStatus().trim().toUpperCase()));
        Attendance saved = attendanceRepository.save(attendanceEntity);
        return toDTO(saved);
    }

    public Attendance_Summary_DTO getAttendanceSummaryByStudentId(Long id) {
        List<Attendance> attendance = attendanceRepository.findByStudentId(id);
        if(attendance == null || attendance.isEmpty()) {
            throw new RuntimeException("Attendance not found");
        }else {
            int totalClasses = attendance.size();

            int present = 0;
            int absent = 0;
            int late = 0;
            for (Attendance attendanceEntity : attendance) {
                if (attendanceEntity.getStatus().equals(AttendanceStatus.PRESENT)) {
                    present++;
                }
                if (attendanceEntity.getStatus().equals(AttendanceStatus.ABSENT)) {
                    absent++;
                }
                if (attendanceEntity.getStatus().equals(AttendanceStatus.LATE)) {
                    late++;
                }
            }
            double percentage = 0;
            if (totalClasses > 0) {
                percentage = ((double) present / (double) totalClasses) * 100;
            }
            return new Attendance_Summary_DTO(id, totalClasses, present, absent, late, percentage);
        }
    }

    public Attendance_DTO toDTO(Attendance attendance) {
        return new Attendance_DTO(attendance.getId(),attendance.getStudent().getId(),attendance.getDate(),attendance.getStatus().name());
    }
}
