package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.votingapp.UserSideofThings.ACvotepage;
import com.example.votingapp.UserSideofThings.BODvotepage;

public class VotingGuidelines extends AppCompatActivity {
    private Button StartVote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting_guidelines);

        StartVote = findViewById(R.id.SVbtn);

        StartVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VotingGuidelines.this, BODvotepage.class));
                finish();
            }
        });
    }
}