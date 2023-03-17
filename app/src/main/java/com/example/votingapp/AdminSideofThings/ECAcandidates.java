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
import com.example.votingapp.UserSideofThings.UserHome;
import com.example.votingapp.adaptersNlists.ACbuttonhandler;
import com.example.votingapp.adaptersNlists.AdminSide.ACAAdapter;
import com.example.votingapp.adaptersNlists.AdminSide.ACAList;
import com.example.votingapp.adaptersNlists.AdminSide.ECAAdapter;
import com.example.votingapp.adaptersNlists.AdminSide.ECAList;
import com.example.votingapp.adaptersNlists.UserSide.ACAdapter;
import com.example.votingapp.adaptersNlists.UserSide.ACList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ECAcandidates extends AppCompatActivity implements ECAAdapter.OnItemClickListener{

    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/");
    private RecyclerView ECArv;
    private ECAAdapter mAdapter;
    private ImageButton ECAtoACA, ECAtoUSERHOME;
    private final List<ECAList> ECAlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecacandidates);
        View darkenView = findViewById(R.id.darken_view);
        darkenView.setVisibility(View.GONE);

        ECArv = findViewById(R.id.ECARV);
        ECAtoACA = findViewById(R.id.ecatoaca);
        ECAtoUSERHOME = findViewById(R.id.ectohome);

        ECAtoACA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ECAcandidates.this, ACAcandidates.class));
                finish();
            }
        });

        ECAtoUSERHOME.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        // set layout manager to the RecyclerView
        ECArv.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new ECAAdapter(ECAlist, this);
        mAdapter.setOnItemClickListener(this);
        ECArv.setAdapter(mAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ECAlist.clear();

                for (DataSnapshot candidates : snapshot.child("Candidates").getChildren()) {
                    if (candidates.hasChild("name") && candidates.hasChild("membership")) {
                        final String elective = candidates.child("elective").getValue(String.class);
                        if (elective != null && elective.equals("ELECTION COMITTEE")) {
                            // get candidate details from database
                            final String ecaName = candidates.child("name").getValue(String.class);
                            final String ecaPosition = elective;
                            final String ecaId = candidates.child("membership").getValue(String.class);

                            // create a new ACList object
                            ECAList candidate = new ECAList(ecaId, ecaName, ecaPosition);

                            // add the candidate to the list
                            ECAlist.add(candidate);
                            Log.d("ECAcandidates", "Added candidate: " + ecaName + " with ID: " + ecaId);
                        }
                    }
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("ECAcandidates", "onCancelled: " + error.getMessage());
            }
        });
    }

    //START OF DELETE BUTTON FUNCTIONS
    @Override
    public void onDeleteClick(int position) {
        ECAList deletedItem = ECAlist.get(position);
        mAdapter.removeItem(position);

        String deletedItemId = deletedItem.getECAMembership();

        databaseReference.child("Candidates").child(deletedItemId).removeValue();
    }
    //END OF DELETE BUTTON FUNCTIONS
    @Override
    public void onItemClick(ECAList item) {
        // Pass the selected item to the next activity using an Intent
        Intent intent = new Intent(this, ECEditCandidate.class);
        intent.putExtra("eca_name", item.getECAName());
        intent.putExtra("eca_id", item.getECAMembership()+"");

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
