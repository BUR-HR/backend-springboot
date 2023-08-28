package com.bubblebubble.hr.attendance.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bubblebubble.hr.attendance.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    List<Attendance> findByEmpNoOrderByNoDesc(int empNo);

    List<Attendance> findByEmpNo(int empNo);

    Optional<Attendance> findByEmpNoAndStartDateTime(int empNo, LocalDateTime workDate);

    Optional<Attendance> findTopByEmpNoAndStartDateTimeBetween(int empNo, LocalDateTime startDateTime,
            LocalDateTime endDateTime);

    Optional<Attendance> findTopByEmpNoAndEndDateTimeIsNull(int empNo);
}
