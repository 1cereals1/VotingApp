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
import com.example.votingapp.adaptersNlists.ACbuttonhandler;
import com.example.votingapp.adaptersNlists.AdminSide.ACAAdapter;
import com.example.votingapp.adaptersNlists.AdminSide.ACAList;
import com.example.votingapp.adaptersNlists.UserSide.ACAdapter;
import com.example.votingapp.adaptersNlists.UserSide.ACList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ACAcandidates extends AppCompatActivity implements ACAAdapter.OnItemClickListener{

    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/");
    private RecyclerView ACArv;
    private ACAAdapter mAdapter;
    private ImageButton ACAtoBODA, ACAtoECA;
    private final List<ACAList> ACAlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acacandidates);
        View darkenView = findViewById(R.id.darken_view);
        darkenView.setVisibility(View.GONE);

        ACArv = findViewById(R.id.ACARV);
        ACAtoBODA = findViewById(R.id.acatoboda);
        ACAtoECA = findViewById(R.id.acatoeca);

        ACAtoBODA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ACAcandidates.this, BODAcandidates.class));
                finish();
            }
        });

        ACAtoECA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ACAcandidates.this, ECAcandidates.class));
                finish();
            }
        });

        // set layout manager to the RecyclerView
        ACArv.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new ACAAdapter(ACAlist, this);
        mAdapter.setOnItemClickListener(this);
        ACArv.setAdapter(mAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ACAlist.clear();

                for (DataSnapshot candidates : snapshot.child("Candidates").getChildren()) {
                    if (candidates.hasChild("name") && candidates.hasChild("membership")) {
                        final String elective = candidates.child("elective").getValue(String.class);
                        if (elective != null && elective.equals("AUDIT COMITTEE")) {
                            // get candidate details from database
                            final String acaName = candidates.child("name").getValue(String.class);
                            final String acaPosition = elective;
                            final String acaId = candidates.child("membership").getValue().toString();
                            //final String acaId = String.valueOf(acaIdLong);

                            // create a new ACList object
                            ACAList candidate = new ACAList(acaId, acaName, acaPosition);

                            // add the candidate to the list
                            ACAlist.add(candidate);
                            Log.d("ACAcandidates", "Added candidate: " + acaName + " with ID: " + acaId);
                        }
                    }
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("ACAcandidates", "onCancelled: " + error.getMessage());
            }
        });
    }



    //START OF DELETE BUTTON FUNCTIONS
    @Override
    public void onDeleteClick(int position) {
        ACAList deletedItem = ACAlist.get(position);
        mAdapter.removeItem(position);

        String deletedItemId = deletedItem.getACAMembership();


        // Pass the selected item to the next activity using an Intent
        Intent delintent = new Intent(this, ACDeleteConfirm.class);
        delintent.putExtra("acaid", deletedItemId);

        // To darken the background, set the visibility of the "darken_view" to "visible"
        View darkenView = findViewById(R.id.darken_view);
        darkenView.setVisibility(View.VISIBLE);

        startActivity(delintent);
    }
    //END OF DELETE BUTTON FUNCTIONS


    @Override
    public void onItemClick(ACAList item) {
        // Pass the selected item to the next activity using an Intent
        Intent intent = new Intent(this, ACEditCandidate.class);
        intent.putExtra("aca_name", item.getACAName());
        intent.putExtra("aca_id", item.getACAMembership()+"");

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
