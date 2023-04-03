package com.example.votingapp.UserSideofThings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.votingapp.R;

public class UserGuide4 extends AppCompatActivity {
    private ImageButton LGtoVNG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide4);

        LGtoVNG = findViewById(R.id.lgtovng);

        LGtoVNG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserGuide4.this, UserGuide5.class));

            }
        });
    }
}