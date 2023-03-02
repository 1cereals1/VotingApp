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
import com.example.votingapp.adaptersNlists.UserSide.ECAdapter;
import com.example.votingapp.adaptersNlists.UserSide.ECList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ECvotepage extends AppCompatActivity implements ECAdapter.OnItemClickListener{

    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/").child("1RO5aLG_FLEoVdnxJqF50fKIlqeKlGBG01-bhDhGPFZo");
    private RecyclerView ECrv;
    private ECAdapter mAdapter;
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

        mAdapter = new ECAdapter(EClist, this);
        mAdapter.setOnItemClickListener(this);
        ECrv.setAdapter(mAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                EClist.clear();

                for (DataSnapshot candidates : snapshot.child("ECcandidates").getChildren()){
                    if (candidates.hasChild("name") && candidates.hasChild("membership")) {

                        // get candidate details from database
                        final String ecName = candidates.child("name").getValue(String.class);
                        final Integer ecId = candidates.child("membership").getValue(Integer.class);

                        // create a new ACList object
                        ECList candidate = new ECList(ecId, ecName);

                        // add the candidate to the list
                        EClist.add(candidate);
                        Log.d("ECvotepage", "Added candidate: " + ecName + " with ID: " + ecId);
                    }
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("ECvotepage", "onCancelled: " + error.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(ECList item) {
        // Pass the selected item to the next activity using an Intent
        Intent intent = new Intent(this, Review.class);
        intent.putExtra("ec_name", item.getECName());
        intent.putExtra("ec_id", item.getECMembership()+"");

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
