package com.example.votingapp.AdminSideofThings;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.votingapp.DrawerBaseActivity;
import com.example.votingapp.R;
import com.example.votingapp.adaptersNlists.AdminSide.MyAdapter;
import com.example.votingapp.adaptersNlists.AdminSide.MyItems;
import com.example.votingapp.databinding.ActivityAdminsEmployeesBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminsEmployees extends DrawerBaseActivity {

    ActivityAdminsEmployeesBinding activityAdminsEmployeesBinding;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/").child("1RO5aLG_FLEoVdnxJqF50fKIlqeKlGBG01-bhDhGPFZo");

    //creating list of MyItems to store user details
    private final List<MyItems> myItemsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admins_employees);

        //for nav
        activityAdminsEmployeesBinding = ActivityAdminsEmployeesBinding.inflate(getLayoutInflater());
        setContentView(activityAdminsEmployeesBinding.getRoot());
        allocatedActivityTitle("Employees");
        //end of for nav

        final Button gotoregister = findViewById(R.id.RegiEmployees);
        final Button gotogooglesheets = findViewById(R.id.googlesheet);

        //getting RecyclerView from xml file
        final RecyclerView idlist = findViewById(R.id.IDList);

        gotogooglesheets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/spreadsheets/d/1RO5aLG_FLEoVdnxJqF50fKIlqeKlGBG01-bhDhGPFZo/edit#gid=0"));
                startActivity(browserIntent);
            }
        });

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
                    if (users.hasChild("Fullname") && users.hasChild("IDNumber")) {

                        //getting user details from database and storing them into our list one by one
                        final Integer getidno = users.child("IDNumber").getValue(Integer.class);
                        final String getfname = users.child("Fullname").getValue(String.class);


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