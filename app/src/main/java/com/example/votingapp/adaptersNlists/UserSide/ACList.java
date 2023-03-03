package com.example.votingapp.adaptersNlists.UserSide;

import java.io.Serializable;

public class ACList implements Serializable {
    private String ACName;
    private String ACPosition;
    private String ACMembership;
    private int ACVotes;
    private boolean votedFor;

    public ACList(String ACName, String ACPosition, String ACMembership, int ACVotes, boolean votedFor) {
        this.ACName = ACName;
        this.ACPosition = ACPosition;
        this.ACMembership = ACMembership;
        this.ACVotes = ACVotes;
        this.votedFor = votedFor;
    }

    public String getACName() {
        return ACName;
    }

    public String getACPosition() {
        return ACPosition;
    }

    public String getACMembership() {
        return ACMembership;
    }

    public int getACVotes() {
        return ACVotes;
    }

    public void setACVotes(int ACVotes) {
        this.ACVotes = ACVotes;
    }

    public boolean isVotedFor() {
        return votedFor;
    }

    public void setVotedFor(boolean votedFor) {
        this.votedFor = votedFor;
    }
}
