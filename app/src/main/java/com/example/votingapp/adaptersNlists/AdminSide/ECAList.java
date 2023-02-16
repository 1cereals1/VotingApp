package com.example.votingapp.adaptersNlists.AdminSide;

public class ECAList {

    private final Integer ECAMembership;
    private final String ECAName;

    public ECAList(Integer ECAMembership, String ECAName) {
        this.ECAMembership = ECAMembership;
        this.ECAName = ECAName;
    }

    public Integer getECAMembership() {
        return ECAMembership;
    }

    public String getECAName() {
        return ECAName;
    }
}
