package com.example.votingapp.AdminSideofThings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.example.votingapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminConfirmReset extends AppCompatActivity {

    Button resetall;
    Button backtocont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_confirm_reset);
        resetall = findViewById(R.id.ConfirmReset);
        backtocont = findViewById(R.id.BacktoControlPage);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.4));



        resetall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference CandidatesRef = FirebaseDatabase.getInstance().getReference("Candidates");
                DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference("Users");

                CandidatesRef.removeValue();
                UsersRef.removeValue();
            }
        });
        backtocont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}