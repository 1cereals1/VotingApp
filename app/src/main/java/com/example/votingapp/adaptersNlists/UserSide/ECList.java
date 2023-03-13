package com.example.votingapp.adaptersNlists.UserSide;

import java.io.Serializable;
import java.util.ArrayList;

public class ECList implements Serializable {
    private String ECName;
    private String ECPosition;
    private String ECMembership;
    private int ECVotes;
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
    public ECList() {
    }

    public void decrementVotes() {
        ECVotes--;
    }

    public ECList(String ECName, String ECPosition, String ECMembership, int ECVotes, ArrayList<String> votedBy) {
        this.ECName = ECName;
        this.ECPosition = ECPosition;
        this.ECMembership = ECMembership;
        this.ECVotes = ECVotes;

        this.votedBy = new ArrayList<>();
    }

    public String getECName() {
        return ECName;
    }

    public String getECPosition() {
        return ECPosition;
    }

    public String getECMembership() {
        return ECMembership;
    }

    public int getECVotes() {
        return ECVotes;
    }

    public void setECVotes(int ECVotes) {
        this.ECVotes = ECVotes;
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
