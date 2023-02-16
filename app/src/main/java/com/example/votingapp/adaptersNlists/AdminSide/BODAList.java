package com.example.votingapp.adaptersNlists.AdminSide;

public class BODAList {

    private final Integer BODAMembership;
    private final String BODAName;

    public BODAList(Integer BODAMembership, String BODAName) {
        this.BODAMembership = BODAMembership;
        this.BODAName = BODAName;
    }

    public Integer getBODAMembership() {
        return BODAMembership;
    }

    public String getBODAName() {
        return BODAName;
    }
}
