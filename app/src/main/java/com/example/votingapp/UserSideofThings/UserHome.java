package com.example.votingapp.UserSideofThings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.votingapp.CandidacyForm;
import com.example.votingapp.R;

public class UserHome extends AppCompatActivity {
    private Button Bvote, backButton, disableButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        Bvote = findViewById(R.id.button_vote);
        backButton = findViewById(R.id.BackButton);
        disableButton = findViewById(R.id.DisableButton);

        Bvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserHome.this,VotePage.class));
                finish();
            }
        });
        disableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserHome.this,CandidacyForm.class));
                finish();
            }
        });

        boolean isButtonDisabled = getIntent().getBooleanExtra("isButtonDisabled", false); // retrieve initial button state from MainActivity

        disableButton.setEnabled(!isButtonDisabled);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void toggleButtonState(boolean isEnabled) {
        disableButton.setEnabled(!isEnabled);
    }

    public void onClick(View view) {
        TextView textView = (TextView) findViewById(R.id.TextView);
        textView.setText("ButtonClicked");


    }
}