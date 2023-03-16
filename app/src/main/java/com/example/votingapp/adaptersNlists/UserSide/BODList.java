package com.example.votingapp.adaptersNlists.UserSide;

import java.io.Serializable;
import java.util.ArrayList;

public class BODList implements Serializable {
    private String BODName;
    private String BODPosition;
    private String BODMembership;
    private int BODVotes;
    private boolean mVoteButtonEnabled;
    public void setVoteButtonEnabled(boolean enabled) {
        mVoteButtonEnabled = enabled;
    }
    public boolean isVoteButtonEnabled() {
        return mVoteButtonEnabled;
    }
    private ArrayList<String> votedBy; // New field for tracking who voted for this candidate
    private boolean isButtonEnabled = true;

    public boolean isButtonEnabled() {
        return isButtonEnabled;
    }

    public void setButtonEnabled(boolean enabled) {
        isButtonEnabled = enabled;
    }
    public BODList() {
    }

    public void decrementVotes() {
        BODVotes--;
    }

    public BODList(String BODName, String BODPosition, String BODMembership, int BODVotes, ArrayList<String> votedBy) {
        this.BODName = BODName;
        this.BODPosition = BODPosition;
        this.BODMembership = BODMembership;
        this.BODVotes = BODVotes;

        this.votedBy = new ArrayList<>();
    }

    public String getBODName() {
        return BODName;
    }

    public String getBODPosition() {
        return BODPosition;
    }

    public String getBODMembership() {
        return BODMembership;
    }

    public int getBODVotes() {
        return BODVotes;
    }

    public void setBODVotes(int BODVotes) {
        this.BODVotes = BODVotes;
    }


    public boolean hasUserVoted(String userId) {
        return votedBy.contains(userId);
    }

    public ArrayList<String> getVotedBy() {
        return votedBy;
    }

    public void setVotedBy(ArrayList<String> votedBy) {
        this.votedBy = votedBy;
    }
    public void addVotedBy(String userId) {
        votedBy.add(userId);
    }

}