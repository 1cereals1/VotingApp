package com.example.votingapp.adaptersNlists.UserSide;

import android.widget.Button;

import java.io.Serializable;

public class ECList implements Serializable {

    private final Integer ECMembership;
    private final String ECName;




    public ECList(Integer ECMembership, String ECName) {
        this.ECMembership = ECMembership;
        this.ECName = ECName;

    }

    public Integer getECMembership() {
        return ECMembership;
    }

    public String getECName() {
        return ECName;
    }


}
