package com.example.votingapp.adaptersNlists.AdminSide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.votingapp.R;

public class AdminGuide4 extends AppCompatActivity {
    private ImageButton Next_4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_guide4);

        Next_4 = findViewById(R.id.next4);

        Next_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminGuide4.this, AdminGuide5.class));

            }
        });
    }
}