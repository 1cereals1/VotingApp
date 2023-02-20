package com.example.votingapp.adaptersNlists.UserSide;

import android.widget.Button;

public class ACList {

    private final Integer ACMembership;
    private final String ACName;

    private final Button ACButton;


    public ACList(Integer ACMembership, String ACName, Button ACButton) {
        this.ACMembership = ACMembership;
        this.ACName = ACName;
        this.ACButton = ACButton;
    }

    public Integer getACMembership() {
        return ACMembership;
    }

    public String getACName() {
        return ACName;
    }

    public Button getACButton(){return ACButton; }
}
