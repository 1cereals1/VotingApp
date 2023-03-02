package com.example.votingapp.adaptersNlists.AdminSide;

import java.io.Serializable;

public class BODAList implements Serializable {

    private final String BODAMembership;
    private final String BODAName;
    private final String BODAPosition;

    public BODAList(String BODAMembership, String BODAName, String BODAPosition) {
        this.BODAMembership = BODAMembership;
        this.BODAName = BODAName;
        this.BODAPosition = BODAPosition;
    }

    public String getBODAMembership() {
        return BODAMembership;
    }

    public String getBODAName() {
        return BODAName;
    }

    public String getBODAPosition(){return BODAPosition;}
}
