package com.example.votingapp;

public class CandidatesList {
    private final String candnumber, candname;


    public CandidatesList(String candnumber, String candname) {
        this.candnumber = candnumber;
        this.candname = candname;
    }

    public String getCandnumber() {
        return candnumber;
    }

    public String getCandname() {
        return candname;
    }
}
