package com.bubblebubble.hr.attendance.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bubblebubble.hr.attendance.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    Optional<Attendance> findByEmpNoAndWorkDate(int empNo, Date workDate);

    List<Attendance> findByEmpNoOrderByNoDesc(int empNo);
    
}
