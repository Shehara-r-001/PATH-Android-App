package com.example.path_02;

public class Req_Handler {

    String contact, message, name, receiver, time;

    public Req_Handler(){

    }

    public Req_Handler(String contact, String message, String name, String receiver, String time) {
        this.contact = contact;
        this.message = message;
        this.name = name;
        this.receiver = receiver;
        this.time = time;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
