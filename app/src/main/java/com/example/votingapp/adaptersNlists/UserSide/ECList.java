package com.example.votingapp.adaptersNlists.UserSide;

public class ECList {

    private final Integer ECMembership;
    private final String ECName;

    public ECList(Integer ECMembership, String ECName) {
        this.ECMembership = ECMembership;
        this.ECName = ECName;
    }

    public Integer getECMembership() {
        return ECMembership;
    }

    public String getECName() {
        return ECName;
    }
}
