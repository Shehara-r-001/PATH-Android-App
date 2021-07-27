package com.example.path_02;

import com.google.firebase.database.PropertyName;

public class Model {

    String achievement, date;

    public Model(){

    }

    public Model(String achievement, String date) {

        this.achievement = achievement;
        this.date = date;
    }

    //@PropertyName("Achievement")
    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }

    //@PropertyName("Date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}