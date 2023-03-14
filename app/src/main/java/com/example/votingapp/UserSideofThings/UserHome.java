package com.example.votingapp.UserSideofThings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.votingapp.CandidacyForm;
import com.example.votingapp.R;
import com.example.votingapp.VotingGuidelines;

public class UserHome extends AppCompatActivity {
    private Button Bvote, disableButton,disableButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        Bvote = findViewById(R.id.DisableButton2);
        disableButton = findViewById(R.id.DisableButton);
        disableButton2 = findViewById(R.id.DisableButton2);

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
        disableButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserHome.this, VotingGuidelines.class));
                finish();
            }
        });

        boolean isButtonDisabled = getIntent().getBooleanExtra("isButtonDisabled", false); // retrieve initial button state from MainActivity

        disableButton.setEnabled(!isButtonDisabled);

        boolean isButtonDisabled2 = getIntent().getBooleanExtra("isButtonDisabled2", false); // retrieve initial button state from MainActivity
        disableButton2.setEnabled(!isButtonDisabled2);

    }

    public void toggleButtonState(boolean isEnabled) {
        disableButton.setEnabled(!isEnabled);
        disableButton2.setEnabled(!isEnabled);
    }
}