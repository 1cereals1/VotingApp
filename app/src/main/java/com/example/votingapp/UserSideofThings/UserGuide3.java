package com.example.votingapp.UserSideofThings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.votingapp.R;
import com.example.votingapp.adaptersNlists.AdminSide.AdminGuide1;
import com.example.votingapp.adaptersNlists.AdminSide.AdminGuide2;

public class UserGuide3 extends AppCompatActivity {
    private ImageButton FGtoLG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide3);

        FGtoLG = findViewById(R.id.fgtolg);

        FGtoLG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserGuide3.this, UserGuide4.class));

            }
        });
    }
}