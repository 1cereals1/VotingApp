package com.example.votingapp.adaptersNlists.AdminSide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.votingapp.AdminSideofThings.AdminDashboard;
import com.example.votingapp.BODResultsChart;
import com.example.votingapp.R;

public class AdminGuide1 extends AppCompatActivity {
    private ImageButton Next_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_guide1);


        Next_1 = findViewById(R.id.next1);

        Next_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminGuide1.this, AdminGuide2.class));

            }
        });

    }
}