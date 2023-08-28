package com.bubblebubble.hr.attendance;

public enum AttendanceType {
    ATTENDANCE("출근"),
    LEAVE("퇴근"),
    COMPLETE("완료");

    final String value;

    AttendanceType(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
