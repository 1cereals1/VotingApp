package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.votingapp.databinding.ActivityAdminsEmployeesBinding;
import com.example.votingapp.databinding.ActivityOrderBinding;

public class OrderActivity extends DrawerBaseActivity {

    ActivityOrderBinding activityOrderBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        //for nav
        activityOrderBinding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(activityOrderBinding.getRoot());
        allocatedActivityTitle("Order?");
        //end of for nav
    }
}