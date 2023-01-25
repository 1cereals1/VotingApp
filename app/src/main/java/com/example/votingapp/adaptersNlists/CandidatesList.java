package com.example.votingapp.adaptersNlists;

public class CandidatesList {
    private final String candnumber, candname, candposition;


    public CandidatesList(String candnumber, String candname, String candposition) {
        this.candnumber = candnumber;
        this.candname = candname;
        this.candposition = candposition;
    }

    public String getCandnumber() {
        return candnumber;
    }

    public String getCandname() {
        return candname;
    }
    public String getCandposition() {
        return candposition;
    }

}
