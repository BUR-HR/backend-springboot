package com.bubblebubble.hr.attendance.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceStatusDTO {
    private LocalDateTime startDateTime;
    private String attendanceType;
}
