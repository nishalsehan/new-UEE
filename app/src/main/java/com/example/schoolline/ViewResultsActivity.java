package com.example.schoolline;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.schoolline.Model.Results;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewResultsActivity extends Fragment {

    TextView title;
    ImageView next;
    PieChart pieChart;
    ProgressDialog mDialog;
    int gradeA = 0;
    int gradeB = 0;
    int gradeC = 0;
    int gradeS = 0;
    int gradeF = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_view_results,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDialog = new ProgressDialog(getActivity());
        mDialog.setMessage("Loading...");
        mDialog.setCancelable(false);
        mDialog.show();

        title = view.findViewById(R.id.resultTitle);

        next = view.findViewById(R.id.next);

        pieChart = view.findViewById(R.id.piechart);

        SharedPreferences prefs =getActivity(). getSharedPreferences("My pref", Context.MODE_PRIVATE);

        final String subject = prefs.getString("subject",null);

        gradeA = prefs.getInt("gradeA",0);
        gradeB = prefs.getInt("gradeB",0);
        gradeC = prefs.getInt("gradeC",0);
        gradeS = prefs.getInt("gradeS",0);
        gradeF = prefs.getInt("gradeF",0);

        title.setText("Results of "+subject);

        displayChart();


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_fragment_area, new StudentResultsActivity());
                fragmentTransaction.addToBackStack(ViewResultsActivity.class.getName());
                fragmentTransaction.commit();

            }
        });

    }

    private void displayChart() {

        pieChart.setUsePercentValues(true);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(30f);
        pieChart.setHoleRadius(30f);

        ArrayList<Entry> yvalues = new ArrayList<Entry>();
        yvalues.add(new Entry(gradeA, 0));
        yvalues.add(new Entry(gradeB, 1));
        yvalues.add(new Entry(gradeC, 2));
        yvalues.add(new Entry(gradeS, 3));
        yvalues.add(new Entry(gradeF, 4));

        PieDataSet dataSet = new PieDataSet(yvalues, "");

        ArrayList<String> xVals = new ArrayList<>();

        xVals.add("A\n+75%");
        xVals.add("B\n+65%");
        xVals.add("C\n+50%");
        xVals.add("S\n+35%");
        xVals.add("F\n-35%");

        PieData data = new PieData(xVals, dataSet);

        data.setValueFormatter(new PercentFormatter());

        data.setValueTextSize(10f);

        data.setHighlightEnabled(true);

        data.setValueTextColor(R.color.colorPrimary);

        pieChart.setData(data);

        pieChart.setDescriptionTextSize(30f);

        pieChart.setDescription("");

        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        mDialog.dismiss();
    }

}
