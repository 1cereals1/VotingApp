package com.example.votingapp.UserSideofThings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class ACvotepage extends AppCompatActivity implements ACAdapter.OnItemClickListener{

    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/").child("1RO5aLG_FLEoVdnxJqF50fKIlqeKlGBG01-bhDhGPFZo");
    private RecyclerView ACrv;
    private ACAdapter mAdapter;
    private final List<ACList> AClist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acvotepage);

        ACrv = findViewById(R.id.ACRV);

        // set layout manager to the RecyclerView
        ACrv.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new ACAdapter(AClist, this);
        mAdapter.setOnItemClickListener(this);
        ACrv.setAdapter(mAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AClist.clear();

                for (DataSnapshot candidates : snapshot.child("ACcandidates").getChildren()){
                    if (candidates.hasChild("name") && candidates.hasChild("membership")) {

                        // get candidate details from database
                        final String acName = candidates.child("name").getValue(String.class);
                        final Integer acId = candidates.child("membership").getValue(Integer.class);

                        // create a new ACList object
                        ACList candidate = new ACList(acId, acName);

                        // add the candidate to the list
                        AClist.add(candidate);
                        Log.d("ACvotepage", "Added candidate: " + acName + " with ID: " + acId);
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

    @Override
    public void onItemClick(ACList item) {
        // Pass the selected item to the next activity using an Intent
        Intent intent = new Intent(this, Review.class);
        intent.putExtra("ac_name", item.getACName());
        intent.putExtra("ac_id", item.getACMembership()+"");

        startActivity(intent);
    }
}
