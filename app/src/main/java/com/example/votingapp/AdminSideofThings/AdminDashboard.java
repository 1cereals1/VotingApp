package com.example.votingapp.AdminSideofThings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.votingapp.R;

public class AdminDashboard extends AppCompatActivity {
    private ImageView gotomail,gotoresults,gotocandidates,gotocontrol,gotomembers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        gotomail = findViewById(R.id.GOTOMAIL);
        gotoresults = findViewById(R.id.GOTORESULTS);
        gotocandidates = findViewById(R.id.GOTOCANDIDATES);
        gotocontrol = findViewById(R.id.GOTOCONTROL);
        gotomembers = findViewById(R.id.GOTOMEMBERS);



        gotomail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboard.this, Mail.class));
                finish();
            }
        });
        gotoresults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboard.this, ChartResults.class));
                finish();
            }
        });

        gotocandidates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboard.this, ChartResults.class));
                finish();
            }
        });
        gotocontrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboard.this, ControlPage.class));
                finish();
            }
        });
        gotomembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboard.this, AdminsEmployees.class));
                finish();
            }
        });



    }
}