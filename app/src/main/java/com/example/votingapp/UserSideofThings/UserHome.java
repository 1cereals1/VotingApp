package com.example.votingapp.UserSideofThings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.votingapp.BODResultsChart;
import com.example.votingapp.CandidacyForm;
import com.example.votingapp.Login;
import com.example.votingapp.R;
import com.example.votingapp.VotingGuidelines;

public class UserHome extends AppCompatActivity {
    private Button BResult, backButton, disableButton,gotovotepage;
    private ImageButton Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        BResult = findViewById(R.id.to_results);
        disableButton = findViewById(R.id.DisableButton);
        gotovotepage  = findViewById(R.id.DisableButton2);
        Logout = findViewById(R.id.logout);

        gotovotepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserHome.this, VotingGuidelines.class));
                finish();
            }
        });
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserHome.this, Login.class));
                finish();
            }
        });

        BResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserHome.this, BODResultsChart.class));

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

        boolean isButtonDisabled2 = getIntent().getBooleanExtra("isButtonDisabled2", false); // retrieve initial button state from MainActivity

        gotovotepage.setEnabled(!isButtonDisabled2);

        boolean isButtonDisabled3 = getIntent().getBooleanExtra("isButtonDisabled3", false); // retrieve initial button state from MainActivity

        BResult.setEnabled(!isButtonDisabled3);


    }

    public void toggleButtonState(boolean isEnabled) {
        disableButton.setEnabled(!isEnabled);
    }
}