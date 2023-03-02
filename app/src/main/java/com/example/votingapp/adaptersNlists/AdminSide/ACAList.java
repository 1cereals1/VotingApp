package com.example.votingapp.adaptersNlists.AdminSide;

import java.io.Serializable;

public class ACAList implements Serializable {

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
