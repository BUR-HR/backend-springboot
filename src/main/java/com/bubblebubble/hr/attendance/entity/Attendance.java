package com.bubblebubble.hr.attendance.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.bubblebubble.hr.attendance.dto.AttendanceDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "TBL_ATTENDANCE")
@SequenceGenerator(
    name = "SEQ_ATTENDANCE",
    sequenceName = "SEQ_ATTENDANCE_NO",
    initialValue = 1, allocationSize = 1
)
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

    @Column(name = "WORK_DATE")
    private Date workDate;

    @Column(name = "LEAVE_WORK_DATE")
    private Date leaveWorkDate;

    @Column(name = "OVERTIME")
    private int overtime;

    @Builder
    public Attendance(int no, int empNo, Date workDate, Date leaveWorkDate, int overtime) {
        this.no = no;
        this.empNo = empNo;
        this.workDate = workDate;
        this.leaveWorkDate = leaveWorkDate;
        this.overtime = overtime;
    }

    public static Attendance create(AttendanceDTO attendanceDTO) {
        return Attendance.builder()
            .no(attendanceDTO.getNo())
            .empNo(attendanceDTO.getEmpNo())
            .workDate(attendanceDTO.getWorkDate())
            .leaveWorkDate(attendanceDTO.getLeaveWorkDate())
            .overtime(attendanceDTO.getOvertime())
            .build();
    }

    public void setLeaveWorkDate(Date leaveWorkDate) {
        this.leaveWorkDate = leaveWorkDate;
    }

    public void setOvertime(int overtime) {
        this.overtime = overtime;
    }
}
