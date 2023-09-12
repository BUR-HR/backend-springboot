package com.bubblebubble.hr.attendance.service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bubblebubble.hr.attendance.controller.AttendanceInfoNotFoundException;
import com.bubblebubble.hr.attendance.dto.AttendanceDTO;
import com.bubblebubble.hr.attendance.dto.AttendanceStatusDTO;
import com.bubblebubble.hr.attendance.entity.Attendance;
import com.bubblebubble.hr.attendance.repository.AttendanceRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final ModelMapper modelMapper;

    public AttendanceService(AttendanceRepository attendanceRepository, ModelMapper modelMapper) {
        this.attendanceRepository = attendanceRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public AttendanceDTO insertStartDateTime(AttendanceDTO requestAttendanceDTO) {
        log.info("[AttendanceService] insertStartDateTime start =========================");
        log.info("[AttendanceService] {}", requestAttendanceDTO);

        Attendance savedAttendance = attendanceRepository.save(Attendance.create(requestAttendanceDTO));

        log.info("[AttendanceService] {}", savedAttendance);
        log.info("[AttendanceService] insertStartDateTime end =========================");
        return modelMapper.map(savedAttendance, AttendanceDTO.class);
    }

    @Transactional
    public AttendanceDTO updateEndDateTime(AttendanceDTO attendanceDTO) throws AttendanceInfoNotFoundException {
        log.info("[AttendanceService] updateEndDateTime start =========================");
        Attendance attendance = attendanceRepository
                .findTopByEmpNoAndEndDateTimeIsNull(attendanceDTO.getEmpNo())
                .orElseThrow(() -> new AttendanceInfoNotFoundException("출근 정보가 존재하지 않습니다."));

        attendance.setEndDateTime(attendanceDTO.getEndDateTime());
        attendance.setAttendanceType(attendanceDTO.getAttendanceType());
        LocalDateTime startDateTime = attendance.getStartDateTime();
        LocalDateTime endDateTime = attendance.getEndDateTime();
        LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0, 0));

        if (startDateTime.isAfter(startTime)) {
            startTime = startDateTime;
        }

        Duration duration = Duration.between(startTime, endDateTime);

        // 시간 차이를 시간 단위로 얻기
        long elapsedTimeHours = duration.toHours();

        if (elapsedTimeHours > 9) {
            attendance.setWorkTime(8);
            attendance.setOverTime(elapsedTimeHours - 9);
        } else {
            attendance.setWorkTime(elapsedTimeHours);
        }

        log.info("[AttendanceService] updateEndDateTime end =========================");
        return modelMapper.map(attendance, AttendanceDTO.class);
    }

    @Transactional
    public List<AttendanceDTO> getPrivateAttendanceList(int empNo) {
        log.info("[AttendanceService] getPrivateAttendanceList start =========================");

        // 현재 날짜와 시간 가져오기
        LocalDateTime now = LocalDateTime.now();

        // 현재 날짜의 시작 시간 계산 (요일의 시작을 월요일로 가정)
        LocalDateTime startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .with(LocalTime.MIN);

        // 현재 날짜의 마지막 시간 계산 (현재 주의 금요일)
        LocalDateTime endOfWeek = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
                .with(LocalTime.MAX);

        List<Attendance> attendanceList = attendanceRepository.findByEmpNoAndStartDateTimeBetweenOrderByNoDesc(empNo,
                startOfWeek, endOfWeek);

        log.info("[AttendanceService] getPrivateAttendanceList end =========================");
        return attendanceList.stream().map(attendance -> modelMapper.map(attendance, AttendanceDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public AttendanceStatusDTO getPrivateAttendanceStatus(int empNo) throws AttendanceInfoNotFoundException {
        log.info("[AttendanceService] getPrivateAttendanceStatus start =========================");
        LocalDateTime startDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        Attendance attendance = attendanceRepository
                .findTopByEmpNoAndStartDateTimeBetween(empNo, startDateTime, endDateTime)
                .orElseThrow(() -> new AttendanceInfoNotFoundException("출근 정보가 존재하지 않습니다."));

        log.info("[AttendanceService] attendance {}", attendance);
        log.info("[AttendanceService] getPrivateAttendanceStatus end =========================");
        return modelMapper.map(attendance, AttendanceStatusDTO.class);
    }

    @Transactional
    public List<AttendanceDTO> getAttendanceList() {

        List<Attendance> attendanceList = attendanceRepository.findAll();

        return attendanceList.stream().map(item -> modelMapper.map(item, AttendanceDTO.class))
                .collect(Collectors.toList());
    }
}
