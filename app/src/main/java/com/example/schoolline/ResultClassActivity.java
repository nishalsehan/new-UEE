package com.example.schoolline;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.schoolline.Adapters.ResultClassAdapter;
import com.example.schoolline.Model.Classrooms;


import java.util.ArrayList;

public class ResultClassActivity extends Fragment {

    RecyclerView recyclerView;
    ProgressDialog mDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_result_class,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDialog = new ProgressDialog(getActivity());
        mDialog.setMessage("Loading...");
        mDialog.show();

        recyclerView = view.findViewById(R.id.resultClassList);

        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.swiperefresh);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                displayGrades(); // your code
                System.out.println("refresh");
                pullToRefresh.setRefreshing(false);
            }
        });
        displayGrades();

    }

    private void displayGrades() {
        ArrayList<Classrooms> classList = new ArrayList<>();

        classList.add(new Classrooms("Class A"));
        classList.add(new Classrooms("Class B"));
        classList.add(new Classrooms("Class C"));
        classList.add(new Classrooms("Class D"));
        classList.add(new Classrooms("Class E"));
        classList.add(new Classrooms("Class F"));
        classList.add(new Classrooms("Class G"));
        classList.add(new Classrooms("Class H"));

        setupRecyclerview(classList);
    }
    private void setupRecyclerview(ArrayList<Classrooms> grades) {
        ResultClassAdapter myAdapter = new ResultClassAdapter(getActivity(),grades,mDialog);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(myAdapter);
    }
}
