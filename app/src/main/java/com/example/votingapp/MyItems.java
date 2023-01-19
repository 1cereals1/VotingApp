package com.example.votingapp;

public class MyItems {

    private final String idnumber, fname;

    public MyItems(String idnumber, String fname) {
        this.idnumber = idnumber;
        this.fname = fname;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public String getFname() {
        return fname;
    }
}
