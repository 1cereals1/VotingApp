package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.votingapp.UserSideofThings.BODvotepage;
import com.example.votingapp.UserSideofThings.UserHome;
import com.example.votingapp.UserSideofThings.VotePage;

public class VotingGuidelines extends AppCompatActivity {
    private Button startvoting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting_guidelines);

        startvoting = findViewById(R.id.SVbtn);

        startvoting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VotingGuidelines.this, BODvotepage.class));
                finish();
            }
        });
    }
}