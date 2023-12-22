package com.jeffersondeguzman.classattendance.ui.classUI;

public class ClassModel {
    public int classID;
    public String className;
    public String classSubject;

    public ClassModel(int classID, String className, String classSubject){
        this.classID = classID;
        this.className = className;
        this.classSubject = classSubject;
    }

    @Override
    public String toString() {
        return "ClassModel{" +
                "classID=" + classID +
                ", className='" + className + '\'' +
                ", classSubject='" + classSubject + '\'' +
                '}';
    }
}
