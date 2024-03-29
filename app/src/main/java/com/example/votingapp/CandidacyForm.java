package com.example.votingapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.votingapp.AdminSideofThings.AdminDashboard;
import com.example.votingapp.AdminSideofThings.Mail;
import com.example.votingapp.UserSideofThings.CandidacyFormConfirm;
import com.example.votingapp.UserSideofThings.Member;
import com.example.votingapp.UserSideofThings.UserHome;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

import java.util.Calendar;

public class CandidacyForm extends AppCompatActivity {


    FirebaseDatabase database;
    DatabaseReference reference;
    DatabaseReference usercheck;
    TextInputEditText email, name, address, membership, age, birth,vision;


    int maxid = 0;
    Member member;
    Button button;
    ImageButton Back;

    RadioButton male, female, single, married, divorced, widowed, others, director, audit, election;

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    int i = 0;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidacy_form);

        mDisplayDate = (TextView) findViewById(R.id.tvDate);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        CandidacyForm.this,
                        0,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // update the text view with the selected date
                                mDisplayDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                            }
                        },
                        year,
                        month,
                        day
                );

                dialog.show();
            }
        });


        Back = findViewById(R.id.Backbtn);


        email = findViewById(R.id.Xemail);
        name = findViewById(R.id.Xname);
        address = findViewById(R.id.Xaddress);
        membership = findViewById(R.id.Xmembership);
        age = findViewById(R.id.Xage);
        birth = findViewById(R.id.tvDate);
        email = findViewById(R.id.Xemail);
        vision = findViewById(R.id.Xvision);
        button = findViewById(R.id.Xbutton);
        male = findViewById(R.id.Xmale);
        female = findViewById(R.id.Xfemale);
        single = findViewById(R.id.Xsingle);
        married = findViewById(R.id.Xmarried);
        divorced = findViewById(R.id.Xdivorced);
        widowed = findViewById(R.id.Xwidowed);
        others = findViewById(R.id.Xothers);
        director = findViewById(R.id.Xdirector);
        audit = findViewById(R.id.Xaudit);
        election = findViewById(R.id.Xelection);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CandidacyForm.this, UserHome.class));
                finish();
            }
        });



        member = new Member();
        reference = databaseReference.child("Candidates");
        usercheck = databaseReference.child("1RO5aLG_FLEoVdnxJqF50fKIlqeKlGBG01-bhDhGPFZo").child("users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    i = (int) snapshot.getChildrenCount();
                    maxid = (int) snapshot.getChildrenCount();
                } else {
                    ///
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String membershipValue = membership.getText().toString();

                usercheck.child(membershipValue).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            member.setEmail(email.getText().toString());
                            member.setName(name.getText().toString());
                            member.setAddress(address.getText().toString());
                            member.setMembership(membershipValue);
                            member.setAge(age.getText().toString());
                            member.setBirth(birth.getText().toString());
                            member.setVision(vision.getText().toString());
                            member.setVotestatus(false);
                            member.setVotes(0);

                            reference.child(String.valueOf(member.getMembership())).setValue(member);

                            String m1 = male.getText().toString();
                            String m2 = female.getText().toString();

                            String c1 = single.getText().toString();
                            String c2 = married.getText().toString();
                            String c3 = divorced.getText().toString();
                            String c4 = widowed.getText().toString();
                            String c5 = others.getText().toString();

                            String e1 = director.getText().toString();
                            String e2 = audit.getText().toString();
                            String e3 = election.getText().toString();


                            if (male.isChecked()) {
                                member.setGender(m1);
                                reference.child(String.valueOf(member.getMembership())).setValue(member);
                            } else {
                                member.setGender(m2);
                                reference.child(String.valueOf(member.getMembership())).setValue(member);
                            }


                            if (single.isChecked()) {
                                member.setCivil(c1);
                            }
                            if (married.isChecked()) {
                                member.setCivil(c2);
                            }
                            if (divorced.isChecked()) {
                                member.setCivil(c3);
                            }
                            if (widowed.isChecked()) {
                                member.setCivil(c4);
                            }
                            if (others.isChecked()) {
                                member.setCivil(c5);
                            }

                            reference.child(String.valueOf(member.getMembership())).setValue(member);

                            if (director.isChecked()) {
                                member.setElective(e1);
                            }
                            if (audit.isChecked()) {
                                member.setElective(e2);
                            }
                            if (election.isChecked()) {
                                member.setElective(e3);
                            }
                            reference.child(String.valueOf(member.getMembership())).setValue(member);

                            startActivity(new Intent(CandidacyForm.this, CandidacyFormConfirm.class));
                            finish();

                        }else {
                            Toast.makeText(CandidacyForm.this, "Member with this ID does not exist", Toast.LENGTH_SHORT).show();
                            return;
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle possible errors
                    }
                });
            }
        });


    }
}