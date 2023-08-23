package com.bubblebubble.hr.attendance.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDTO {
    private int no;
    private int empNo;
    private Date workDate;
    private Date leaveWorkDate;
    private int overtime;
}
