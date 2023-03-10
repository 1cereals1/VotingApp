package com.example.votingapp.adaptersNlists.UserSide;

import java.io.Serializable;
import java.util.ArrayList;

public class ACList implements Serializable {
    private String ACName;
    private String ACPosition;
    private String ACMembership;
    private int ACVotes;
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
    public ACList() {
    }

    public void decrementVotes() {
        ACVotes--;
    }

    public ACList(String ACName, String ACPosition, String ACMembership, int ACVotes, ArrayList<String> votedBy) {
        this.ACName = ACName;
        this.ACPosition = ACPosition;
        this.ACMembership = ACMembership;
        this.ACVotes = ACVotes;

        this.votedBy = new ArrayList<>();
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
