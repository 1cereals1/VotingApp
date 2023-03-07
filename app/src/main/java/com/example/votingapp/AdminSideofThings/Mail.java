package com.example.votingapp.AdminSideofThings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.votingapp.R;
import com.example.votingapp.UserSideofThings.UserHome;
import com.example.votingapp.UserSideofThings.VotePage;

public class Mail extends AppCompatActivity implements View.OnClickListener {

    public CardView card1,card2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);

        card1 = (CardView) findViewById(R.id.card_view1);
        card2 = (CardView) findViewById(R.id.card_view2);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Mail.this, ReviewCandidates.class));
                finish();
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Mail.this, ReviewContactus.class));
                finish();
            }
        });


    }

    @Override
    public void onClick(View v) {

    }
}