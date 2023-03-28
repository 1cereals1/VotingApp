package com.example.votingapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.votingapp.AdminSideofThings.ACAcandidates;
import com.example.votingapp.AdminSideofThings.BODAcandidates;
import com.example.votingapp.adaptersNlists.UserSide.ACList;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
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


public class ACResultsChart extends AppCompatActivity {


    ACList acList = new ACList();
    // Set the percentage value for acList from Firebase Realtime Database
    //float percentage = acList.getPercentage(); // Get the percentage value
    private TextView ACWinner1;
    private TextView ACWinner2;
    private TextView ACWinner1votes;
    private TextView ACWinner2votes;
    private TextView ACWinner3;
    private TextView ACWinner3votes;
    private ImageButton actobod;
    private ImageButton actoec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acresults_chart);
        HorizontalBarChart barChart = findViewById(R.id.bar_chart);
        setupHorizontalBarChart(barChart);
        List<String> candidateNames = new ArrayList<>();

        ACWinner1  = findViewById(R.id.ACWinner1);
        ACWinner2  = findViewById(R.id.ACWinner2);
        ACWinner3 = findViewById(R.id.ACWinner3);
        ACWinner1votes = findViewById(R.id.ACWinner1votes);
        ACWinner2votes = findViewById(R.id.ACWinner2votes);
        ACWinner3votes = findViewById(R.id.ACWinner3votes);
        actobod = findViewById(R.id.actobod);
        actoec = findViewById(R.id.actoec);


        actobod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ACResultsChart.this, BODResultsChart.class));
                finish();
            }
        });

        actoec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ACResultsChart.this, ECResultsChart.class));
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
                    if (elective != null && elective.equals("AUDIT COMITTEE")) {
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
                    String acWinner1Name = candidateNames.get((int) entries.get(0).getX());
                    ACWinner1.setText("Top 1: "+acWinner1Name);

                    // Get the number of votes of the winner
                    int acWinner1Votes = (int) entries.get(0).getY();
                    ACWinner1votes.setText("votes: "+(String.valueOf(acWinner1Votes)));
                }

                if (entries.size() > 1) {
                    String acWinner2Name = candidateNames.get((int) entries.get(1).getX());
                    ACWinner2.setText("Top 2: "+acWinner2Name);

                    // Get the number of votes of the runner-up
                    int acWinner2Votes = (int) entries.get(1).getY();
                    ACWinner2votes.setText("votes: "+(String.valueOf(acWinner2Votes)));
                }
                if (entries.size() > 2) {
                    String acWinner3Name = candidateNames.get((int) entries.get(2).getX());
                    ACWinner3.setText("Top 3: "+acWinner3Name);

                    // Get the number of votes of the runner-up
                    int acWinner3Votes = (int) entries.get(2).getY();
                    ACWinner3votes.setText("votes: "+(String.valueOf(acWinner3Votes)));
                }


                BarDataSet dataSet = new BarDataSet(entries, "Candidate Votes");
                dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                dataSet.setValueTextColor(Color.BLACK);
                dataSet.setValueTextSize(16f);
                dataSet.setDrawValues(true);




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
                xAxis.setGranularity(1f);

                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                barChart.setExtraLeftOffset(45f);
                xAxis.setTextSize(14f);

                xAxis.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        return candidateNames.get((int) value % candidateNames.size());
                    }
                });














                // Set up the y-axis
                YAxis yAxis = barChart.getAxisLeft();
                yAxis.setDrawGridLines(false);
                yAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
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