package com.bubblebubble.hr.attendance.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bubblebubble.hr.attendance.AttendanceType;
import com.bubblebubble.hr.attendance.dto.AttendanceDTO;
import com.bubblebubble.hr.attendance.dto.AttendanceStatusDTO;
import com.bubblebubble.hr.attendance.service.AttendanceService;
import com.bubblebubble.hr.login.dto.EmployeeDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/v1/attendance")
@Slf4j
@Tag(name = "Attendance", description = "출근 API")
@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @Operation(summary = "출근 기록", description = "본인의 출근 기록을 조회한다.")
    @GetMapping
    public ResponseEntity<?> getPrivateAttendanceList(@Parameter(hidden = true) @AuthenticationPrincipal EmployeeDTO employee)
    throws AttendanceInfoNotFoundException {
        log.info("[AttendanceController] getPrivateAttendanceList start =========================");
        
        List<AttendanceDTO> attendanceList = attendanceService.getPrivateAttendanceList(employee.getEmpNo());
        
        log.info("[AttendanceController] getPrivateAttendanceList end =========================");
        
        return ResponseEntity.ok().body(attendanceList);
    }
    
    @Operation(summary = "출근 상태", description = "본인의 출근 상태를 조회한다.")
    @PostMapping("/status")
    public ResponseEntity<?> getPrivateAttendanceStatus(@Parameter(hidden = true) @AuthenticationPrincipal EmployeeDTO employee) throws AttendanceInfoNotFoundException {
        log.info("[AttendanceController] getPrivateAttendanceStatus start =========================");
        
        AttendanceStatusDTO attendance = attendanceService.getPrivateAttendanceStatus(employee.getEmpNo());
        log.info("[AttendanceController] getPrivateAttendanceStatus end =========================");

        return ResponseEntity.ok(attendance);
    }
    
    @Operation(summary = "출근", description = "출근 버튼을 누르면 출근 정보를 추가한다.")
    @PostMapping("/start")
    public ResponseEntity<?> insertAttendanceTime(
            @Parameter(hidden = true) @AuthenticationPrincipal EmployeeDTO employee) throws Exception {
        log.info("[AttendanceController] insertAttendanceTime start =========================");
        log.info("[AttendanceController] insertAttendanceTime {}", employee);
        
        AttendanceDTO requestAttendanceDTO = new AttendanceDTO();
        requestAttendanceDTO.setEmpNo(employee.getEmpNo());
        requestAttendanceDTO.setStartDateTime(LocalDateTime.now());
        requestAttendanceDTO.setAttendanceType(AttendanceType.LEAVE.value());
        log.info("[AttendanceController] insertAttendanceTime {}", requestAttendanceDTO);
        AttendanceDTO attendanceDTO = attendanceService.insertStartDateTime(requestAttendanceDTO);

        log.info("[AttendanceController] insertAttendanceTime end =========================");
        return ResponseEntity.ok().body(attendanceDTO);
    }

    @Operation(summary = "퇴근", description = "퇴근 버튼을 누르면 출근 상태를 업데이트한다.")
    @PutMapping("/end")
    public ResponseEntity<?> updateEndDateTime(@Parameter(hidden = true) @AuthenticationPrincipal EmployeeDTO employee)
            throws AttendanceInfoNotFoundException {
        log.info("[AttendanceController] updateEndDateTime start =========================");
        
        AttendanceDTO requestAttendanceDTO = new AttendanceDTO();
        requestAttendanceDTO.setEmpNo(employee.getEmpNo());
        requestAttendanceDTO.setEndDateTime(LocalDateTime.now());
        requestAttendanceDTO.setAttendanceType(AttendanceType.COMPLETE.value());
        log.info("[AttendanceController] updateEndDateTime {}", requestAttendanceDTO);
        AttendanceDTO attendanceDTO = attendanceService.updateEndDateTime(requestAttendanceDTO);
        
        log.info("[AttendanceController] updateEndDateTime start =========================");
        return ResponseEntity.ok().body(attendanceDTO);
    }
    
    @Operation(summary = "출근 현황", description = "전직원의 출근현황을 조회한다.")
    @GetMapping(value="/list")
    @PreAuthorize("hasRole('ROLE_HR_EMPLOYEE') || hasRole('ROLE_PAYROLL') || hasRole('ROLE_HR_LEADER') || hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAttendanceList() {
        log.info("[AttendanceController] getAttendanceList start =========================");
        
        List<AttendanceDTO> attendanceList = attendanceService.getAttendanceList();
        log.info("[AttendanceController] getAttendanceList end =========================");
        return ResponseEntity.ok().body(attendanceList);
    }
    
}
