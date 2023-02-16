package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.votingapp.databinding.ActivityAdminHomepageBinding;

public class AdminHomepage extends DrawerBaseActivity {

    ActivityAdminHomepageBinding activityAdminHomepageBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_homepage);

        //for nav
        activityAdminHomepageBinding = ActivityAdminHomepageBinding.inflate(getLayoutInflater());
        setContentView(activityAdminHomepageBinding.getRoot());
        allocatedActivityTitle("Homepage");
        //end of for nav

    }
}