package com.example.votingapp.AdminSideofThings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.votingapp.R;

public class AdminDashboard extends AppCompatActivity {
    private CardView gotomail, gotoresults,gotocandidates,gotocontrol,gotomembers;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);



    }
}