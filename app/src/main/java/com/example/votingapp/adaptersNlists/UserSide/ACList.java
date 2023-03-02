package com.example.votingapp.adaptersNlists.UserSide;

import android.widget.Button;

import java.io.Serializable;

public class ACList implements Serializable {

    private final String ACMembership;
    private final String ACName;

    private final String ACPosition;




    public ACList(String ACMembership, String ACName, String ACPosition) {
        this.ACMembership = ACMembership;
        this.ACName = ACName;
        this.ACPosition = ACPosition;

    }

    public String getACMembership() {
        return ACMembership;
    }

    public String getACName() {
        return ACName;
    }

    public String getACPosition(){return ACPosition;}

}
