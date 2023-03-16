package com.example.votingapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.votingapp.adaptersNlists.UserSide.ACList;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ChartTest extends AppCompatActivity {


    ACList acList = new ACList();
    // Set the percentage value for acList from Firebase Realtime Database
    //float percentage = acList.getPercentage(); // Get the percentage value



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_test);
        HorizontalBarChart barChart = findViewById(R.id.bar_chart);
        setupHorizontalBarChart(barChart);




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
                //
                Collections.sort(entries, new Comparator<BarEntry>() {
                    @Override
                    public int compare(BarEntry o1, BarEntry o2) {
                        return Float.compare(o2.getY(), o1.getY());
                    }
                });

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