package com.example.votingapp.adaptersNlists.UserSide;

import android.widget.Button;

import java.io.Serializable;

public class BODList implements Serializable {

    private final String BODMembership;
    private final String BODName;

    private final String BODPosition;



    public BODList(String BODMembership, String BODName, String BODPosition) {
        this.BODMembership = BODMembership;
        this.BODName = BODName;
        this.BODPosition = BODPosition;

    }

    public String getBODMembership() {
        return BODMembership;
    }

    public String getBODName() {
        return BODName;
    }

    public String getBODPosition(){return BODPosition;}

}
