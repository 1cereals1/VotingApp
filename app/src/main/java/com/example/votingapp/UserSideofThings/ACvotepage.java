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

public class ACvotepage extends AppCompatActivity {


    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/").child("1RO5aLG_FLEoVdnxJqF50fKIlqeKlGBG01-bhDhGPFZo");



    //creating list of MyItems to store user details
    private final List<ACList> AClist = new ArrayList<>();


    ACAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acvotepage);

        //for nav

        //end of for nav

        View layoutView = getLayoutInflater().inflate(R.layout.recyclerview_adapter_layout_acvotepage, null);
        Button btnEdit = layoutView.findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedPosition = mAdapter.getSelectedPosition();
                if (selectedPosition != RecyclerView.NO_POSITION) {
                    ACList selectedItem = AClist.get(selectedPosition);
                    Intent intent = new Intent(ACvotepage.this, Review.class);
                    intent.putExtra("data", selectedItem);
                    startActivity(intent);
                }
            }
        });


        //getting RecyclerView from xml file
        final RecyclerView ACrv = findViewById(R.id.ACRV);
        mAdapter = new ACAdapter(AClist, this);
        ACrv.setAdapter(mAdapter);


        //setting R.V. size fixed for every item in the R.V.
        ACrv.setHasFixedSize(true);


        //setting layout manager to the R.V. Ex: LinearLayoutManager (vertical mode) which is this apparently
        ACrv.setLayoutManager(new LinearLayoutManager(ACvotepage.this));

        mAdapter.setOnItemClickListener(new ACAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ACList data) {
                Log.d("ACvotepage", "Button clicked");
                // Start a new activity and pass the data to it
                Intent intent = new Intent(ACvotepage.this, Review.class);
                intent.putExtra("data", data);
                startActivity(intent);
            }
        });




        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //clears old items / users from list to add new data /user
                AClist.clear();

                //getting all children from users root
                for (DataSnapshot candidates : snapshot.child("ACcandidates").getChildren()){

                    //prevent crashing by checking if user has all details being asked for
                    if (candidates.hasChild("name") && candidates.hasChild("membership")) {



                        //getting user details from database and storing them into our list one by one
                        final String getACName = candidates.child("name").getValue(String.class);
                        final Integer getACID = candidates.child("membership").getValue(Integer.class);
                        final Button getACButton = findViewById(R.id.btn_edit);



                        //creating the user item with user details
                        ACList myACItems = new ACList(getACID, getACName, getACButton);


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
