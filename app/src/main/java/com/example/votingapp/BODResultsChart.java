package com.example.votingapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.votingapp.adaptersNlists.UserSide.ACList;
import com.example.votingapp.adaptersNlists.UserSide.BODList;
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


public class BODResultsChart extends AppCompatActivity {


    BODList bodList = new BODList();
    // Set the percentage value for acList from Firebase Realtime Database
    //float percentage = acList.getPercentage(); // Get the percentage value
    private TextView BODWinner1;
    private TextView BODWinner2;
    private TextView BODWinner1votes;
    private TextView BODWinner2votes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bodresults_chart);
        HorizontalBarChart barChart = findViewById(R.id.BODbar_chart);
        setupHorizontalBarChart(barChart);

        BODWinner1  = findViewById(R.id.BODWinner1);
        BODWinner2  = findViewById(R.id.BODWinner2);
        BODWinner1votes = findViewById(R.id.BODWinner1votes);
        BODWinner2votes = findViewById(R.id.BODWinner2votes);

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
                    if (elective != null && elective.equals("DIRECTOR")) {
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
                    String bodWinner1Name = candidateNames.get((int) entries.get(0).getX());
                    BODWinner1.setText(bodWinner1Name);

                    // Get the number of votes of the winner
                    int bodWinner1Votes = (int) entries.get(0).getY();
                    BODWinner1votes.setText("votes: "+(String.valueOf(bodWinner1Votes)));
                }

                if (entries.size() > 1) {
                    String bodWinner2Name = candidateNames.get((int) entries.get(1).getX());
                    BODWinner2.setText(bodWinner2Name);

                    // Get the number of votes of the runner-up
                    int bodWinner2Votes = (int) entries.get(1).getY();
                    BODWinner2votes.setText("votes: "+(String.valueOf(bodWinner2Votes)));
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