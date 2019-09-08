package com.example.schoolline;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ViewResultsActivity extends Fragment {

    TextView title;
    ImageView next;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_view_results,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title = view.findViewById(R.id.resultTitle);

        next = view.findViewById(R.id.next);



        PieChart pieChart = (PieChart) view.findViewById(R.id.piechart);

        SharedPreferences prefs =getActivity(). getSharedPreferences("My pref", Context.MODE_PRIVATE);

        final String subject = prefs.getString("subject",null);


        title.setText("Results of "+subject);

        pieChart.setUsePercentValues(true);

        ArrayList<Entry> yvalues = new ArrayList<Entry>();
        yvalues.add(new Entry(3f, 0));
        yvalues.add(new Entry(8f, 1));
        yvalues.add(new Entry(12f, 2));
        yvalues.add(new Entry(10f, 3));
        yvalues.add(new Entry(7f, 4));

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

        pieChart.setData(data);

        pieChart.setDescriptionTextSize(30f);

        pieChart.setDescription("");

        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);

        pieChart.setDrawHoleEnabled(false);

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
}
