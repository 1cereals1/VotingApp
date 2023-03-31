package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.votingapp.UserSideofThings.ACvotepage;
import com.example.votingapp.UserSideofThings.BODvotepage;

public class VotingGuidelines extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting_guidelines);


        CheckBox myCheckbox = findViewById(R.id.my_checkbox);
        Button myButton = findViewById(R.id.SVbtn);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VotingGuidelines.this, BODvotepage.class));
                finish();
            }
        });

        myCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                myButton.setEnabled(isChecked);
            }
        });


    }
}