package com.example.path_02;

public class Helper_Sent {

    String to , date;

    public Helper_Sent(){}

    public Helper_Sent(String to, String date) {
        this.to = to;
        this.date = date;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
