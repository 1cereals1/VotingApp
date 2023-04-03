package com.example.votingapp.UserSideofThings;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;
import com.example.votingapp.R;
public class BODReview extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bodreview);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        final TextView first = findViewById(R.id.FirstNumber);
        final TextView second = findViewById(R.id.SecondNumber);
        final TextView third = findViewById(R.id.ThirdNumber);
        final TextView fourth = findViewById(R.id.FourthNumber);

        getWindow().setLayout((int)(width*.8),(int)(height*.4));




        // Get selected data from Intent
        Intent intent = getIntent();

        //
        String bodname = intent.getStringExtra("bod_name");
        String bodposition = intent.getStringExtra("bod_position");
        String bodid = intent.getStringExtra("bod_id");








        // Set text for acTextViews
        first.setText("Name: "+bodname);
        second.setText("ID: "+bodid);




    }
    @Override
    public void onBackPressed() {
        finish();

    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!hasFocus) {
            finish();
        }
    }
}