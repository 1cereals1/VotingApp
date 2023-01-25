package com.example.votingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class RegisterCandidatesForm extends AppCompatActivity {

    //must create object of DatabaseReference class to access firebase's realtime database
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_candidates_form);

        final EditText idnocand = findViewById(R.id.IDCand);
        final EditText orgcand = findViewById(R.id.OrganizationCand);
        final EditText fnamecand = findViewById(R.id.FullNameCand);
        final EditText emailcand = findViewById(R.id.EmailCand);
        final EditText contactnumbercand = findViewById(R.id.ContactNumberCand);

        final Button submitrcand = findViewById(R.id.SubmitRCand);


        final TextView idtextcand = findViewById(R.id.IDT);

        submitrcand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //to get the data from these editexts to string vars
                final String idnotextcand = idnocand.getText().toString();
                final String orgtextcand = orgcand.getText().toString();
                final String fnametextcand = fnamecand.getText().toString();
                final String emailtextcand = emailcand.getText().toString();
                final String contactnumbertextcand = contactnumbercand.getText().toString();


                //a check if user filled up the fields before sending data to firebase
                if (idnotextcand.isEmpty() || orgtextcand.isEmpty() || fnametextcand.isEmpty() || emailtextcand.isEmpty() || contactnumbertextcand.isEmpty()){
                    Toast.makeText(RegisterCandidatesForm.this, "FILL ALL FIELDS FIRST", Toast.LENGTH_SHORT).show();
                }

                //checking if 'Pass' is same as 'confirm Pass'
                //if (!idnotext.equals(confirmidnotext)){
                //    Toast.makeText(Register.this, "Passwords must match", Toast.LENGTH_SHORT).show();
                //}

                else {

                    databaseReference.child("Candidates").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            //check if user is not registered before
                            if (snapshot.hasChild(idnotextcand)){
                                Toast.makeText(RegisterCandidatesForm.this, "This I.D. Number has been Registered before already", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                //sending data back to firebase Realtime Database
                                //in this case, I'm using the IDNO# as the 'unique' identifier
                                //so all other details of user comes under IDNO#
                                databaseReference.child("candidates").child(idnotextcand).child("IDNumber").setValue(idnotextcand);
                                databaseReference.child("candidates").child(idnotextcand).child("Position").setValue(orgtextcand);
                                databaseReference.child("candidates").child(idnotextcand).child("FullName").setValue(fnametextcand);
                                databaseReference.child("candidates").child(idnotextcand).child("Email").setValue(emailtextcand);
                                databaseReference.child("candidates").child(idnotextcand).child("ContactNumber").setValue(contactnumbertextcand);

                                //adding to our list of Users after adding them to database


                                //showing success message then finishing activity
                                Toast.makeText(RegisterCandidatesForm.this, "User Registered", Toast.LENGTH_SHORT).show();
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



    }
}