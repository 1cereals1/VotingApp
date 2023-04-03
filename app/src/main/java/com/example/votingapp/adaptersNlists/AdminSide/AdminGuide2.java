package com.example.votingapp.adaptersNlists.AdminSide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.votingapp.R;

public class AdminGuide2 extends AppCompatActivity {
    private ImageButton Next_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_guide2);

        Next_2 = findViewById(R.id.next2);
        Next_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminGuide2.this, AdminGuide3.class));

            }
        });
    }
}