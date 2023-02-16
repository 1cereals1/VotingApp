package com.example.votingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;



public class VotePage extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_page);

        final Button toBOD = findViewById(R.id.GOTOBOD);
        final Button toAC = findViewById(R.id.GOTOAC);
        final Button toEC = findViewById(R.id.GOTOEC);

        toBOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VotePage.this, BODvotepage.class));
                finish();
            }
        });

        toAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VotePage.this, ACvotepage.class));
                finish();
            }
        });

        toEC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VotePage.this, ECvotepage.class));
                finish();
            }
        });
    }
}