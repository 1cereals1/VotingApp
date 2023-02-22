package com.example.votingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.votingapp.UserSideofThings.Member;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CandidacyForm extends AppCompatActivity {


    FirebaseDatabase database;
    DatabaseReference reference;
    TextInputEditText email,name,address,membership,age,birth,vision;
    int maxid = 0;
    Member member;
    Button button;

    RadioButton male,female,single,married,divorced,widowed,others,director,audit,election;

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidacy_form);


        email = findViewById(R.id.Xemail);
        name = findViewById(R.id.Xname);
        address = findViewById(R.id.Xaddress);
        membership = findViewById(R.id.Xmembership);
        age = findViewById(R.id.Xage);
        birth = findViewById(R.id.Xbirth);
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






        member = new Member();
        reference = database.getInstance().getReference().child("Candidates");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    i = (int)snapshot.getChildrenCount();
                    maxid = (int) snapshot.getChildrenCount();
                }else  {
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

                member.setEmail(email.getText().toString());
                member.setName(name.getText().toString());
                member.setAddress(address.getText().toString());
                member.setMembership(membership.getText().toString());
                member.setAge(age.getText().toString());
                member.setBirth(birth.getText().toString());
                member.setVision(vision.getText().toString());

                reference.child(String.valueOf(maxid + 1)).setValue(member);

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


                if (male.isChecked()){
                    member.setGender(m1);
                    reference.child(String.valueOf(i+1)).setValue(member);
                }else {
                    member.setGender(m2);
                    reference.child(String.valueOf(i+1)).setValue(member);
                }





                if (single.isChecked()) {
                    member.setCivil(c1);
                } else if (married.isChecked()) {
                    member.setCivil(c2);
                } else if (divorced.isChecked()) {
                    member.setCivil(c3);
                } else if (widowed.isChecked()) {
                    member.setCivil(c4);
                } else if (others.isChecked()) {
                    member.setCivil(c5);
                }

                reference.child(String.valueOf(i+1)).setValue(member);

                if (director.isChecked()) {
                    member.setElective(e1);
                } else if (married.isChecked()) {
                    member.setElective(e2);
                } else if (divorced.isChecked()) {
                    member.setElective(e3);}
                reference.child(String.valueOf(i+1)).setValue(member);

            }
        });


    }
}