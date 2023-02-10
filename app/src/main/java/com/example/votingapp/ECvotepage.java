package com.example.votingapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votingapp.adaptersNlists.ECAdapter;
import com.example.votingapp.adaptersNlists.ECList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ECvotepage extends AppCompatActivity {


    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/").child("1RO5aLG_FLEoVdnxJqF50fKIlqeKlGBG01-bhDhGPFZo");

    //creating list of MyItems to store user details
    private final List<ECList> EClist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecvotepage);

        //for nav

        //end of for nav



        //getting RecyclerView from xml file
        final RecyclerView ECrv = findViewById(R.id.ECRV);





        //setting R.V. size fixed for every item in the R.V.
        ECrv.setHasFixedSize(true);


        //setting layout manager to the R.V. Ex: LinearLayoutManager (vertical mode) which is this apparently
        ECrv.setLayoutManager(new LinearLayoutManager(ECvotepage.this));



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //clears old items / users from list to add new data /user
                EClist.clear();

                //getting all children from users root
                for (DataSnapshot candidates : snapshot.child("ECcandidates").getChildren()){

                    //prevent crashing by checking if user has all details being asked for
                    if (candidates.hasChild("name") && candidates.hasChild("membership")) {



                        //getting user details from database and storing them into our list one by one
                        final String getECName = candidates.child("name").getValue(String.class);
                        final Integer getECID = candidates.child("membership").getValue(Integer.class);



                        //creating the user item with user details
                        ECList myECItems = new ECList(getECID, getECName);

                        //adding this user item to list
                        EClist.add(myECItems);

                    }


                }

                //after all the users has been added to the list
                //NOW set adapter to recyclerview or in our case>> idlist
                ECrv.setAdapter(new ECAdapter(EClist, ECvotepage.this));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}