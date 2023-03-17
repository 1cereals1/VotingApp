package com.example.votingapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.votingapp.AdminSideofThings.AdminDashboard;
import com.example.votingapp.adaptersNlists.UserSide.ACList;
import com.example.votingapp.adaptersNlists.UserSide.ECList;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ECResultsChart extends AppCompatActivity {


    ECList ecList = new ECList();
    // Set the percentage value for acList from Firebase Realtime Database
    //float percentage = acList.getPercentage(); // Get the percentage value
    private TextView ECWinner1;
    private TextView ECWinner2;
    private TextView ECWinner1votes;
    private TextView ECWinner2votes;
    private ImageButton ectoac;
    private ImageButton ectohome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecresults_chart);
        HorizontalBarChart barChart = findViewById(R.id.ecbar_chart);
        setupHorizontalBarChart(barChart);

        ECWinner1  = findViewById(R.id.ECWinner1);
        ECWinner2  = findViewById(R.id.ECWinner2);
        ECWinner1votes = findViewById(R.id.ECWinner1votes);
        ECWinner2votes = findViewById(R.id.ECWinner2votes);
        ectoac = findViewById(R.id.ectoac);
        ectohome = findViewById(R.id.ectohome);

        ectoac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ECResultsChart.this, ACResultsChart.class));
                finish();
            }
        });

        ectohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }

    private void setupHorizontalBarChart(HorizontalBarChart barChart) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        List<String> candidateNames = new ArrayList<>();
        DatabaseReference candidatesRef = FirebaseDatabase.getInstance().getReference().child("Candidates");
        candidatesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i = 0;

                ArrayList<BarEntry> entries = new ArrayList<>();

                for (DataSnapshot candidateSnapshot : snapshot.getChildren()) {
                    String elective = candidateSnapshot.child("elective").getValue(String.class);
                    if (elective != null && elective.equals("ELECTION COMITTEE")) {
                        String name = candidateSnapshot.child("name").getValue(String.class);
                        candidateNames.add(name); // Add the name to the list
                        int votes = candidateSnapshot.child("votes").getValue(Integer.class);
                        entries.add(new BarEntry(i, votes));
                        i++;
                    }
                }

                // Sort the entries ArrayList in descending order based on y-values
                Collections.sort(entries, new Comparator<BarEntry>() {
                    @Override
                    public int compare(BarEntry o1, BarEntry o2) {
                        return Float.compare(o2.getY(), o1.getY());
                    }
                });

                // Set the text of ACWinner1 and ACWinner2
                if (entries.size() > 0) {
                    String ecWinner1Name = candidateNames.get((int) entries.get(0).getX());
                    ECWinner1.setText(ecWinner1Name);

                    // Get the number of votes of the winner
                    int ecWinner1Votes = (int) entries.get(0).getY();
                    ECWinner1votes.setText("votes: "+(String.valueOf(ecWinner1Votes)));
                }

                if (entries.size() > 1) {
                    String ecWinner2Name = candidateNames.get((int) entries.get(1).getX());
                    ECWinner2.setText(ecWinner2Name);

                    // Get the number of votes of the runner-up
                    int ecWinner2Votes = (int) entries.get(1).getY();
                    ECWinner2votes.setText("votes: "+(String.valueOf(ecWinner2Votes)));
                }


                BarDataSet dataSet = new BarDataSet(entries, "Candidate Votes");
                dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                dataSet.setValueTextColor(Color.BLACK);
                dataSet.setValueTextSize(16f);

                BarData data = new BarData(dataSet);
                barChart.setData(data);

                barChart.getDescription().setEnabled(false);
                barChart.getXAxis().setDrawGridLines(false);
                barChart.getAxisLeft().setDrawGridLines(false);
                barChart.getAxisRight().setDrawGridLines(false);
                barChart.getLegend().setEnabled(false);
                barChart.setTouchEnabled(false);
                barChart.setDragEnabled(false);
                barChart.setScaleEnabled(false);
                barChart.animateY(1000);

                barChart.invalidate();

                // Set up the x-axis
                XAxis xAxis = barChart.getXAxis();
                xAxis.setDrawGridLines(false);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setTextSize(16f);
                xAxis.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        if (value >= 0 && value < candidateNames.size() && value % 1 == 0) {
                            return candidateNames.get((int) value);
                        } else {
                            return "";
                        }
                    }
                });



                // Set up the y-axis
                YAxis yAxis = barChart.getAxisLeft();
                yAxis.setDrawGridLines(false);
                yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
                yAxis.setTextSize(16f);
                yAxis.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        if (value >= 0 && value < entries.size()) {
                            BarEntry entry = entries.get((int) value);
                            if (entry.getData() != null) {
                                return entry.getData().toString();
                            } else {
                                return "";
                            }
                        } else {
                            return "";
                        }
                    }
                });

            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Failed to retrieve data from Firebase");
            }
        });
    }

}