package com.example.votingapp.adaptersNlists;

public class ACList {

    private final Integer ACMembership;
    private final String ACName;

    public ACList(Integer ACMembership, String ACName) {
        this.ACMembership = ACMembership;
        this.ACName = ACName;
    }

    public Integer getACMembership() {
        return ACMembership;
    }

    public String getACName() {
        return ACName;
    }
}
