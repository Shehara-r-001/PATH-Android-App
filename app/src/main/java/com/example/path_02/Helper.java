package com.example.path_02;

public class Helper {

    String fname, uname, email, pnumber, pword, rePword, spin;

    public Helper(){           //avoid errors in firebase

    }

    public Helper(String fname, String uname, String email, String pnumber, String pword, String rePword, String spin) {
        this.fname = fname;
        this.uname = uname;
        this.email = email;
        this.pnumber = pnumber;
        this.pword = pword;
        this.rePword = rePword;
        this.spin = spin;

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
