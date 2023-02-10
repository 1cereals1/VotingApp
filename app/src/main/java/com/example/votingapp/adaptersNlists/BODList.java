package com.example.votingapp.adaptersNlists;

public class BODList {

    private final Integer BODMembership;
    private final String BODName;

    public BODList(Integer BODMembership, String BODName) {
        this.BODMembership = BODMembership;
        this.BODName = BODName;
    }

    public Integer getBODMembership() {
        return BODMembership;
    }

    public String getBODName() {
        return BODName;
    }
}
