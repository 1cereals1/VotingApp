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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ACEditCandidate extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private ImageButton ACEDITBACK;
    private Button savebutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_candidate);
        ACEDITBACK = findViewById(R.id.acbacktocandidate);

        ACEDITBACK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ACEditCandidate.this, ACAcandidates.class));
                finish();
            }
        });


        final EditText ACEditName = findViewById(R.id.ACEditName);
        final EditText ACEditPosition = findViewById(R.id.ACEditPosition);
        final EditText ACEditAge = findViewById(R.id.ACEditAge);
        final EditText ACEditAddress = findViewById(R.id.ACEditAddress);
        final EditText ACEditCivil = findViewById(R.id.ACEditCivil);
        final EditText ACEditBirth = findViewById(R.id.ACEditBirth);
        final EditText ACEditEmail = findViewById(R.id.ACEditEmail);
        final EditText ACEditGender = findViewById(R.id.ACEditGender);
        final EditText ACEditID = findViewById(R.id.ACEditID);
        final EditText ACEditVision = findViewById(R.id.ACEditVision);



        // Get selected data from Intent
        Intent intent = getIntent();

        int ACAId = Integer.parseInt(intent.getStringExtra("aca_id"));
        String ACAMembershipID = intent.getStringExtra("aca_id");
        String ACAName = intent.getStringExtra("aca_name");
        String ACAPosition = intent.getStringExtra("aca_position");



        // Get a reference to the Firebase database node for the candidate
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/").child("Candidates").child(ACAMembershipID);
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
                ACEditID.setText(String.valueOf(ACAId));
                ACEditName.setText(name);
                ACEditAge.setText(String.valueOf(age));
                ACEditPosition.setText(position);
                ACEditAddress.setText(address);
                ACEditCivil.setText(civil);
                ACEditBirth.setText(birth);
                ACEditEmail.setText(email);
                ACEditGender.setText(gender);
                ACEditVision.setText(vision);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors here
            }
        });


        // Set up the save button click listener to update the candidate in the database
        savebutton = findViewById(R.id.SaveEdit);
        savebutton.setOnClickListener(view -> {
            String ID = ACEditID.getText().toString();
            int newID = Integer.parseInt(ID);

            String newName = ACEditName.getText().toString();
            databaseReference.child("name").setValue(newName);
            databaseReference.child("membership").setValue(newID);
            // Finish the activity to return to the previous screen
            finish();
        });

    }




}
