package com.jeffersondeguzman.classattendance.ui.students;

public class AttendanceDateModel {
    public int attendanceDateID;
    public String attendanceDate;
    public int studentID;

    public AttendanceDateModel(int attendanceDateID, String attendanceDate, int studentID){
        this.attendanceDateID = attendanceDateID;
        this.attendanceDate = attendanceDate;
        this.studentID = studentID;
    }

    @Override
    public String toString() {
        return "AttendanceDateModel{" +
                "attendanceDateID=" + attendanceDateID +
                ", attendanceDate='" + attendanceDate + '\'' +
                ", studentID=" + studentID +
                '}';
    }
}
