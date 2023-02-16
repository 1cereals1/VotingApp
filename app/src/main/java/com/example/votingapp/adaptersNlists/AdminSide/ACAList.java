package com.example.votingapp.adaptersNlists.AdminSide;

public class ACAList {

    private final Integer ACAMembership;
    private final String ACAName;

    public ACAList(Integer ACAMembership, String ACAName) {
        this.ACAMembership = ACAMembership;
        this.ACAName = ACAName;
    }

    public Integer getACAMembership() {
        return ACAMembership;
    }

    public String getACAName() {
        return ACAName;
    }
}
