package com.example.votingapp.UserSideofThings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.votingapp.R;


public class VotePage extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_page);

        final ImageButton toBOD = findViewById(R.id.GOTOBOD);
        final ImageButton toAC = findViewById(R.id.GOTOAC);
        final ImageButton toEC = findViewById(R.id.GOTOEC);

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