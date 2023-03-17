package com.example.votingapp.AdminSideofThings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.votingapp.AdminSideofThings.AdminsEmployees;
import com.example.votingapp.AdminSideofThings.Register;
import com.example.votingapp.R;
import com.example.votingapp.UserSideofThings.ACvotepage;
import com.example.votingapp.UserSideofThings.ECvotepage;
import com.example.votingapp.adaptersNlists.ACbuttonhandler;
import com.example.votingapp.adaptersNlists.AdminSide.ACAAdapter;
import com.example.votingapp.adaptersNlists.AdminSide.ACAList;
import com.example.votingapp.adaptersNlists.AdminSide.BODAAdapter;
import com.example.votingapp.adaptersNlists.AdminSide.BODAList;
import com.example.votingapp.adaptersNlists.UserSide.ACAdapter;
import com.example.votingapp.adaptersNlists.UserSide.ACList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BODAcandidates extends AppCompatActivity implements BODAAdapter.OnItemClickListener{

    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/");
    private RecyclerView BODArv;
    private BODAAdapter mAdapter;
    private ImageButton GotoHome, BODtoACC;
    private final List<BODAList> BODAlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bodacandidates);
        View darkenView = findViewById(R.id.darken_view);
        darkenView.setVisibility(View.GONE);

        BODArv = findViewById(R.id.BODARV);
        GotoHome = findViewById(R.id.Homee);
        BODtoACC = findViewById(R.id.bodtoacc);

        GotoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BODAcandidates.this, AdminDashboard.class));
                finish();
            }
        });

        BODtoACC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BODAcandidates.this, ACAcandidates.class));
                finish();
            }
        });

        // set layout manager to the RecyclerView
        BODArv.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new BODAAdapter(BODAlist, this);
        mAdapter.setOnItemClickListener(this);
        BODArv.setAdapter(mAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                BODAlist.clear();

                for (DataSnapshot candidates : snapshot.child("Candidates").getChildren()) {
                    if (candidates.hasChild("name") && candidates.hasChild("membership")) {
                        final String elective = candidates.child("elective").getValue(String.class);
                        if (elective != null && elective.equals("DIRECTOR")) {
                            // get candidate details from database
                            final String bodaName = candidates.child("name").getValue(String.class);
                            final String bodaPosition = elective;
                            final Long bodaIdLong = Long.parseLong(candidates.child("membership").getValue().toString());
                            final String bodaId = String.valueOf(bodaIdLong);
                            // create a new ACList object
                            BODAList candidate = new BODAList(bodaId, bodaName, bodaPosition);

                            // add the candidate to the list
                            BODAlist.add(candidate);
                            Log.d("BODAcandidates", "Added candidate: " + bodaName + " with ID: " + bodaId);
                        }
                    }
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("BODAcandidates", "onCancelled: " + error.getMessage());
            }
        });
    }

    //START OF DELETE BUTTON FUNCTIONS
    @Override
    public void onDeleteClick(int position) {
        BODAList deletedItem = BODAlist.get(position);
        mAdapter.removeItem(position);

        String deletedItemId = deletedItem.getBODAMembership();

        databaseReference.child("Candidates").child(deletedItemId).removeValue();
    }
    //END OF DELETE BUTTON FUNCTIONS
    @Override
    public void onItemClick(BODAList item) {
        // Pass the selected item to the next activity using an Intent
        Intent intent = new Intent(this, ACEditCandidate.class);
        intent.putExtra("boda_name", item.getBODAName());
        intent.putExtra("boda_id", item.getBODAMembership()+"");

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
