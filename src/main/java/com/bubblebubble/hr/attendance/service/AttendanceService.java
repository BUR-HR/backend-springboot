package com.bubblebubble.hr.attendance.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bubblebubble.hr.attendance.dto.AttendanceDTO;
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
    public List<AttendanceDTO> insertAttendanceTime(AttendanceDTO attendanceDTO) throws Exception {
        log.info("[AttendanceService] start =========================");
        
        try {
            attendanceRepository.saveAndFlush(Attendance.create(attendanceDTO));
            List<Attendance> attendanceList = attendanceRepository.findByEmpNoOrderByNoDesc(attendanceDTO.getEmpNo());
            
            
            log.info("[AttendanceService] {}", attendanceList);
            log.info("[AttendanceService] start =========================");
            return attendanceList.stream().map(item -> modelMapper.map(item, AttendanceDTO.class)).collect(Collectors.toList());
        } catch (Exception e) {
            e.getStackTrace();
            throw new Exception();
        }
    }

    @Transactional
    public void insertLeaveTime(AttendanceDTO attendanceDTO) {
        try {
            Optional<Attendance> attendance = attendanceRepository.findByEmpNoAndWorkDate(
                attendanceDTO.getEmpNo(),
                attendanceDTO.getWorkDate()
            );
            
            if (!attendance.isPresent()) {

                throw new AttendanceInfoNotFoundException("출근 정보가 존재하지 않습니다."); 
            }

            attendance.get().setLeaveWorkDate(attendanceDTO.getLeaveWorkDate());

            Date startDate = attendance.get().getWorkDate();
            Date endDate = attendance.get().getLeaveWorkDate();
            long elapsedTimeHours = (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60);

            if (elapsedTimeHours > 9) {
                attendance.get().setOvertime((int) (elapsedTimeHours - 9));
            }

            attendanceRepository.saveAndFlush(attendance.get());
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

}
