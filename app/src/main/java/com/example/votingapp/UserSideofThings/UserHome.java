package com.example.votingapp.UserSideofThings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.votingapp.CandidacyForm;
import com.example.votingapp.R;

public class UserHome extends AppCompatActivity {
    private Button vote;
    private Button apply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        vote =findViewById(R.id.button3);
        apply = findViewById(R.id.button2);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserHome.this,CandidacyForm.class);
                startActivity(intent);
            }
        });

        vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserHome.this,VotePage.class);
                startActivity(intent);
            }
        });


    }
}