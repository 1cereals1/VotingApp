package com.example.votingapp.AdminSideofThings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.votingapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminConfirmReset extends AppCompatActivity {


    private ImageButton resetall, backtocont;


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

        getWindow().setLayout((int)(width*.90),(int)(height*.40));



        resetall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference CandidatesRef = FirebaseDatabase.getInstance().getReference("Candidates");
                DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference("Users");

                CandidatesRef.removeValue();
                UsersRef.removeValue();
                finish();
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