package com.example.votingapp.AdminSideofThings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.votingapp.R;

public class AdminDashboard extends AppCompatActivity {
    private Button gotomail,gotoresults;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        gotomail = findViewById(R.id.GOTOMAIL);
        gotoresults = findViewById(R.id.GOTORESULTS);

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



    }
}