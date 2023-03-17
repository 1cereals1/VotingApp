package com.example.votingapp.AdminSideofThings;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.votingapp.R;
import com.example.votingapp.UserSideofThings.UserHome;
import com.example.votingapp.VotingGuidelines;

public class ControlPage extends AppCompatActivity {
    private Switch toggleSwitch,toggleSwitch2,toggleSwitch3;
    private Button transferButton,home;
    private boolean isButtonDisabled = false;
    private boolean isButtonDisabled2 = false;

    private boolean isButtonDisabled3 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_page);

        toggleSwitch = findViewById(R.id.switchButton);
        toggleSwitch2 = findViewById(R.id.switchButton2);
        toggleSwitch3 = findViewById(R.id.switchButton3);
        transferButton = findViewById(R.id.Buttonnext);
        home = findViewById(R.id.button_home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ControlPage.this, AdminDashboard.class));
                finish();
            }
        });

        toggleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isButtonDisabled = isChecked;
            }
        });

        toggleSwitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isButtonDisabled2 = isChecked;
            }
        });
        toggleSwitch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isButtonDisabled3 = isChecked;
            }
        });

        transferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });

    }

    public void openActivity2() {
        Intent intent = new Intent(this, UserHome.class);
        intent.putExtra("isButtonDisabled", isButtonDisabled);
        intent.putExtra("isButtonDisabled2", isButtonDisabled2);
        intent.putExtra("isButtonDisabled3", isButtonDisabled3);
        startActivity(intent);
    }
}