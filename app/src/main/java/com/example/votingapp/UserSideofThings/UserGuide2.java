package com.example.votingapp.UserSideofThings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.votingapp.R;

public class    UserGuide2 extends AppCompatActivity {
    private ImageButton NEXTtoUSER3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide2);

        NEXTtoUSER3 = findViewById(R.id.next2);

        NEXTtoUSER3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserGuide2.this, UserGuide3.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);



            }
        });




    }
}