package com.example.votingapp.adaptersNlists;

//Myitems is the list of employees
public class MyItems {
    private final Integer idnumber;
    private final String fname;

    public MyItems(Integer idnumber, String fname) {
        this.idnumber = idnumber;
        this.fname = fname;
    }

    public Integer getIdnumber() {
        return idnumber;
    }

    public String getFname() {
        return fname;
    }
}

