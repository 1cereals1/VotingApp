package com.example.votingapp.adaptersNlists;

public class ACList {

    private final String ACName;
    private final Integer ACMembership;

    public ACList(String ACName, Integer ACMembership) {
        this.ACName = ACName;
        this.ACMembership = ACMembership;
    }

    public String getACName() {
        return ACName;
    }

    public Integer getACMembership() {
        return ACMembership;
    }
}
