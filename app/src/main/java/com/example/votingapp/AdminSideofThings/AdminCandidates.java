package com.example.votingapp.AdminSideofThings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.votingapp.UserSideofThings.ACvotepage;
import com.example.votingapp.UserSideofThings.BODvotepage;
import com.example.votingapp.UserSideofThings.ECvotepage;
import com.example.votingapp.R;

public class AdminCandidates extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_candidates);
        final Button toBODA = findViewById(R.id.GOTOBODA);
        final Button toACA = findViewById(R.id.GOTOACA);
        final Button toECA = findViewById(R.id.GOTOECA);

        toBODA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminCandidates.this, BODAcandidates.class));
                finish();
            }
        });

        toACA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminCandidates.this, ACAcandidates.class));
                finish();
            }
        });

        toECA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminCandidates.this, ECAcandidates.class));
                finish();
            }
        });
    }
}