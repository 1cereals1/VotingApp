package com.example.votingapp.adaptersNlists;

public class BODList {

    private final String BODName;
    private final Integer BODMembership;


    public BODList(String BODName, Integer BODMembership) {
        this.BODName = BODName;
        this.BODMembership = BODMembership;
    }

    public String getBODName() {
        return BODName;
    }

    public Integer getBODMembership() {
        return BODMembership;
    }
}
