package com.bubblebubble.hr.attendance.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.bubblebubble.hr.attendance.dto.AttendanceDTO;
import com.bubblebubble.hr.login.member.entity.Employee;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "TBL_ATTENDANCE")
@SequenceGenerator(name = "SEQ_ATTENDANCE", sequenceName = "SEQ_ATTENDANCE_NO", initialValue = 1, allocationSize = 1)
@NoArgsConstructor
@Getter
@ToString
public class Attendance {

    @Id
    @GeneratedValue(generator = "SEQ_ATTENDANCE", strategy = GenerationType.SEQUENCE)
    @Column(name = "ATTENDANCE_NO")
    private int no;

    @Column(name = "EMP_NO")
    private int empNo;

    @Column(name = "START_DATETIME")
    private LocalDateTime startDateTime;

    @Column(name = "END_DATETIME")
    private LocalDateTime endDateTime;

    @Column(name = "OVER_TIME")
    private long overTime;

    @Column(name = "WORK_TIME")
    private long workTime;

    @Column(name = "ATTENDANCE_TYPE")
    private String attendanceType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMP_NO", insertable = false, updatable = false)
    private Employee employee;

    @Builder
    public Attendance(int no, int empNo, LocalDateTime startDateTime, LocalDateTime endDateTime, long overTime, long workTime, String attendanceType) {
        this.no = no;
        this.empNo = empNo;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.overTime = overTime;
        this.workTime = workTime;
        this.attendanceType = attendanceType;
    }



    public static Attendance create(AttendanceDTO attendanceDTO) {
        return Attendance.builder()
                .no(attendanceDTO.getNo())
                .empNo(attendanceDTO.getEmpNo())
                .startDateTime(attendanceDTO.getStartDateTime())
                .endDateTime(attendanceDTO.getEndDateTime())
                .overTime(attendanceDTO.getOverTime())
                .workTime(attendanceDTO.getWorkTime())
                .attendanceType(attendanceDTO.getAttendanceType())
                .build();
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public void setWorkTime(long workTime) { this.workTime = workTime; }
    public void setOverTime(long overTime) {
        this.overTime = overTime;
    }

    public void setAttendanceType(String attendanceType) {
        this.attendanceType = attendanceType;
    }
}
