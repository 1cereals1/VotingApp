package com.example.votingapp.adaptersNlists;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;

import com.example.votingapp.R;

public class ACbuttonhandler extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_adapter_layout_acvotepage);

        Button butt = findViewById(R.id.btn_edit);

    }
}