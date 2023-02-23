package com.example.votingapp.adaptersNlists.UserSide;

import android.widget.Button;

import java.io.Serializable;

public class ACList implements Serializable {

    private final Integer ACMembership;
    private final String ACName;




    public ACList(Integer ACMembership, String ACName) {
        this.ACMembership = ACMembership;
        this.ACName = ACName;

    }

    public Integer getACMembership() {
        return ACMembership;
    }

    public String getACName() {
        return ACName;
    }


}
