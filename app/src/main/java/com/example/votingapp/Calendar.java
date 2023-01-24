package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.votingapp.databinding.ActivityAdminsEmployeesBinding;
import com.example.votingapp.databinding.ActivityCalendarBinding;

public class Calendar extends DrawerBaseActivity {

    ActivityCalendarBinding activityCalendarBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        //for nav
        activityCalendarBinding = ActivityCalendarBinding.inflate(getLayoutInflater());
        setContentView(activityCalendarBinding.getRoot());
        allocatedActivityTitle("Employees");
        //end of for nav
    }
}