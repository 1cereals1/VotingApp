package com.example.votingapp.AdminSideofThings;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.nfc.cardemulation.CardEmulation;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import com.example.votingapp.Login;
import com.example.votingapp.R;
import com.example.votingapp.UserSideofThings.ACvotepage;
import com.example.votingapp.UserSideofThings.BODvotepage;
import com.example.votingapp.UserSideofThings.UserHome;
import com.example.votingapp.VotingGuidelines;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ControlPage extends AppCompatActivity {
    private Switch toggleSwitch,toggleSwitch2,toggleSwitch3;
    private ImageButton transferButton,home, Reset,logoutC;
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
        logoutC = findViewById(R.id.logs);
        Reset = findViewById(R.id.Reset);

        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ControlPage.this, AdminConfirmReset.class));


            }
        });


        DatabaseReference myBoolean = FirebaseDatabase.getInstance().getReference("Value");
        DatabaseReference myBoolean2 = FirebaseDatabase.getInstance().getReference("Value2");
        DatabaseReference myBoolean3 = FirebaseDatabase.getInstance().getReference("Value3");


        toggleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isButtonDisabled = isChecked;
                myBoolean.setValue(isChecked);
            }
        });

        myBoolean.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                Boolean isSwitchOn = (Boolean) datasnapshot.getValue();
                if(isSwitchOn != null) {
                    toggleSwitch.setChecked(isSwitchOn);
                }
            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG,"loadPost:OnCancelled", databaseError.toException());
            }
        });

        toggleSwitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isButtonDisabled2 = isChecked;
                myBoolean2.setValue(isChecked);
            }
        });

        myBoolean2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                Boolean isSwitchOn2 = (Boolean) datasnapshot.getValue();
                if(isSwitchOn2 != null) {
                    toggleSwitch2.setChecked(isSwitchOn2);
                }
            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG,"loadPost:OnCancelled", databaseError.toException());
            }
        });

        toggleSwitch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isButtonDisabled3 = isChecked;
                myBoolean3.setValue(isChecked);
            }
        });

        myBoolean3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                Boolean isSwitchOn3 = (Boolean) datasnapshot.getValue();
                if(isSwitchOn3 != null) {
                    toggleSwitch3.setChecked(isSwitchOn3);
                }
            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG,"loadPost:OnCancelled", databaseError.toException());
            }
        });


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        startActivity(new Intent(ControlPage.this, AdminDashboard.class));

        finish();
        }
        });

        logoutC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ControlPage.this, AdminLogin.class));

                finish();
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
        intent.putExtra("toggleSwitchState", toggleSwitch.isChecked());
        startActivity(intent);
    }
}