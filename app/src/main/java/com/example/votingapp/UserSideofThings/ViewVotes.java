package com.example.votingapp.UserSideofThings;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.votingapp.R;
import com.example.votingapp.VotingGuidelines;

public class ViewVotes extends AppCompatActivity {

    private Dialog dialogcon;

    private Button Submit;
    private ImageButton ReviewtoEc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_votes);

        Submit = findViewById(R.id.submit);
        ReviewtoEc = findViewById(R.id.reviewtoec);
        dialogcon = new Dialog(this);

        ReviewtoEc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewVotes.this, ECvotepage.class));
                finish();
            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

    }

    private void openDialog() {
        dialogcon.setContentView(R.layout.congratsvotecast);
        dialogcon.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewClose=dialogcon.findViewById(R.id.imageViewclose);
        ImageButton close=dialogcon.findViewById(R.id.close);

        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogcon.dismiss();
                Toast.makeText(ViewVotes.this, "Dialog Close", Toast.LENGTH_SHORT).show();
            }
        });
        dialogcon.show();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogcon.dismiss();
                Toast.makeText(ViewVotes.this, "Close", Toast.LENGTH_SHORT).show();
            }
        });
        dialogcon.show();


    }
}