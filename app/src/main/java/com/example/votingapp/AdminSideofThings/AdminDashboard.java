package com.example.votingapp.AdminSideofThings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.votingapp.BODResultsChart;
import com.example.votingapp.R;
import com.example.votingapp.UserSideofThings.UserHome;

public class AdminDashboard extends AppCompatActivity {
    private CardView gotomail, gotoresults,gotocandidates,gotocontrol,gotomembers;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        gotoresults = findViewById(R.id.GOTORESULTS);
        gotocandidates = findViewById(R.id.GOTOCANDIDATES);
        gotomembers = findViewById(R.id.GOTOMEMBERS);
        gotocontrol = findViewById(R.id.GOTOCONTROL);

        gotoresults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboard.this, BODResultsChart.class));

            }
        });
        gotocandidates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboard.this, BODAcandidates.class));

            }
        });
        gotomembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboard.this, AdminsEmployees.class));

            }
        });
        gotocontrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboard.this, ControlPage.class));

            }
        });


    }
}