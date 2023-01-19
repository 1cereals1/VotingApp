package com.example.votingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminsEmployees extends AppCompatActivity {

    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/");

    //creating list of MyItems to store user details
    private final List<MyItems> myItemsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admins_employees);

        final Button gotoregister = findViewById(R.id.RegiEmployees);

        //getting RecyclerView from xml file
        final RecyclerView idlist = findViewById(R.id.IDList);



        gotoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminsEmployees.this, Register.class));
            }
        });

        //setting R.V. size fixed for every item in the R.V.
        idlist.setHasFixedSize(true);


        //setting layout manager to the R.V. Ex: LinearLayoutManager (vertical mode) which is this apparently
        idlist.setLayoutManager(new LinearLayoutManager(AdminsEmployees.this));


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //clears old items / users from list to add new data /user
                myItemsList.clear();

                //getting all children from users root
                for (DataSnapshot users : snapshot.child("users").getChildren()){

                    //prevent crashing by checking if user has all details being asked for
                    if (users.hasChild("FullName") && users.hasChild("IDNumber")) {

                        //getting user details from database and storing them into our list one by one
                        final String getidno = users.child("IDNumber").getValue(String.class);
                        final String getfname = users.child("FullName").getValue(String.class);


                        //creating the user item with user details
                        MyItems myItems = new MyItems(getidno, getfname);

                        //adding this user item to list
                        myItemsList.add(myItems);

                    }


                }

                //after all the users has been added to the list
                //NOW set adapter to recyclerview or in our case>> idlist
                idlist.setAdapter(new MyAdapter(myItemsList, AdminsEmployees.this));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}