package com.jeffersondeguzman.classattendance.login;

public class UserModel {
    public String userName;
    public String userEmail;
    public String userPassw;

    public UserModel(String userName, String userEmail, String userPassw){
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassw = userPassw;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPassw='" + userPassw + '\'' +
                '}';
    }
}
