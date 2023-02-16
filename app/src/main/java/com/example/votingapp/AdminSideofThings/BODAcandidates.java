package com.example.votingapp.AdminSideofThings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.votingapp.R;
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

public class BODAcandidates extends AppCompatActivity {


    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/").child("1RO5aLG_FLEoVdnxJqF50fKIlqeKlGBG01-bhDhGPFZo");

    //creating list of MyItems to store user details
    private final List<BODAList> BODAlist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bodacandidates);

        //for nav

        //end of for nav



        //getting RecyclerView from xml file
        final RecyclerView BODArv = findViewById(R.id.BODARV);





        //setting R.V. size fixed for every item in the R.V.
        BODArv.setHasFixedSize(true);


        //setting layout manager to the R.V. Ex: LinearLayoutManager (vertical mode) which is this apparently
        BODArv.setLayoutManager(new LinearLayoutManager(BODAcandidates.this));



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //clears old items / users from list to add new data /user
                BODAlist.clear();

                //getting all children from users root
                for (DataSnapshot candidates : snapshot.child("BODcandidates").getChildren()){

                    //prevent crashing by checking if user has all details being asked for
                    if (candidates.hasChild("name") && candidates.hasChild("membership")) {



                        //getting user details from database and storing them into our list one by one
                        final String getBODAName = candidates.child("name").getValue(String.class);
                        final Integer getBODAID = candidates.child("membership").getValue(Integer.class);



                        //creating the user item with user details
                        BODAList myBODAItems = new BODAList(getBODAID, getBODAName);

                        //adding this user item to list
                        BODAlist.add(myBODAItems);

                    }


                }

                //after all the users has been added to the list
                //NOW set adapter to recyclerview or in our case>> idlist
                BODArv.setAdapter(new BODAAdapter(BODAlist, BODAcandidates.this));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}