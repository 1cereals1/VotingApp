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

public class ACvotepage extends AppCompatActivity implements ACAdapter.OnItemClickListener, ACAdapter.OnVoteClickListener {

    private boolean oneMoreVoteAllowed = true;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/");
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private RecyclerView ACrv;
    private ACAdapter mAdapter;
    private ImageButton ACtoBOD,ACtoEC;
    private Button BODreset;



    private static final int MAX_VOTES = 3;
    private int ACnumVotesRemaining;
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

        ACtoBOD = findViewById(R.id.actobod);
        ACtoEC = findViewById(R.id.actoec);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


        if (user != null) {
            String userId = user.getUid();
            databaseReference.child("Users").child(userId).child("ACnumVotesRemaining").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        ACnumVotesRemaining = snapshot.getValue(Integer.class);
                    } else {
                        ACnumVotesRemaining = MAX_VOTES;
                        databaseReference.child("Users").child(userId).child("ACnumVotesRemaining").setValue(ACnumVotesRemaining);
                    }
                    mAdapter = new ACAdapter(AClist, ACvotepage.this, ACvotepage.this, oneMoreVoteAllowed, ACnumVotesRemaining);
                    mAdapter.setOnItemClickListener(ACvotepage.this);
                    mAdapter.setRecyclerView(ACrv);
                    ACrv.setAdapter(mAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    ACnumVotesRemaining = MAX_VOTES;
                    mAdapter = new ACAdapter(AClist, ACvotepage.this, ACvotepage.this, oneMoreVoteAllowed, ACnumVotesRemaining);
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
                            final String acId = candidates.child("membership").getValue().toString();
                            final Integer acVotes = candidates.child("votes").getValue(Integer.class);

                            final ArrayList votedBy = new ArrayList<>();


                            // create a new BODList object
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




        ACtoBOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ACvotepage.this, BODvotepage.class));
                finish();
            }
        });
        ACtoEC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ACvotepage.this, ECvotepage.class));
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
            Toast.makeText(ACvotepage.this, "You must be logged in to vote", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the selected candidate
        ACList selectedCandidate = AClist.get(position);

        // Check if the user has already voted for the maximum number of candidates
        numCandidatesVotedForByUser(user.getUid(), new OnGetDataListener() {
            @Override
            public void onSuccess(long count) {
                if (count >= MAX_VOTES) {
                    Toast.makeText(ACvotepage.this, "You have already voted for the maximum number of candidates", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if the user has already voted for this candidate
                DatabaseReference candidateRef = databaseReference.child("Candidates").child(selectedCandidate.getACMembership());
                candidateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            ACList candidate = snapshot.getValue(ACList.class);
                            List<String> votedBy = candidate.getVotedBy();

                            if (votedBy == null) {
                                votedBy = new ArrayList<>();
                            }

                            if (votedBy.contains(user.getUid())) {
                                Toast.makeText(ACvotepage.this, "You have already voted for this candidate", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (ACnumVotesRemaining == 0){
                                oneMoreVoteAllowed = false;
                            } else {
                                // Update the candidate's vote count
                                DataSnapshot candidateSnapshot = snapshot.child("Candidates").child(selectedCandidate.getACMembership());
//aaa
                                int newVoteCount = selectedCandidate.getACVotes() + 1;
                                candidateRef.child("votes").setValue(newVoteCount);
                                ACnumVotesRemaining--;

                                // Update the list of users who have voted for this candidate
                                votedBy.add(user.getUid());
                                candidateRef.child("votedBy").setValue(votedBy);

                                // Update the user's numVotesRemaining and votedFor fields
                                databaseReference.child("Users").child(user.getUid()).child("ACnumVotesRemaining").setValue(ACnumVotesRemaining);
                                databaseReference.child("Users").child(user.getUid()).child("votedFor").child(selectedCandidate.getACMembership()).setValue(true);

                                // Disable the vote button for this candidate
                                mAdapter.disableVoteButtonForCandidate(position);
                                //oneMoreVoteAllowed = false;
                                mAdapter = new ACAdapter(AClist, ACvotepage.this, ACvotepage.this, oneMoreVoteAllowed, ACnumVotesRemaining);
                                mAdapter.setOnItemClickListener(ACvotepage.this);
                                mAdapter.setRecyclerView(ACrv);
                                ACrv.setAdapter(mAdapter);

                                Toast.makeText(ACvotepage.this, "Vote submitted successfully", Toast.LENGTH_SHORT).show();
                                Toast.makeText(ACvotepage.this, "Number of votes left: " + ACnumVotesRemaining, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ACvotepage.this, "Failed to submit vote", Toast.LENGTH_SHORT).show();
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
    public void onItemClick (ACList item){
        // Pass the selected item to the next activity using an Intent
        Intent intent = new Intent(this, Review.class);
        intent.putExtra("ac_name", item.getACName());
        intent.putExtra("ac_id", item.getACMembership() + "");
        intent.putExtra("ac_votes", item.getACVotes());
        String acidacid = item.getACMembership();





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