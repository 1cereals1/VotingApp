package com.example.votingapp.UserSideofThings;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.votingapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ContactUs extends AppCompatActivity {



    FirebaseDatabase database;
    DatabaseReference reference;
    TextInputEditText firstname,lastname,email,contact,question;
    int maxid = 0;
    Member member;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        firstname = findViewById(R.id.Xfirstname);
        lastname = findViewById(R.id.Xlastname);
        email = findViewById(R.id.Xemailaddress);
        contact = findViewById(R.id.Xcontactnumber);
        question = findViewById(R.id.Xquestion);
        button = findViewById(R.id.Xbutton);


        member = new Member();
        reference = database.getInstance().getReference().child("Contact Us");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
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
                member.setFirstname(firstname.getText().toString());
                member.setLastname(lastname.getText().toString());
                member.setContact(contact.getText().toString());
                member.setQuestion(question.getText().toString());


                reference.child(String.valueOf(maxid + 1)).setValue(member);



            }

        });





    }
}