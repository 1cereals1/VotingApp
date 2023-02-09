package com.example.votingapp.adaptersNlists;

public class ECList {

    private final String ECName;
    private final Integer ECMembership;

    public ECList(String ECName, Integer ECMembership) {
        this.ECName = ECName;
        this.ECMembership = ECMembership;
    }

    public String getECName() {
        return ECName;
    }

    public Integer getECMembership() {
        return ECMembership;
    }
}
