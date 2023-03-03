package com.example.votingapp.adaptersNlists.AdminSide;

import java.io.Serializable;

public class ECAList implements Serializable {

    private final String ECAMembership;
    private final String ECAName;
    private final String ECAPosition;

    public ECAList(String ECAMembership, String ECAName, String ECAPosition) {
        this.ECAMembership = ECAMembership;
        this.ECAName = ECAName;
        this.ECAPosition = ECAPosition;
    }

    public String getECAMembership() {
        return ECAMembership;
    }

    public String getECAName() {
        return ECAName;
    }
    public String getECAPosition(){ return ECAPosition; }
}
