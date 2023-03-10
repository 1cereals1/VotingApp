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

public class ACvotepage extends AppCompatActivity implements ACAdapter.OnItemClickListener, ACAdapter.OnVoteClickListener {

    private boolean oneMoreVoteAllowed = true;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/");
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private RecyclerView ACrv;
    private ACAdapter mAdapter;


    private static final int MAX_VOTES = 2;
    private int numVotesRemaining;
    private final List<ACList> AClist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acvotepage);
        View darkenView = findViewById(R.id.darken_view);
        darkenView.setVisibility(View.GONE);

        ACrv = findViewById(R.id.ACRV);
        // set layout manager to the RecyclerView
        ACrv.setLayoutManager(new LinearLayoutManager(this));

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if (user != null) {
            String userId = user.getUid();
            databaseReference.child("Users").child(userId).child("numVotesRemaining").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        numVotesRemaining = snapshot.getValue(Integer.class);
                    } else {
                        numVotesRemaining = MAX_VOTES;
                        databaseReference.child("Users").child(userId).child("numVotesRemaining").setValue(numVotesRemaining);
                    }
                    mAdapter = new ACAdapter(AClist, ACvotepage.this, ACvotepage.this, oneMoreVoteAllowed, numVotesRemaining);
                    mAdapter.setOnItemClickListener(ACvotepage.this);
                    ACrv.setAdapter(mAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    numVotesRemaining = MAX_VOTES;
                    mAdapter = new ACAdapter(AClist, ACvotepage.this, ACvotepage.this, oneMoreVoteAllowed, numVotesRemaining);
                    mAdapter.setOnItemClickListener(ACvotepage.this);
                    ACrv.setAdapter(mAdapter);
                }
            });
        }


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AClist.clear();

                for (DataSnapshot candidates : snapshot.child("Candidates").getChildren()) {
                    if (candidates.hasChild("name") && candidates.hasChild("membership")) {
                        final String elective = candidates.child("elective").getValue(String.class);
                        if (elective != null && elective.equals("AUDIT COMITTEE")) {
                            // get candidate details from database
                            final String acName = candidates.child("name").getValue(String.class);
                            final String acPosition = elective;
                            final String acId = candidates.child("membership").getValue(String.class);
                            final Integer acVotes = candidates.child("votes").getValue(Integer.class);

                            final ArrayList votedBy = new ArrayList<>();


                            // create a new ACList object
                            ACList candidate = new ACList(acName, acPosition, acId, acVotes, votedBy);

                            // add the candidate to the list
                            AClist.add(candidate);
                            Log.d("ACvotepage", "Added candidate: " + acName + " with ID: " + acId);
                        }
                    }
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("ACvotepage", "onCancelled: " + error.getMessage());
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


    private void updateUserVotedCandidates(String userId, ACList candidate) {


        numCandidatesVotedForByUser(userId, new OnGetDataListener() {
            @Override
            public void onSuccess(long count) {}

            @Override
            public void onSuccess(long count, DataSnapshot snapshot) {
                if (count < MAX_VOTES) {
                    DatabaseReference userRef = databaseReference.child("Users").child(userId);
                    if (userRef != null) {
                        DatabaseReference votedForRef = userRef.child("votedFor").child(candidate.getACMembership());
                        votedForRef.setValue(candidate.getACName());

                        DatabaseReference votedByRef = databaseReference.child("Candidates").child(candidate.getACMembership()).child("votedBy");
                        votedByRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};
                                ArrayList<String> votedBy = snapshot.getValue(t);
                                if (votedBy == null) {
                                    votedBy = new ArrayList<>();
                                }
                                if (votedBy.contains(userId)){
                                    int position = mAdapter.getSelectedPosition();
                                    mAdapter.disableVoteButton(position);
                                    return;
                                }
                                votedBy.add(userId);
                                votedByRef.setValue(votedBy);

                                int newVotes = candidate.getACVotes() + 1;
                                numVotesRemaining --;
                                DatabaseReference candidateRef = databaseReference.child("Candidates").child(candidate.getACMembership());
                                candidateRef.child("votes").setValue(newVotes);
                                DatabaseReference uservoteref = databaseReference.child("Users").child(userId).child("numVotesRemaining");
                                uservoteref.setValue(numVotesRemaining);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(ACvotepage.this, "Failed to update candidate votes.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    Toast.makeText(ACvotepage.this, "You have reached the maximum number of votes.", Toast.LENGTH_SHORT).show();
                    int position = mAdapter.getSelectedPosition();
                    mAdapter.disableVoteButton(position);
                    return;
                }
            }

            @Override
            public void onSuccess(boolean value, DataSnapshot snapshot) {}

            @Override
            public void onFailure() {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void readDataOnce(DatabaseReference ref, final OnGetDataListener listener) {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long count = snapshot.getChildrenCount();
                listener.onSuccess(count, snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onFailure();
            }
        });
    }


    //VOTE BUTTON FUNCTIONS
    @Override
    public void onVoteClick(int position) {
        ACList candidate = AClist.get(position);
        String userId = mAuth.getCurrentUser().getUid();

        // Check if user has remaining votes
        if (numVotesRemaining <= 0) {
            Toast.makeText(ACvotepage.this, "You have used all your votes.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if user has already voted for this candidate
        if (candidate.getVotedBy().contains(userId)) {
            Toast.makeText(ACvotepage.this, "You have already voted for this candidate.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if user has already voted for maximum number of candidates
        numCandidatesVotedForByUser(userId, new OnGetDataListener() {
            @Override
            public void onSuccess(long count) {
                if (count >= MAX_VOTES) {
                    Toast.makeText(ACvotepage.this, "You have already voted for maximum number of candidates.", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference candidateRef = databaseReference.child("Candidates").child(candidate.getACMembership()).child("votes");
                    candidateRef.runTransaction(new Transaction.Handler() {
                        @NonNull
                        @Override
                        public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                            Integer votes = mutableData.getValue(Integer.class);
                            if (votes == null) {
                                mutableData.setValue(1);
                                candidate.setACVotes(1);
                            } else {
                                mutableData.setValue(votes + 1);
                                candidate.setACVotes(votes + 1);
                            }
                            return Transaction.success(mutableData);
                        }

                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, boolean committed, @Nullable DataSnapshot dataSnapshot) {
                            if (committed) {
                                numVotesRemaining--;
                                databaseReference.child("Users").child(userId).child("numVotesRemaining").setValue(numVotesRemaining);
                                DatabaseReference votedForRef = databaseReference.child("Users").child(userId).child("votedFor").child(candidate.getACMembership());
                                votedForRef.setValue(candidate.getACName());
                                DatabaseReference votedByRef = databaseReference.child("Candidates").child(candidate.getACMembership()).child("votedBy").child(userId);
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
    public void onItemClick (ACList item){
        // Pass the selected item to the next activity using an Intent
        Intent intent = new Intent(this, Review.class);
        intent.putExtra("ac_name", item.getACName());
        intent.putExtra("ac_id", item.getACMembership() + "");
        intent.putExtra("ac_votes", item.getACVotes());

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
