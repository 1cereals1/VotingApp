package com.example.votingapp.adaptersNlists.AdminSide;

import java.io.Serializable;

public class ACAList implements Serializable {

    private final String ACAMembership;
    private final String ACAName;

    private final String ACAPosition;

    public ACAList(String ACAMembership, String ACAName, String ACAPosition) {
        this.ACAMembership = ACAMembership;
        this.ACAName = ACAName;
        this.ACAPosition = ACAPosition;
    }

    public String getACAMembership() {
        return ACAMembership;
    }

    public String getACAName() {
        return ACAName;
    }

    public String getACAPosition(){return ACAPosition;}
}
