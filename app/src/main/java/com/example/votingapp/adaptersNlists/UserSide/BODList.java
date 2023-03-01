package com.example.votingapp.adaptersNlists.UserSide;

import android.widget.Button;

import java.io.Serializable;

public class BODList implements Serializable {

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
