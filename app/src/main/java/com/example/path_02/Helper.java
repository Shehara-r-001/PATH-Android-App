package com.example.path_02;

import android.widget.ImageView;

public class Helper {

    String fname, uname, email, pnumber, pword, rePword, spin, profile_url, uid;

    public Helper(){           //avoid errors in firebase

    }



    public Helper(String fname, String uname, String email, String pnumber, String pword, String rePword, String spin , String profile_url, String uid) {
        this.fname = fname;
        this.uname = uname;
        this.email = email;
        this.pnumber = pnumber;
        this.pword = pword;
        this.rePword = rePword;
        this.spin = spin;
        this.profile_url = profile_url;
        this.uid = uid;


    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProf_url() {
        return profile_url;
    }

    public void setProf_url(String prof_url) {
        this.profile_url = prof_url;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPnumber() {
        return pnumber;
    }

    public void setPnumber(String pnumber) {
        this.pnumber = pnumber;
    }

    public String getPword() {
        return pword;
    }

    public void setPword(String pword) {
        this.pword = pword;
    }
    public String getRePword(){
        return rePword;
    }
    public void setRePword(String rePword){
        this.rePword = rePword;
    }
    public String getSpin(){
        return spin;
    }
    public void setSpin(String spin){
        this.spin = spin;
    }


}
