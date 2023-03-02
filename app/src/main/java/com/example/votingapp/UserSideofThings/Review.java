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

public class Review extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        final TextView first = findViewById(R.id.FirstNumber);
        final TextView second = findViewById(R.id.SecondNumber);
        final TextView third = findViewById(R.id.ThirdNumber);
        final TextView fourth = findViewById(R.id.FourthNumber);

        getWindow().setLayout((int)(width*.8),(int)(height*.6));




        // Get selected data from Intent
        Intent intent = getIntent();

        //
        String acname = intent.getStringExtra("ac_name");
        String acposition = intent.getStringExtra("ac_position");
        String acid = intent.getStringExtra("ac_id");


        String bodname = intent.getStringExtra("bod_name");
        String bodposition = intent.getStringExtra("bod_position");
        String bodid = intent.getStringExtra("bod_id");


        String ecname = intent.getStringExtra("ec_name");
        String ecposition = intent.getStringExtra("ec_position");
        String ecid = intent.getStringExtra("ec_id");





        // Set text for acTextViews
        first.setText(acname);
        second.setText(acid);

        // Set text for bodTextViews
        first.setText(bodname);
        second.setText(bodid);

        // Set text for ecTextViews
        first.setText(ecname);
        second.setText(ecid);


    }
}