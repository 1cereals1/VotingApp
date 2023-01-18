package com.example.votingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final EditText idnologin = findViewById(R.id.IDLogin);
        final EditText passlogin = findViewById(R.id.Pass);

        final Button loginb = findViewById(R.id.LoginB);
        final Button registerb = findViewById(R.id.Register);


        loginb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String idnologintext = idnologin.getText().toString();
                final String passloginstext = passlogin.getText().toString();

                if(idnologintext.isEmpty() || passloginstext.isEmpty()){
                    Toast.makeText(Login.this, "Fill the fields first", Toast.LENGTH_SHORT).show();
                }
                else {

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            //NOW to check if LastName is exists in our database
                            if (snapshot.hasChild(idnologintext)){

                                //LastName exists in database..
                                //NOW get 'Pass' from firebase data and match it with user entered Pass
                                final String getpass = snapshot.child(idnologintext).child("Pass").getValue(String.class);

                                if (getpass.equals(passloginstext)){
                                    Toast.makeText(Login.this, "Logged in", Toast.LENGTH_SHORT).show();

                                    //off to the main activity!!!
                                    startActivity(new Intent(Login.this, MainActivity.class));
                                    finish();
                                }
                                else {
                                    Toast.makeText(Login.this, "WRONG DETAILS", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(Login.this, "WRONG DETAILS", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        });

        registerb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

    }
}