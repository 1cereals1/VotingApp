package com.example.votingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.votingapp.adaptersNlists.ACAdapter;
import com.example.votingapp.adaptersNlists.ACList;
import com.example.votingapp.adaptersNlists.BODAdapter;
import com.example.votingapp.adaptersNlists.BODList;
import com.example.votingapp.adaptersNlists.ECAdapter;
import com.example.votingapp.adaptersNlists.ECList;
import com.example.votingapp.adaptersNlists.MyAdapter;
import com.example.votingapp.adaptersNlists.MyItems;
import com.example.votingapp.databinding.ActivityAdminsEmployeesBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ACvotepage extends AppCompatActivity {


    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/").child("1RO5aLG_FLEoVdnxJqF50fKIlqeKlGBG01-bhDhGPFZo");

    //creating list of MyItems to store user details
    private final List<ACList> AClist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acvotepage);

        //for nav

        //end of for nav



        //getting RecyclerView from xml file
        final RecyclerView ACrv = findViewById(R.id.ACRV);





        //setting R.V. size fixed for every item in the R.V.
        ACrv.setHasFixedSize(true);


        //setting layout manager to the R.V. Ex: LinearLayoutManager (vertical mode) which is this apparently
        ACrv.setLayoutManager(new LinearLayoutManager(ACvotepage.this));



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //clears old items / users from list to add new data /user
                AClist.clear();

                //getting all children from users root
                for (DataSnapshot candidates : snapshot.child("candidates").getChildren()){

                    //prevent crashing by checking if user has all details being asked for
                    if (candidates.hasChild("name") && candidates.hasChild("membership")) {



                        //getting user details from database and storing them into our list one by one
                        final Integer getACID = candidates.child("membership").getValue(Integer.class);
                        final String getACName = candidates.child("name").getValue(String.class);


                        //creating the user item with user details
                        ACList myACItems = new ACList(getACName, getACID);

                        //adding this user item to list
                        AClist.add(myACItems);

                    }


                }

                //after all the users has been added to the list
                //NOW set adapter to recyclerview or in our case>> idlist
                ACrv.setAdapter(new ACAdapter(AClist, ACvotepage.this));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}