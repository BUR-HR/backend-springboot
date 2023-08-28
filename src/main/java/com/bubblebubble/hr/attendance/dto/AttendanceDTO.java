package com.bubblebubble.hr.attendance.dto;

import java.time.LocalDateTime;

import com.bubblebubble.hr.login.dto.EmployeeDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDTO {
    private int no;

    private int empNo;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private long overTime;

    private long workTime;

    private String attendanceType;

    private EmployeeDTO employee;
}
