package com.example.votingapp.UserSideofThings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.votingapp.R;

public class UserGuide6 extends AppCompatActivity {
    private ImageButton SRGtoCFG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide6);

        setContentView(R.layout.activity_user_guide4);

        SRGtoCFG = findViewById(R.id.srgtocfg);

        SRGtoCFG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserGuide6.this, UserGuide7.class));

            }
        });
    }
}