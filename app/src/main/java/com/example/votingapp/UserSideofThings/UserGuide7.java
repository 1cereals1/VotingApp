package com.example.votingapp.UserSideofThings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.votingapp.R;

public class UserGuide7 extends AppCompatActivity {
    private ImageButton CFGtoUserhome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide7);

        CFGtoUserhome = findViewById(R.id.cfgtoUserhome);

        CFGtoUserhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserGuide7.this, UserHome.class));

            }
        });
    }
}