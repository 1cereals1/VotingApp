package com.example.votingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class AdminLogin extends AppCompatActivity {



    private EditText adminidlogin,adminpasslogin;

    private Button adminloginb;

    // string for storing our verification ID
    String verificationId;

    private Context context;



    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/").child("1RO5aLG_FLEoVdnxJqF50fKIlqeKlGBG01-bhDhGPFZo");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        adminidlogin = findViewById(R.id.adminID);
        adminpasslogin = findViewById(R.id.adminPass);

        adminloginb = findViewById(R.id.adminLogin);
        final Button buttona = findViewById(R.id.button);





        buttona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminLogin.this, Login.class));
            }
        });


        adminloginb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String adminidloginS = adminidlogin.getText().toString();
                final String adminpassloginS = adminpasslogin.getText().toString();

                if(adminidloginS.isEmpty()){
                    Toast.makeText(AdminLogin.this, "Fill the field/s first", Toast.LENGTH_SHORT).show();
                }
                else {

                    databaseReference.child("admins").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            //NOW to check if the ID used exists in our database
                            if (snapshot.hasChild(adminidloginS)){

                                //id exists in database..
                                //NOW get 'Child' from firebase data and match it with user entered 'Child'
                                final String getpass = snapshot.child(adminidloginS).child("AdminPassword").getValue(String.class);

                                if (getpass.equals("\""+adminpassloginS+"\"")){
                                    startActivity(new Intent(AdminLogin.this, AdminsEmployees.class));
                                    finish();
                                }
                                else {
                                    Toast.makeText(AdminLogin.this, "WRONG DETAILS", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(AdminLogin.this, "No admin has this ID", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        });

    }
}