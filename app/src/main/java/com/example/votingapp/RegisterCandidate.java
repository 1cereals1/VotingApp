package com.example.votingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.votingapp.adaptersNlists.AdapterCand;
import com.example.votingapp.adaptersNlists.CandidatesList;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RegisterCandidate extends AppCompatActivity {


    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/");

    //creating list of MyItems to store user details
    private final List<CandidatesList> myItemsListCand = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_candidate);

        //getting RecyclerView from xml file
        final RecyclerView idcandlist = findViewById(R.id.IDCandList);


        final Button gotocandregister = findViewById(R.id.GotoCandRegi);
        gotocandregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterCandidate.this, RegisterCandidatesForm.class));
            }
        });

        //setting R.V. size fixed for every item in the R.V.
        idcandlist.setHasFixedSize(true);


        //setting layout manager to the R.V. Ex: LinearLayoutManager (vertical mode) which is this apparently
        idcandlist.setLayoutManager(new LinearLayoutManager(RegisterCandidate.this));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //clears old items / users from list to add new data /user
                myItemsListCand.clear();

                //getting all children from users root
                for (DataSnapshot candidates : snapshot.child("candidates").getChildren()){

                    //prevent crashing by checking if user has all details being asked for
                    if (candidates.hasChild("FullName") && candidates.hasChild("IDNumber")) {

                        //getting user details from database and storing them into our list one by one
                        final String getidnocand = candidates.child("IDNumber").getValue(String.class);
                        final String getfnamecand = candidates.child("FullName").getValue(String.class);
                        final String getpositioncand = candidates.child("Position").getValue(String.class);


                        //creating the user item with user details

                        CandidatesList candidatesList = new CandidatesList(getidnocand, getfnamecand, getpositioncand);

                        //adding this user item to list
                        myItemsListCand.add(candidatesList);

                    }


                }

                //after all the users has been added to the list
                //NOW set adapter to recyclerview or in our case>> idlist
                idcandlist.setAdapter(new AdapterCand(myItemsListCand, RegisterCandidate.this));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}