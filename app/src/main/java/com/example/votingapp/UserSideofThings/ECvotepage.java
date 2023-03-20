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

    private ImageButton ECtoAC,ECtoHome;



    private static final int MAX_VOTES = 2;
    private int ECnumVotesRemaining;
    private final List<ECList> EClist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecvotepage);
        View darkenView = findViewById(R.id.darken_view);
        darkenView.setVisibility(View.GONE);

        ECtoAC = findViewById(R.id.ectoac);
        ECtoHome = findViewById(R.id.ectohome);


        ECtoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        ECtoAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ECvotepage.this, ACvotepage.class));
                finish();
            }
        });

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
        ECList candidate = EClist.get(position);
        String userId = mAuth.getCurrentUser().getUid();

        // Check if user has remaining votes
        if (ECnumVotesRemaining <= 0) {
            Toast.makeText(ECvotepage.this, "You have used all your votes.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if user has already voted for this candidate
        if (candidate.getVotedBy().contains(userId)) {
            Toast.makeText(ECvotepage.this, "You have already voted for this candidate.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if user has already voted for maximum number of candidates
        numCandidatesVotedForByUser(userId, new ECvotepage.OnGetDataListener() {
            @Override
            public void onSuccess(long count) {
                if (count >= MAX_VOTES) {
                    Toast.makeText(ECvotepage.this, "You have already voted for maximum number of candidates.", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference candidateRef = databaseReference.child("Candidates").child(candidate.getECMembership()).child("votes");
                    candidateRef.runTransaction(new Transaction.Handler() {
                        @NonNull
                        @Override
                        public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                            Integer votes = mutableData.getValue(Integer.class);
                            if (votes == null) {
                                mutableData.setValue(1);
                                candidate.setECVotes(1);
                            } else {
                                mutableData.setValue(votes + 1);
                                candidate.setECVotes(votes + 1);
                            }
                            return Transaction.success(mutableData);
                        }

                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, boolean committed, @Nullable DataSnapshot dataSnapshot) {
                            if (committed) {
                                ECnumVotesRemaining--;
                                databaseReference.child("Users").child(userId).child("ECnumVotesRemaining").setValue(ECnumVotesRemaining);
                                DatabaseReference votedForRef = databaseReference.child("Users").child(userId).child("votedFor").child(candidate.getECMembership());
                                votedForRef.setValue(candidate.getECName());
                                DatabaseReference votedByRef = databaseReference.child("Candidates").child(candidate.getECMembership()).child("votedBy").child(userId);
                                votedByRef.setValue(true);
                                mAdapter.notifyItemChanged(position);
                            }
                        }
                    });
                }
            }

            @Override
            public void onSuccess(long count, DataSnapshot snapshot) {}

            @Override
            public void onSuccess(boolean value, DataSnapshot snapshot) {}

            @Override
            public void onFailure() {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
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