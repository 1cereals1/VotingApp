package com.example.votingapp.adaptersNlists.AdminSide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.votingapp.AdminSideofThings.AdminDashboard;
import com.example.votingapp.R;

public class AdminGuide5 extends AppCompatActivity {

    private ImageButton Next_5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_guide5);

        Next_5 = findViewById(R.id.next5);

        Next_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminGuide5.this, AdminDashboard.class));

            }
        });
    }
}