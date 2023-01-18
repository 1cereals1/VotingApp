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

        final EditText namelogins = findViewById(R.id.NameLogin);
        final EditText idnologin = findViewById(R.id.IDLogin);

        final Button loginb = findViewById(R.id.LoginB);
        final Button goregister = findViewById(R.id.GotoRegister);

        loginb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String nameloginstext = namelogins.getText().toString();
                final String idnologintext = idnologin.getText().toString();

                if(nameloginstext.isEmpty() || idnologintext.isEmpty()){
                    Toast.makeText(Login.this, "Enter details", Toast.LENGTH_SHORT).show();
                }
                else {

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            //NOW to check if Name is exists in our database
                            if (snapshot.hasChild(nameloginstext)){

                                //Name exists in database..
                                //NOW get 'password' or in our case, id no, from firebase data and match it with user entered password
                                final String getidno = snapshot.child(nameloginstext).child("IDNumber").getValue(String.class);

                                if (getidno.equals(idnologintext)){
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

        goregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

    }
}