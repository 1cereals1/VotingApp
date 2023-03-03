package com.example.votingapp.AdminSideofThings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.votingapp.R;
import com.example.votingapp.adaptersNlists.AdminSide.REVAdapter;
import com.example.votingapp.adaptersNlists.AdminSide.REVList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReviewCandidates extends AppCompatActivity {


    // getting Firebase Database reference
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/").child("Candidates");


    // creating
    private final List<REVList> revLists = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_candidates);

        // getting RecyclerView from xml file
        final RecyclerView recyclerView = findViewById(R.id.ReviewpRecyclerview);


        // setting recyclerview size fixed for every item in the recyclerview
        recyclerView.setHasFixedSize(true);

        // setting layout manager to the recyclerview. Ex. LinearLayoutManager (vertical mode)
        recyclerView.setLayoutManager(new LinearLayoutManager(ReviewCandidates.this));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //clear old items / users from list to add new data / users
                revLists.clear();
                //getting all children from users root
                for(DataSnapshot Candidates : snapshot.child("Candidates").getChildren()){

                    // to prevent app crush check if the user has all the details  in Firebase Database
                    if(Candidates.hasChild("name") && Candidates.hasChild("elective") && Candidates.hasChild("membership")) {


                        // getting users details from Firebase Database and store into the list one by one

                        final String getname = Candidates.child("name").getValue(String.class);
                        final String getposition = Candidates.child("elective").getValue(String.class);
                        final String getid = Candidates.child("membership").getValue(String.class);

                        // creating user item with user details
                        REVList revList = new REVList(getname, getposition, getid);

                        // adding this user item to list
                        revLists.add(revList);

                    }

                }

                //after all the users has added to list.
                // now set adapter to RecyclerView
                recyclerView.setAdapter(new REVAdapter(revLists, ReviewCandidates.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}