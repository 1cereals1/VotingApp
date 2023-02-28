package com.example.votingapp.AdminSideofThings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.votingapp.R;
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

public class ACAcandidates extends AppCompatActivity {


    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/").child("1RO5aLG_FLEoVdnxJqF50fKIlqeKlGBG01-bhDhGPFZo");

    //creating list of MyItems to store user details
    private final List<ACAList> ACAlist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acacandidates);

        //for nav

        //end of for nav



        //getting RecyclerView from xml file
        final RecyclerView ACArv = findViewById(R.id.ACARV);





        //setting R.V. size fixed for every item in the R.V.
        ACArv.setHasFixedSize(true);


        //setting layout manager to the R.V. Ex: LinearLayoutManager (vertical mode) which is this apparently
        ACArv.setLayoutManager(new LinearLayoutManager(ACAcandidates.this));

        

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //clears old items / users from list to add new data /user
                ACAlist.clear();

                //getting all children from users root
                for (DataSnapshot candidates : snapshot.child("ACcandidates").getChildren()){

                    //prevent crashing by checking if user has all details being asked for
                    if (candidates.hasChild("name") && candidates.hasChild("membership")) {



                        //getting user details from database and storing them into our list one by one
                        final String getACAName = candidates.child("name").getValue(String.class);
                        final Integer getACAID = candidates.child("membership").getValue(Integer.class);



                        //creating the user item with user details
                        ACAList myACAItems = new ACAList(getACAID, getACAName);

                        //adding this user item to list
                        ACAlist.add(myACAItems);

                    }


                }

                //after all the users has been added to the list
                //NOW set adapter to recyclerview or in our case>> idlist
                ACArv.setAdapter(new ACAAdapter(ACAlist, ACAcandidates.this));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}