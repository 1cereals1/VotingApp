package com.example.votingapp.AdminSideofThings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.votingapp.R;
import com.example.votingapp.UserSideofThings.ACvotepage;
import com.example.votingapp.UserSideofThings.BODvotepage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ECEditCandidate extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private ImageButton ECEDITBACK;
    private Button savebutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecedit_candidate);

        ECEDITBACK = findViewById(R.id.ecbacktocandidate);

        ECEDITBACK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ECEditCandidate.this, ECAcandidates.class));
                finish();
            }
        });

        final EditText ECEditName = findViewById(R.id.ECEditName);
        final EditText ECEditPosition = findViewById(R.id.ECEditPosition);
        final EditText ECEditAge = findViewById(R.id.ECEditAge);
        final EditText ECEditAddress = findViewById(R.id.ECEditAddress);
        final EditText ECEditCivil = findViewById(R.id.ECEditCivil);
        final EditText ECEditBirth = findViewById(R.id.ECEditBirth);
        final EditText ECEditEmail = findViewById(R.id.ECEditEmail);
        final EditText ECEditGender = findViewById(R.id.ECEditGender);
        final EditText ECEditID = findViewById(R.id.ECEditID);
        final EditText ECEditVision = findViewById(R.id.ECEditVision);



        // Get selected data from Intent
        Intent intent = getIntent();

        int ECAId = Integer.parseInt(intent.getStringExtra("eca_id"));
        String ECAMembershipID = intent.getStringExtra("eca_id");
        String ECAName = intent.getStringExtra("eca_name");
        String ECAPosition = intent.getStringExtra("eca_position");



        // Get a reference to the Firebase database node for the candidate
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/").child("Candidates").child(ECAMembershipID);
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
                ECEditID.setText(String.valueOf(ECAId));
                ECEditName.setText(name);
                ECEditAge.setText(String.valueOf(age));
                ECEditPosition.setText(position);
                ECEditAddress.setText(address);
                ECEditCivil.setText(civil);
                ECEditBirth.setText(birth);
                ECEditEmail.setText(email);
                ECEditGender.setText(gender);
                ECEditVision.setText(vision);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors here
            }
        });


        // Set up the save button click listener to update the candidate in the database
        savebutton = findViewById(R.id.SaveEdit);
        savebutton.setOnClickListener(view -> {
            String ID = ECEditID.getText().toString();
            int newID = Integer.parseInt(ID);

            String newName = ECEditName.getText().toString();
            databaseReference.child("name").setValue(newName);
            databaseReference.child("membership").setValue(newID);
            // Finish the activity to return to the previous screen
            finish();
        });

    }




}
