package com.bubblebubble.hr.attendance.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bubblebubble.hr.attendance.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

        List<Attendance> findByEmpNoOrderByNoDesc(int empNo);

        List<Attendance> findByEmpNo(int empNo);

        Optional<Attendance> findTopByEmpNoAndStartDateTimeBetween(int empNo, LocalDateTime startDateTime,
                        LocalDateTime endDateTime);

        @Override
        @Query("select m from Attendance m join fetch m.employee order by m.no desc")
        List<Attendance> findAll();

        List<Attendance> findByEmpNoAndStartDateTimeBetweenOrderByNoDesc(int empNo, LocalDateTime startOfWeek,
                        LocalDateTime endOfWeek);

        Optional<Attendance> findTopByEmpNoAndEndDateTimeIsNull(int empNo);
}
