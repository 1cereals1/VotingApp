package com.example.votingapp.AdminSideofThings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.votingapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {



    //must create object of DatabaseReference class to access firebase's realtime database
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/").child("1RO5aLG_FLEoVdnxJqF50fKIlqeKlGBG01-bhDhGPFZo");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //


        final EditText idno = findViewById(R.id.ID);
        final EditText org = findViewById(R.id.Organization);
        final EditText lname = findViewById(R.id.LastName);
        final EditText fname = findViewById(R.id.FirstName);
        final EditText mname = findViewById(R.id.MiddleName);
        final EditText email = findViewById(R.id.Email);
        final EditText contactnumber = findViewById(R.id.ContactNumber);

        final Button submitr = findViewById(R.id.SubmitR);

        final ProgressDialog progressDialog = new ProgressDialog(Register.this);
        progressDialog.setMessage("Loading...");



        final TextView idtext = findViewById(R.id.IDT);




        submitr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


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

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            //check if user is not registered before
                            if (snapshot.hasChild(idnotext)){
                                Toast.makeText(Register.this, "This I.D. Number has been Registered before already", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                //progressdialog
                                progressDialog.show();
                                addToGoogleSheets();

                                //sending data back to firebase Realtime Database
                                //in this case, I'm using the IDNO# as the 'unique' identifier
                                //so all other details of user comes under IDNO#
                                //databaseReference.child(idnotext).child("IDNumber").setValue(idnotext);
                                //databaseReference.child("users").child(idnotext).child("Organization").setValue(orgtext);
                                //databaseReference.child("users").child(idnotext).child("LastName").setValue(lnametext);
                                //databaseReference.child("users").child(idnotext).child("FirstName").setValue(fnametext);
                                //databaseReference.child("users").child(idnotext).child("MiddleName").setValue(mnametext);
                                //databaseReference.child("users").child(idnotext).child("Email").setValue(emailtext);
                                //databaseReference.child(idnotext).child("ContactNumber").setValue(contactnumbertext);
                                //databaseReference.child("users").child(idnotext).child("FullName").setValue(lnametext + ", " + fnametext + " " + mnametext );


                                //adding to our list of Users after adding them to database


                                //showing success message then finishing activity
                                Toast.makeText(Register.this, "User Registered", Toast.LENGTH_SHORT).show();
                                //finish();
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

    public void addToGoogleSheets(){
        final EditText idno = findViewById(R.id.ID);
        final EditText org = findViewById(R.id.Organization);
        final EditText lname = findViewById(R.id.LastName);
        final EditText fname = findViewById(R.id.FirstName);
        final EditText mname = findViewById(R.id.MiddleName);
        final EditText email = findViewById(R.id.Email);
        final EditText contactnumber = findViewById(R.id.ContactNumber);

        final String idnotext = idno.getText().toString();
        final String orgtext = org.getText().toString();
        final String lnametext = lname.getText().toString();
        final String fnametext = fname.getText().toString();
        final String mnametext = mname.getText().toString();
        final String emailtext = email.getText().toString();
        final String contactnumbertext = "\""+contactnumber.getText().toString()+"\"";
        final String fullnametext = fnametext + " " + mnametext + " " + lnametext;

        final ProgressDialog progressDialog = new ProgressDialog(Register.this);
        progressDialog.setMessage("Loading...");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbyThxNe351NL6dvdyjiZfLk-bB6SRbbCu-RhVD8kxmwFscxZY11Yk2-et3mSW44RAVO/exec", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(getApplicationContext(), AdminsEmployees.class);
                startActivity(intent);
                finish();
                progressDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("action", "addToGoogleSheets");
                params.put("vIDNumber", idnotext);
                params.put("vOrganization", orgtext);
                params.put("vLastName", lnametext);
                params.put("vFirstName", fnametext);
                params.put("vMiddleName", mnametext);
                params.put("vEmail", emailtext);
                params.put("vContactNumber", contactnumbertext);
                params.put("vFullname", fullnametext);

                return params;
            }
        };

        int socketTimeOut = 50000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }


}