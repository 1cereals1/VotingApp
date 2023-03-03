package com.example.votingapp.adaptersNlists.UserSide;

import android.widget.Button;

import java.io.Serializable;

public class ECList implements Serializable {

    private final String ECMembership;
    private final String ECName;

    private final String ECPosition;



    public ECList(String ECMembership, String ECName, String ECPosition) {
        this.ECMembership = ECMembership;
        this.ECName = ECName;
        this.ECPosition = ECPosition;

    }

    public String getECMembership() {
        return ECMembership;
    }

    public String getECName() {
        return ECName;
    }

    public String getECPosition(){return ECPosition;}


}
