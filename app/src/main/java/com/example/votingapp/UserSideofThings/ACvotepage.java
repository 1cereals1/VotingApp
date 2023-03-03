package com.example.votingapp.UserSideofThings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.votingapp.AdminSideofThings.AdminsEmployees;
import com.example.votingapp.AdminSideofThings.Register;
import com.example.votingapp.R;
import com.example.votingapp.adaptersNlists.ACbuttonhandler;
import com.example.votingapp.adaptersNlists.UserSide.ACAdapter;
import com.example.votingapp.adaptersNlists.UserSide.ACList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class ACvotepage extends AppCompatActivity implements ACAdapter.OnItemClickListener, ACAdapter.OnVoteClickListener {

    private boolean oneMoreVoteAllowed = true;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/");
    private RecyclerView ACrv;
    private ACAdapter mAdapter;
    private Button ACvoteButton;
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

        mAdapter = new ACAdapter(AClist, this, this, oneMoreVoteAllowed);
        mAdapter.setOnItemClickListener(this);
        ACrv.setAdapter(mAdapter);




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
                            final Boolean acisVoted = candidates.child("votestatus").getValue(Boolean.class);

                            // create a new ACList object
                            ACList candidate = new ACList(acName, acPosition, acId, acVotes, acisVoted);

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
    //VOTE BUTTON FUNCTIONS
    @Override
    public void onVoteClick(int position) {
        ACList clickedItem = AClist.get(position);
        if (!clickedItem.isVotedFor()) {
            clickedItem.setACVotes(clickedItem.getACVotes() + 1);
            clickedItem.setVotedFor(true);
            FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/").child("Candidates").child(clickedItem.getACMembership()).setValue(clickedItem);
            DatabaseReference clickedItemRef = databaseReference.child("Candidates").child(clickedItem.getACMembership());
            clickedItemRef.child("votes").setValue(clickedItem.getACVotes());
            clickedItemRef.child("votestatus").setValue(clickedItem.isVotedFor());
            Log.d("ACvotepage", "button pressed");
        }

        int numVotedFor = 0;
        for (ACList candidate : AClist) {
            if (candidate.isVotedFor()) {
                numVotedFor++;
            }
        }

        boolean oneMoreVoteAllowed = numVotedFor == 1;

        for (int i = 0; i < AClist.size(); i++) {
            ACList candidate = AClist.get(i);
            if (candidate.isVotedFor()) {
                mAdapter.notifyItemChanged(i);
            } else {
                if (!oneMoreVoteAllowed) {
                    mAdapter.notifyItemChanged(i);
                }
                mAdapter.notifyItemChanged(i, oneMoreVoteAllowed);
            }
        }
    }
    //END OF VOTE BUTTON FUNCTIONS

    @Override
    public void onItemClick(ACList item) {
        // Pass the selected item to the next activity using an Intent
        Intent intent = new Intent(this, Review.class);
        intent.putExtra("ac_name", item.getACName());
        intent.putExtra("ac_id", item.getACMembership()+"");
        intent.putExtra("ac_votes", item.getACVotes());

        // To darken the background, set the visibility of the "darken_view" to "visible"
        View darkenView = findViewById(R.id.darken_view);
        darkenView.setVisibility(View.VISIBLE);

        startActivity(intent);
    }
    @Override
    protected void onResume() {
        super.onResume();

        // Hide the darken view when returning to this activity
        View darkenView = findViewById(R.id.darken_view);
        darkenView.setVisibility(View.GONE);
    }


}
