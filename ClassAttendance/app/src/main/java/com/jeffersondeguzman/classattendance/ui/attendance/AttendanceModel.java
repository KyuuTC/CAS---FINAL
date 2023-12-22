package com.jeffersondeguzman.classattendance.ui.attendance;

public class AttendanceModel {
    public int attendanceID;
    public String attendanceName;
    public int classNo;

    public AttendanceModel(int attendanceID, String attendanceName, int classNo){
        this.attendanceID = attendanceID;
        this.attendanceName = attendanceName;
        this.classNo = classNo;
    }

    @Override
    public String toString() {
        return "AttendanceModel{" +
                "attendanceID=" + attendanceID +
                ", attendanceName='" + attendanceName + '\'' +
                ", classNo=" + classNo +
                '}';
    }
}
