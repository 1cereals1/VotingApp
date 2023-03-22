package com.example.votingapp.UserSideofThings;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.votingapp.BODResultsChart;
import com.example.votingapp.CandidacyForm;
import com.example.votingapp.Login;
import com.example.votingapp.R;
import com.example.votingapp.VotingGuidelines;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserHome extends AppCompatActivity {
    private Button gotoresultpage, backButton, gotocandidacypage,gotovotepage;
    private ImageButton Logout;
    private DatabaseReference myBoolean,myBoolean2,myBoolean3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        gotoresultpage = findViewById(R.id.to_results);
        gotocandidacypage = findViewById(R.id.DisableButton2);
        gotovotepage  = findViewById(R.id.DisableButton);
        Logout = findViewById(R.id.logout);

        myBoolean = FirebaseDatabase.getInstance().getReference("Value");
        myBoolean2 = FirebaseDatabase.getInstance().getReference("Value2");
        myBoolean3 = FirebaseDatabase.getInstance().getReference("Value3");
        myBoolean.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean isSwitchOn = dataSnapshot.getValue(Boolean.class);
                if (isSwitchOn != null && isSwitchOn) {
                    gotovotepage.setEnabled(false);
                } else {
                    gotovotepage.setEnabled(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });

        myBoolean2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean isSwitchOn2 = dataSnapshot.getValue(Boolean.class);
                if (isSwitchOn2 != null && isSwitchOn2) {
                    gotocandidacypage.setEnabled(false);
                } else {
                    gotocandidacypage.setEnabled(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });

        myBoolean3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean isSwitchOn3 = dataSnapshot.getValue(Boolean.class);
                if (isSwitchOn3 != null && isSwitchOn3) {
                    gotoresultpage.setEnabled(false);
                } else {
                    gotoresultpage.setEnabled(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });


        gotoresultpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHome.this, BODResultsChart.class));
            }
        });



        gotovotepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHome.this, BODvotepage.class));
            }
        });

        gotocandidacypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHome.this, CandidacyForm.class));
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserHome.this, Login.class));
                finish();
            }
        });
    }
}
