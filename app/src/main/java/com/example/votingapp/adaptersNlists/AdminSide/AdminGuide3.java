package com.example.votingapp.adaptersNlists.AdminSide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.votingapp.R;

public class AdminGuide3 extends AppCompatActivity {
    private ImageButton Next_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_guide3);

        Next_3 = findViewById(R.id.next3);

        Next_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminGuide3.this, AdminGuide4.class));

            }
        });
    }
}