package com.example.votingapp.UserSideofThings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.votingapp.CandidacyForm;
import com.example.votingapp.R;

public class CandidacyFormConfirm extends AppCompatActivity {

    ImageButton close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmcandidacy);

        close = findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CandidacyFormConfirm.this, UserHome.class));
                finish();
            }
        });
    }
}