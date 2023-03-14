package com.example.votingapp.AdminSideofThings;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.votingapp.Login;
import com.example.votingapp.R;
import com.example.votingapp.UserSideofThings.ACvotepage;
import com.example.votingapp.UserSideofThings.BODvotepage;
import com.example.votingapp.UserSideofThings.UserHome;

public class ControlPage extends AppCompatActivity {
    private Switch toggleSwitch,toggleSwitch2;
    private Button transferButton,logout,Homeback;
    private boolean isButtonDisabled = false;
    private boolean isButtonDisabled2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_page);

        toggleSwitch = findViewById(R.id.switchButton);
        toggleSwitch2 = findViewById(R.id.switchButton2);
        transferButton = findViewById(R.id.Buttonnext);
        Homeback = findViewById(R.id.homeback);
        logout = findViewById(R.id.logout);

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

        transferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ControlPage.this, Login.class));
                finish();
            }
        });
        Homeback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ControlPage.this, AdminDashboard.class));
                finish();
            }
        });

    }

    public void openActivity2() {
        Intent intent = new Intent(this, UserHome.class);
        intent.putExtra("isButtonDisabled", isButtonDisabled);
        intent.putExtra("isButtonDisabled2", isButtonDisabled2);
        startActivity(intent);
    }
}