package com.jeffersondeguzman.classattendance.ui.students;

public class StudentsModel {
    public int studentID;
    public int studentAbsents;
    public String studentFirstName;
    public String studentLastName;
    public String studentDate;
    public int studentStat;
    public int attendanceID;


    public StudentsModel(int studentID, int studentAbsents, String studentFirstName, String studentLastName, String studentDate, int studentStat, int attendanceID){
        this.studentID = studentID;
        this.studentAbsents = studentAbsents;
        this.studentFirstName = studentFirstName;
        this.studentLastName = studentLastName;
        this.studentDate = studentDate;
        this.studentStat = studentStat;
        this.attendanceID = attendanceID;
    }

    @Override
    public String toString() {
        return "StudentsModel{" +
                "studentID=" + studentID +
                ", studentAbsents=" + studentAbsents +
                ", studentFirstName='" + studentFirstName + '\'' +
                ", studentLastName='" + studentLastName + '\'' +
                ", attendanceID=" + attendanceID +
                ", studentDate='" + studentDate + '\'' +
                ", studentStat=" + studentStat +
                '}';
    }
}
