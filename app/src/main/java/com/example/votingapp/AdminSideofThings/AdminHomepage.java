package com.example.votingapp.AdminSideofThings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.votingapp.DrawerBaseActivity;
import com.example.votingapp.R;
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