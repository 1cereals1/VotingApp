package com.example.votingapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.votingapp.AdminSideofThings.AdminLogin;
import com.example.votingapp.AdminSideofThings.AdminsEmployees;
import com.example.votingapp.UserSideofThings.ACvotepage;
import com.example.votingapp.UserSideofThings.ECvotepage;
import com.example.votingapp.UserSideofThings.UserHome;
import com.example.votingapp.UserSideofThings.VotePage;
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

public class Login extends AppCompatActivity {



    private EditText idnologin,edtPhone, edtOTP;
    private TextView registerb;

    private Button loginb, generateOTPBtn;

    // string for storing our verification ID
     String verificationId;

    private Context context;



    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/").child("1RO5aLG_FLEoVdnxJqF50fKIlqeKlGBG01-bhDhGPFZo");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        idnologin = findViewById(R.id.IDLogin);
        edtPhone = findViewById(R.id.idEdtPhoneNumber);



        generateOTPBtn = findViewById(R.id.idBtnGetOtp);
        registerb = findViewById(R.id.Register);


        FirebaseAuth mAuth = FirebaseAuth.getInstance();




       // setting onclick listener for generate OTP button.
        generateOTPBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                final String idnologintext = idnologin.getText().toString();
                final String edtPhonetext = edtPhone.getText().toString();
                // below line is for checking whether the user
                // has entered his mobile number or not.
                if (TextUtils.isEmpty(edtPhone.getText().toString())) {
                    // when mobile number text field is empty
                    // displaying a toast message.
                    Toast.makeText(Login.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                } else {

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            //NOW to check if the ID used exists in our database
                            if (snapshot.hasChild(idnologintext)){

                                //id exists in database..
                                //NOW get 'Child' from firebase data and match it with user entered 'Child'
                                final String getnumber = snapshot.child(idnologintext).child("ContactNumber").getValue(String.class);
                                final String getid = snapshot.child(idnologintext).child("IDNumber").getValue(String.class);

                                if (getnumber != null && getnumber.equals(edtPhonetext) ){
                                    // if the text field is not empty we are calling our
                                    // send OTP method for getting OTP from Firebase.
                                    String phone = edtPhone.getText().toString();
                                    String phonenum = "+63 " + phone.substring(1, 4) + "-" + phone.substring(4, 7) + "-" + phone.substring(7);
                                    sendVerificationCode(phonenum);
                                }
                                else {
                                    Toast.makeText(Login.this, "WRONG NUMBER", Toast.LENGTH_SHORT).show();

                                }
                            }
                            else {
                                Toast.makeText(Login.this, "No employee has this ID", Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(Login.this, AdminLogin.class));
                finish();
            }
        });




    }


    //Here we start with the phone number auth
    private void signInWithCredential(PhoneAuthCredential credential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // if the code is correct and the task is successful
                            // we are sending our user to new activity.
                            Intent i = new Intent(Login.this, UserHome.class);
                            startActivity(i);
                            finish();
                        } else {
                            // if the code is not correct then we are
                            // displaying an error message to the user.
                            Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "You have exited application(But not really since this is a prototype)", Toast.LENGTH_SHORT).show();

    }


    private void sendVerificationCode(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this) // activity to launch the OTP verification UI
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                // Callback triggered if auto verification of code happens.
                                final String code = phoneAuthCredential.getSmsCode();

                                // checking if the code
                                // is null or not.
                                if (code != null) {
                                    // if the code is not null then
                                    // we are setting that code to
                                    // our OTP edittext field.
                                    edtOTP.setText(code);

                                    // after setting this code
                                    // to OTP edittext field we
                                    // are calling our verifycode method.
                                    verifyCode(code);
                                }
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                // Callback triggered if verification fails.
                                Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                // Callback triggered after OTP is sent to the user.
                                Login.this.verificationId = verificationId;
                                Toast.makeText(Login.this, "OTP sent successfully", Toast.LENGTH_LONG).show();

                                // create an intent to start the OTPVerificationActivity
                                Intent intent = new Intent(Login.this, OTPVerificationActivity.class);
                                intent.putExtra("verificationId", verificationId);
                                startActivity(intent);
                            }
                        })
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    // callback method is called on Phone auth provider.
    public PhoneAuthProvider.OnVerificationStateChangedCallbacks

            // initializing our callbacks for on
            // verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // below method is used when
        // OTP is sent from Firebase
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            // when we receive the OTP it
            // contains a unique id which
            // we are storing in our string
            // which we have already created.
            verificationId = s;
        }

        // this method is called when user
        // receive OTP from Firebase.
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            // below line is used for getting OTP code
            // which is sent in phone auth credentials.
            final String code = phoneAuthCredential.getSmsCode();

            // checking if the code
            // is null or not.
            if (code != null) {
                // if the code is not null then
                // we are setting that code to
                // our OTP edittext field.
                edtOTP.setText(code);

                // after setting this code
                // to OTP edittext field we
                // are calling our verifycode method.
                verifyCode(code);
            }
        }

        // this method is called when firebase doesn't
        // sends our OTP code due to any error or issue.
        @Override
        public void onVerificationFailed(FirebaseException e) {
            // displaying error message with firebase exception.
            Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    // below method is use to verify code from Firebase.
    public void verifyCode(String code) {
        // below line is used for getting
        // credentials from our verification id and code.
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential);
    }
}