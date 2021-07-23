package com.example.path_02;

public class Model {

    String Achievement, Date;

    public Model(String achievment, String date) {

        this.Achievement = achievment;
        this.Date = date;
    }

    public String getAchievement() {
        return Achievement;
    }

    public void setAchievement(String achievment) {
        this.Achievement = achievment;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }
}
