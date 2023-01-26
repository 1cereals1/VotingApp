package com.example.votingapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.votingapp.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class Register extends AppCompatActivity {


    FirebaseStorage storage;
    Uri imageUri;

    //must create object of DatabaseReference class to access firebase's realtime database
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //

        ImageView userpicr = findViewById(R.id.UserPicr);
        final Button uploadpic = findViewById(R.id.UploadPic);

        storage = FirebaseStorage.getInstance();

        userpicr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("image/*");

            }
        });
        uploadpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("image/*");

            }
        });


        final EditText idno = findViewById(R.id.ID);
        final EditText org = findViewById(R.id.Organization);
        final EditText lname = findViewById(R.id.LastName);
        final EditText fname = findViewById(R.id.FirstName);
        final EditText mname = findViewById(R.id.MiddleName);
        final EditText email = findViewById(R.id.Email);
        final EditText contactnumber = findViewById(R.id.ContactNumber);

        final Button submitr = findViewById(R.id.SubmitR);


        final TextView idtext = findViewById(R.id.IDT);




        submitr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();

                //to get the data from these editexts to string vars
                final String idnotext = idno.getText().toString();
                final String orgtext = org.getText().toString();
                final String lnametext = lname.getText().toString();
                final String fnametext = fname.getText().toString();
                final String mnametext = mname.getText().toString();
                final String emailtext = email.getText().toString();
                final String contactnumbertext = contactnumber.getText().toString();


                //a check if user filled up the fields before sending data to firebase
                if (idnotext.isEmpty() || orgtext.isEmpty() || lnametext.isEmpty() || fnametext.isEmpty() || mnametext.isEmpty() || emailtext.isEmpty() || contactnumbertext.isEmpty()){
                    Toast.makeText(Register.this, "FILL ALL FIELDS FIRST", Toast.LENGTH_SHORT).show();
                }

                //checking if 'Pass' is same as 'confirm Pass'
                //if (!idnotext.equals(confirmidnotext)){
                //    Toast.makeText(Register.this, "Passwords must match", Toast.LENGTH_SHORT).show();
                //}

                else {

                    databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            //check if user is not registered before
                            if (snapshot.hasChild(idnotext)){
                                Toast.makeText(Register.this, "This I.D. Number has been Registered before already", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                //sending data back to firebase Realtime Database
                                //in this case, I'm using the IDNO# as the 'unique' identifier
                                //so all other details of user comes under IDNO#
                                databaseReference.child("users").child(idnotext).child("IDNumber").setValue(idnotext);
                                databaseReference.child("users").child(idnotext).child("Organization").setValue(orgtext);
                                databaseReference.child("users").child(idnotext).child("LastName").setValue(lnametext);
                                databaseReference.child("users").child(idnotext).child("FirstName").setValue(fnametext);
                                databaseReference.child("users").child(idnotext).child("MiddleName").setValue(mnametext);
                                databaseReference.child("users").child(idnotext).child("Email").setValue(emailtext);
                                databaseReference.child("users").child(idnotext).child("ContactNumber").setValue(contactnumbertext);
                                databaseReference.child("users").child(idnotext).child("FullName").setValue(lnametext + ", " + fnametext + " " + mnametext );
                                databaseReference.child("users").child(idnotext).child("Pass").setValue(lnametext);

                                //adding to our list of Users after adding them to database


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



    }

    //upload image into storage functions starts here
    private void uploadImage() {

        if (imageUri != null) {
            StorageReference reference = storage.getReference().child("images/" + UUID.randomUUID().toString());


            reference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Register.this,"Image Uploaded", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(Register.this,task.getException().getMessage(), Toast.LENGTH_LONG).show();

                    }
                }
            });
        }


    }
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    ImageView userpicr = findViewById(R.id.UserPicr);
                    if (result != null) {

                        userpicr.setImageURI(result);
                        imageUri = result;
                    }
                }
            });

    //upload image into storage functions ends here
}