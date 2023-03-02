package com.example.votingapp.AdminSideofThings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.votingapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ACEditCandidate extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private Button savebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_candidate);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        final EditText first = findViewById(R.id.FirstNumber);
        final EditText second = findViewById(R.id.SecondNumber);
        final EditText third = findViewById(R.id.ThirdNumber);
        final EditText fourth = findViewById(R.id.FourthNumber);

        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        // Get selected data from Intent
        Intent intent = getIntent();

        int ACAId = Integer.parseInt(intent.getStringExtra("aca_id"));
        String ACAName = intent.getStringExtra("aca_name");
        String ACAPosition = intent.getStringExtra("aca_position");

        // Set text for acText
        first.setText(String.valueOf(ACAId));
        second.setText(ACAName);

        // Get a reference to the Firebase database node for the candidate
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/").child("1RO5aLG_FLEoVdnxJqF50fKIlqeKlGBG01-bhDhGPFZo").child("ACcandidates").child(String.valueOf(ACAId));

        // Set up the save button click listener to update the candidate in the database
        savebutton = findViewById(R.id.SaveEdit);
        savebutton.setOnClickListener(view -> {
            String ID = first.getText().toString();
            int newID = Integer.parseInt(ID);

            String newName = second.getText().toString();
            databaseReference.child("name").setValue(newName);
            databaseReference.child("membership").setValue(newID);
            // Finish the activity to return to the previous screen
            finish();
        });

    }



    public void addToGoogleSheets(){
        final EditText ACIDno = findViewById(R.id.FirstNumber);

        final EditText ACName = findViewById(R.id.SecondNumber);


        final String acidnotext = ACIDno.getText().toString();

        final String acnametext = ACName.getText().toString();


        final ProgressDialog progressDialog = new ProgressDialog(ACEditCandidate.this);
        progressDialog.setMessage("Loading...");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbyThxNe351NL6dvdyjiZfLk-bB6SRbbCu-RhVD8kxmwFscxZY11Yk2-et3mSW44RAVO/exec", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(getApplicationContext(), ACAcandidates.class);
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
                params.put("vIDNumber", acidnotext);

                params.put("vACName", acnametext);


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
