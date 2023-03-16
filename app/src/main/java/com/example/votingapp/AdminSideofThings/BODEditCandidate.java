package com.example.votingapp.AdminSideofThings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.votingapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class BODEditCandidate extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private Button savebutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bodedit_candidate);


        final EditText BODEditName = findViewById(R.id.BODEditName);
        final EditText BODEditPosition = findViewById(R.id.BODEditPosition);
        final EditText BODEditAge = findViewById(R.id.BODEditAge);
        final EditText BODEditAddress = findViewById(R.id.BODEditAddress);
        final EditText BODEditCivil = findViewById(R.id.BODEditCivil);
        final EditText BODEditBirth = findViewById(R.id.BODEditBirth);
        final EditText BODEditEmail = findViewById(R.id.BODEditEmail);
        final EditText BODEditGender = findViewById(R.id.BODEditGender);
        final EditText BODEditID = findViewById(R.id.BODEditID);
        final EditText BODEditVision = findViewById(R.id.BODEditVision);



        // Get selected data from Intent
        Intent intent = getIntent();

        int BODId = Integer.parseInt(intent.getStringExtra("boda_id"));
        String BODMembershipID = intent.getStringExtra("boda_id");
        String BODName = intent.getStringExtra("boda_name");
        String BODPosition = intent.getStringExtra("boda_position");



        // Get a reference to the Firebase database node for the candidate
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/").child("Candidates").child(BODMembershipID);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Retrieve values from dataSnapshot
                String name = dataSnapshot.child("name").getValue(String.class);
                String age = dataSnapshot.child("age").getValue(String.class);
                String position = dataSnapshot.child("elective").getValue(String.class);
                String address = dataSnapshot.child("address").getValue(String.class);
                String civil = dataSnapshot.child("civil").getValue(String.class);
                String birth = dataSnapshot.child("birth").getValue(String.class);
                String email = dataSnapshot.child("email").getValue(String.class);
                String gender = dataSnapshot.child("gender").getValue(String.class);
                String vision = dataSnapshot.child("vision").getValue(String.class);

                // Set retrieved values to text fields
                BODEditID.setText(String.valueOf(BODId));
                BODEditName.setText(name);
                BODEditAge.setText(String.valueOf(age));
                BODEditPosition.setText(position);
                BODEditAddress.setText(address);
                BODEditCivil.setText(civil);
                BODEditBirth.setText(birth);
                BODEditEmail.setText(email);
                BODEditGender.setText(gender);
                BODEditVision.setText(vision);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors here
            }
        });


        // Set up the save button click listener to update the candidate in the database
        savebutton = findViewById(R.id.SaveEdit);
        savebutton.setOnClickListener(view -> {
            String ID = BODEditID.getText().toString();
            int newID = Integer.parseInt(ID);

            String newName = BODEditName.getText().toString();
            databaseReference.child("name").setValue(newName);
            databaseReference.child("membership").setValue(newID);
            // Finish the activity to return to the previous screen
            finish();
        });

    }




}
