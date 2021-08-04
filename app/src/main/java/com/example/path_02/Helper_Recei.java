package com.example.path_02;



public class Helper_Recei{

    String contact, message, time, from;

    public Helper_Recei(){}

    public Helper_Recei(String contact, String message, String time, String from) {
        this.contact = contact;
        this.message = message;
        this.time = time;
        this.from = from;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
