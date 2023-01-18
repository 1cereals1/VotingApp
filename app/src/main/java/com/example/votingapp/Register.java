package com.example.votingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    //must create object of DatabaseReference class to access firebase's realtime database
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText name = findViewById(R.id.Name);
        final EditText idno = findViewById(R.id.ID);

        final Button submitr = findViewById(R.id.SubmitR);
        final Button gologin = findViewById(R.id.GotoLogin);

        final TextView idtext = findViewById(R.id.IDText);

        submitr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //to get the data from these editexts to string vars
                final String nametext = name.getText().toString();
                final String idnotext = idno.getText().toString();

                //a check if user filled up the fields before sending data to firebase
                if (nametext.isEmpty() || idnotext.isEmpty()){
                    Toast.makeText(Register.this, "FILL ALL FIELDS", Toast.LENGTH_SHORT).show();
                }

                //checking if 'password' is same as 'confirm password'
                //if (!idnotext.equals(confirmidnotext)){
                //    Toast.makeText(Register.this, "Passwords must match", Toast.LENGTH_SHORT).show();
                //}

                else {

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            //check if user is not registered before
                            if (snapshot.hasChild(nametext)){
                                Toast.makeText(Register.this, "Name has been Registered before already", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                //sending data back to firebase Realtime Database
                                //in this case, I'm using the name as the 'unique' identifier
                                //so all other details of user comes under Name
                                databaseReference.child("users").child(nametext).child("IDNumber").setValue(idnotext);
                                //databaseReference.child("Users").child(nametext).child("Password").setValue(passtext);
                                //databaseReference.child("Users").child(nametext).child("SomethingSomething").setValue(sometin);

                                //showing success message then finishing activity
                                Toast.makeText(Register.this, "User Registered", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });

        gologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}