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
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.votingapp.AdminSideofThings.AdminsEmployees;
import com.example.votingapp.AdminSideofThings.Register;
import com.example.votingapp.R;
import com.example.votingapp.adaptersNlists.ACbuttonhandler;
import com.example.votingapp.adaptersNlists.UserSide.ACAdapter;
import com.example.votingapp.adaptersNlists.UserSide.ACList;
import com.example.votingapp.adaptersNlists.UserSide.BODAdapter;
import com.example.votingapp.adaptersNlists.UserSide.BODList;
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

public class BODvotepage extends AppCompatActivity implements BODAdapter.OnItemClickListener, BODAdapter.OnVoteClickListener {

    private boolean oneMoreVoteAllowed = true;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/");
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private RecyclerView BODrv;
    private BODAdapter mAdapter;
    private ImageButton BODtoAC,BODtoHOME;
    private Button BODreset;



    private static final int MAX_VOTES = 3;
    private int BODnumVotesRemaining;
    private final List<BODList> BODlist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bodvotepage);
        View darkenView = findViewById(R.id.darken_view);
        darkenView.setVisibility(View.GONE);

        BODrv = findViewById(R.id.BODRV);
        // set layout manager to the RecyclerView
        BODrv.setLayoutManager(new LinearLayoutManager(this));

        BODtoAC = findViewById(R.id.bodtoac);
        BODtoHOME = findViewById(R.id.bodtohome);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


        BODtoAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BODvotepage.this, ACvotepage.class));
                finish();
            }
        });

        if (user != null) {
            String userId = user.getUid();
            databaseReference.child("Users").child(userId).child("BODnumVotesRemaining").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        BODnumVotesRemaining = snapshot.getValue(Integer.class);
                    } else {
                        BODnumVotesRemaining = MAX_VOTES;
                        databaseReference.child("Users").child(userId).child("BODnumVotesRemaining").setValue(BODnumVotesRemaining);
                    }
                    mAdapter = new BODAdapter(BODlist, BODvotepage.this, BODvotepage.this, oneMoreVoteAllowed, BODnumVotesRemaining);
                    mAdapter.setOnItemClickListener(BODvotepage.this);
                    mAdapter.setRecyclerView(BODrv);
                    BODrv.setAdapter(mAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    BODnumVotesRemaining = MAX_VOTES;
                    mAdapter = new BODAdapter(BODlist, BODvotepage.this, BODvotepage.this, oneMoreVoteAllowed, BODnumVotesRemaining);
                    mAdapter.setOnItemClickListener(BODvotepage.this);
                    BODrv.setAdapter(mAdapter);
                }
            });
        }


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                BODlist.clear();

                for (DataSnapshot candidates : snapshot.child("Candidates").getChildren()) {
                    if (candidates.hasChild("name") && candidates.hasChild("membership")) {
                        final String elective = candidates.child("elective").getValue(String.class);
                        if (elective != null && elective.equals("DIRECTOR")) {
                            // get candidate details from database
                            final String bodName = candidates.child("name").getValue(String.class);
                            final String bodPosition = elective;
                            final String bodId = candidates.child("membership").getValue(String.class);
                            //final String bodId = longbodId.toString();
                            final Integer bodVotes = candidates.child("votes").getValue(Integer.class);

                            final ArrayList votedBy = new ArrayList<>();


                            // create a new BODList object
                            BODList candidate = new BODList(bodName, bodPosition, bodId, bodVotes, votedBy);

                            // add the candidate to the list
                            BODlist.add(candidate);
                            Log.d("BODvotepage", "Added candidate: " + bodName + " with ID: " + bodId);
                        }
                    }
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("BODvotepage", "onCancelled: " + error.getMessage());
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


        BODtoHOME.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

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
            Toast.makeText(BODvotepage.this, "You must be logged in to vote", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the selected candidate
        BODList selectedCandidate = BODlist.get(position);

        // Check if the user has already voted for the maximum number of candidates
        numCandidatesVotedForByUser(user.getUid(), new OnGetDataListener() {
            @Override
            public void onSuccess(long count) {
                if (count >= MAX_VOTES) {
                    Toast.makeText(BODvotepage.this, "You have already voted for the maximum number of candidates", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if the user has already voted for this candidate
                DatabaseReference candidateRef = databaseReference.child("Candidates").child(selectedCandidate.getBODMembership());
                candidateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            BODList candidate = snapshot.getValue(BODList.class);
                            List<String> votedBy = candidate.getVotedBy();

                            if (votedBy == null) {
                                votedBy = new ArrayList<>();
                            }

                            if (votedBy.contains(user.getUid())) {
                                Toast.makeText(BODvotepage.this, "You have already voted for this candidate", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (BODnumVotesRemaining == 0){
                                oneMoreVoteAllowed = false;
                            } else {
                                // Update the candidate's vote count
                                DataSnapshot candidateSnapshot = snapshot.child("Candidates").child(selectedCandidate.getBODMembership());

                                int newVoteCount = selectedCandidate.getBODVotes() + 1;
                                candidateRef.child("votes").setValue(newVoteCount);
                                BODnumVotesRemaining--;

                                // Update the list of users who have voted for this candidate
                                votedBy.add(user.getUid());
                                candidateRef.child("votedBy").setValue(votedBy);

                                // Update the user's numVotesRemaining and votedFor fields
                                databaseReference.child("Users").child(user.getUid()).child("BODnumVotesRemaining").setValue(BODnumVotesRemaining);
                                databaseReference.child("Users").child(user.getUid()).child("votedFor").child(selectedCandidate.getBODMembership()).setValue(true);

                                // Disable the vote button for this candidate
                                mAdapter.disableVoteButtonForCandidate(position);
                                //oneMoreVoteAllowed = false;
                                mAdapter = new BODAdapter(BODlist, BODvotepage.this, BODvotepage.this, oneMoreVoteAllowed, BODnumVotesRemaining);
                                mAdapter.setOnItemClickListener(BODvotepage.this);
                                mAdapter.setRecyclerView(BODrv);
                                BODrv.setAdapter(mAdapter);

                                Toast.makeText(BODvotepage.this, "Vote submitted successfully", Toast.LENGTH_SHORT).show();
                                Toast.makeText(BODvotepage.this, "Number of votes left: " + BODnumVotesRemaining, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(BODvotepage.this, "Failed to submit vote", Toast.LENGTH_SHORT).show();
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
    public void onItemClick (BODList item){
        // Pass the selected item to the next activity using an Intent
        Intent intent = new Intent(this, BODReview.class);
        intent.putExtra("bod_name", item.getBODName());
        intent.putExtra("bod_id", item.getBODMembership() + "");
        intent.putExtra("bod_votes", item.getBODVotes());

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