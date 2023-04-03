package com.example.votingapp.UserSideofThings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.example.votingapp.AdminSideofThings.ACAcandidates;
import com.example.votingapp.R;
import com.example.votingapp.adaptersNlists.AdminSide.ACAAdapter;
import com.example.votingapp.adaptersNlists.AdminSide.ACAList;
import com.example.votingapp.adaptersNlists.UserSide.ACList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ECReview extends AppCompatActivity {


    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecreview);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        final TextView first = findViewById(R.id.FirstNumber);
        final TextView second = findViewById(R.id.SecondNumber);
        final TextView third = findViewById(R.id.ThirdNumber);
        final TextView fourth = findViewById(R.id.FourthNumber);

        getWindow().setLayout((int)(width*.8),(int)(height*.4));




        // Get selected data from Intent
        Intent intent = getIntent();

        //
        String ecname = intent.getStringExtra("ec_name");
        String ecvision1 = intent.getStringExtra("ec_vision");
        String ecid = intent.getStringExtra("ec_id");



        // Set text for acTextViews
        first.setText("Name: "+ecname);
        second.setText("ID: "+ecid);





    }
    @Override
    public void onBackPressed() {
        finish();

    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}