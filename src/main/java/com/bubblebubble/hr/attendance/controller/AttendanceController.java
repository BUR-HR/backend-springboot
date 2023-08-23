package com.bubblebubble.hr.attendance.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bubblebubble.hr.attendance.dto.AttendanceDTO;
import com.bubblebubble.hr.attendance.service.AttendanceService;
import com.bubblebubble.hr.login.dto.EmployeeDTO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/attendance")
@Slf4j
public class AttendanceController {
    
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/start")
    public ResponseEntity<?> insertAttendanceTime(@RequestBody AttendanceDTO attendance, @AuthenticationPrincipal EmployeeDTO employee) throws Exception {
        log.info("[AttendanceController] start =========================");
        log.info("[AttendanceController] {}", attendance);
        log.info("[AttendanceController] {}", employee);
        
        attendance.setEmpNo(employee.getEmpNo());
        log.info("[AttendanceController] {}", attendance);
        List<AttendanceDTO> attendanceList = attendanceService.insertAttendanceTime(attendance);
        
        log.info("[AttendanceController] end =========================");
        return ResponseEntity.ok().body(attendanceList);
    }

    @PostMapping("/end")
    public ResponseEntity<?> insertLeaveTime(@RequestBody AttendanceDTO attendance) {
        attendanceService.insertLeaveTime(attendance);

        return ResponseEntity.ok().body(true);
    }
}
