package com.example.votingapp.UserSideofThings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.votingapp.R;

public class UserGuide5 extends AppCompatActivity {
    private ImageButton VNGtoSRG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide5);

        VNGtoSRG = findViewById(R.id.vngtosrg);

        VNGtoSRG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserGuide5.this, UserGuide6.class));

            }
        });
    }
}