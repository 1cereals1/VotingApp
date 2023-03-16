package com.example.votingapp.UserSideofThings;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.votingapp.AdminSideofThings.AdminsEmployees;
import com.example.votingapp.AdminSideofThings.Register;
import com.example.votingapp.R;
import com.example.votingapp.adaptersNlists.ACbuttonhandler;
import com.example.votingapp.adaptersNlists.UserSide.ACAdapter;
import com.example.votingapp.adaptersNlists.UserSide.ACList;
import com.example.votingapp.adaptersNlists.UserSide.ECAdapter;
import com.example.votingapp.adaptersNlists.UserSide.ECList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class ECvotepage extends AppCompatActivity implements ECAdapter.OnItemClickListener, ECAdapter.OnVoteClickListener {

    private boolean oneMoreVoteAllowed = true;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/");
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private RecyclerView ECrv;
    private ECAdapter mAdapter;



    private static final int MAX_VOTES = 2;
    private int ECnumVotesRemaining;
    private final List<ECList> EClist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecvotepage);
        View darkenView = findViewById(R.id.darken_view);
        darkenView.setVisibility(View.GONE);

        ECrv = findViewById(R.id.ECRV);
        // set layout manager to the RecyclerView
        ECrv.setLayoutManager(new LinearLayoutManager(this));

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if (user != null) {
            String userId = user.getUid();
            databaseReference.child("Users").child(userId).child("ECnumVotesRemaining").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        ECnumVotesRemaining = snapshot.getValue(Integer.class);
                    } else {
                        ECnumVotesRemaining = MAX_VOTES;
                        databaseReference.child("Users").child(userId).child("ECnumVotesRemaining").setValue(ECnumVotesRemaining);
                    }
                    mAdapter = new ECAdapter(EClist, ECvotepage.this, ECvotepage.this, oneMoreVoteAllowed, ECnumVotesRemaining);
                    mAdapter.setOnItemClickListener(ECvotepage.this);
                    mAdapter.setRecyclerView(ECrv);
                    ECrv.setAdapter(mAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    ECnumVotesRemaining = MAX_VOTES;
                    mAdapter = new ECAdapter(EClist, ECvotepage.this, ECvotepage.this, oneMoreVoteAllowed, ECnumVotesRemaining);
                    mAdapter.setOnItemClickListener(ECvotepage.this);
                    ECrv.setAdapter(mAdapter);
                }
            });
        }


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                EClist.clear();

                for (DataSnapshot candidates : snapshot.child("Candidates").getChildren()) {
                    if (candidates.hasChild("name") && candidates.hasChild("membership")) {
                        final String elective = candidates.child("elective").getValue(String.class);
                        if (elective != null && elective.equals("ELECTION COMITTEE")) {
                            // get candidate details from database
                            final String ecName = candidates.child("name").getValue(String.class);
                            final String ecPosition = elective;
                            final String ecId = candidates.child("membership").getValue(String.class);
                            final Integer ecVotes = candidates.child("votes").getValue(Integer.class);

                            final ArrayList votedBy = new ArrayList<>();


                            // create a new ACList object
                            ECList candidate = new ECList(ecName, ecPosition, ecId, ecVotes, votedBy);

                            // add the candidate to the list
                            EClist.add(candidate);
                            Log.d("ECvotepage", "Added candidate: " + ecName + " with ID: " + ecId);
                        }
                    }
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("ECvotepage", "onCancelled: " + error.getMessage());
            }
        });

        //START OF CALCULATING CANDIDATES PERCENTAGES
        DatabaseReference candidatesRef = FirebaseDatabase.getInstance().getReference().child("Candidates");
        candidatesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot candidateSnapshot : dataSnapshot.getChildren()) {
                    String candidateId = candidateSnapshot.getKey();
                    int totalVoters = (int) candidateSnapshot.child("votedBy").getChildrenCount();
                    int votes = candidateSnapshot.child("votes").getValue(Integer.class);
                    if (totalVoters > 0) {
                        float percentage = (votes * 100) / totalVoters;
                        candidateSnapshot.child("percentage").getRef().setValue(percentage);
                    } else {
                        candidateSnapshot.child("percentage").getRef().setValue(0);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
        //END OF CALCULATING CANDIDATES PERCENTAGES


    }

    public interface OnGetDataListener {
        void onSuccess(long count);

        void onSuccess(long count, DataSnapshot snapshot);

        void onSuccess(boolean value, DataSnapshot snapshot);

        void onFailure();

        void onCancelled(DatabaseError databaseError);
    }


    private void numCandidatesVotedForByUser(String userId, OnGetDataListener listener) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Votes").child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            long count = dataSnapshot.getChildrenCount();
                            listener.onSuccess(count);
                        } else {
                            listener.onSuccess(0);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        listener.onFailure();
                    }
                });
    }




    //VOTE BUTTON FUNCTIONS
    @Override
    public void onVoteClick(int position) {
        // Get the current user
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Toast.makeText(ECvotepage.this, "You must be logged in to vote", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the selected candidate
        ECList selectedCandidate = EClist.get(position);

        // Check if the user has already voted for the maximum number of candidates
        numCandidatesVotedForByUser(user.getUid(), new OnGetDataListener() {
            @Override
            public void onSuccess(long count) {
                if (count >= MAX_VOTES) {
                    Toast.makeText(ECvotepage.this, "You have already voted for the maximum number of candidates", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if the user has already voted for this candidate
                DatabaseReference candidateRef = databaseReference.child("Candidates").child(selectedCandidate.getECMembership());
                candidateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            ECList candidate = snapshot.getValue(ECList.class);
                            List<String> votedBy = candidate.getVotedBy();

                            if (votedBy == null) {
                                votedBy = new ArrayList<>();
                            }

                            if (votedBy.contains(user.getUid())) {
                                Toast.makeText(ECvotepage.this, "You have already voted for this candidate", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (ECnumVotesRemaining == 0){
                                oneMoreVoteAllowed = false;
                            } else {
                                // Update the candidate's vote count
                                int newVoteCount = candidate.getECVotes() + 1;
                                candidateRef.child("votes").setValue(newVoteCount);
                                ECnumVotesRemaining--;

                                // Update the list of users who have voted for this candidate
                                votedBy.add(user.getUid());
                                candidateRef.child("votedBy").setValue(votedBy);

                                // Update the user's numVotesRemaining and votedFor fields
                                databaseReference.child("Users").child(user.getUid()).child("ECnumVotesRemaining").setValue(ECnumVotesRemaining);
                                databaseReference.child("Users").child(user.getUid()).child("votedFor").child(selectedCandidate.getECMembership()).setValue(true);

                                // Disable the vote button for this candidate
                                mAdapter.disableVoteButtonForCandidate(position);
                                //oneMoreVoteAllowed = false;
                                mAdapter = new ECAdapter(EClist, ECvotepage.this, ECvotepage.this, oneMoreVoteAllowed, ECnumVotesRemaining);
                                mAdapter.setOnItemClickListener(ECvotepage.this);
                                mAdapter.setRecyclerView(ECrv);
                                ECrv.setAdapter(mAdapter);

                                Toast.makeText(ECvotepage.this, "Vote submitted successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ECvotepage.this, "Failed to submit vote", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onSuccess(long count, DataSnapshot snapshot) {

            }

            @Override
            public void onSuccess(boolean value, DataSnapshot snapshot) {

            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

            // ...
        });
    }

    // END OF VOTE BUTTON FUNCTION

    @Override
    public void onItemClick (ECList item){
        // Pass the selected item to the next activity using an Intent
        Intent intent = new Intent(this, Review.class);
        intent.putExtra("ec_name", item.getECName());
        intent.putExtra("ec_id", item.getECMembership() + "");
        intent.putExtra("ec_votes", item.getECVotes());

        // To darken the background, set the visibility of the "darken_view" to "visible"
        View darkenView = findViewById(R.id.darken_view);
        darkenView.setVisibility(View.VISIBLE);

        startActivity(intent);
    }
    @Override
    protected void onResume () {
        super.onResume();

        // Hide the darken view when returning to this activity
        View darkenView = findViewById(R.id.darken_view);
        darkenView.setVisibility(View.GONE);
    }
}