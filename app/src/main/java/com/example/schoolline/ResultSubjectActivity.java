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


import com.example.schoolline.Adapters.ResultSubjectAdapter;
import com.example.schoolline.Model.Subject;

import java.util.ArrayList;

public class ResultSubjectActivity extends Fragment {



    RecyclerView recyclerView;
    ProgressDialog mDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_result_subject,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDialog = new ProgressDialog(getActivity());
        mDialog.setMessage("Loading...");
        mDialog.setCancelable(false);
        mDialog.show();
        recyclerView = view.findViewById(R.id.resultSubjectList);

        displayGrades();

    }


    private void displayGrades() {
        ArrayList<Subject> subjectList = new ArrayList<>();

        subjectList.add(new Subject("Mathematics"));
        subjectList.add(new Subject("English"));
        subjectList.add(new Subject("Science"));
        subjectList.add(new Subject("Commerce"));
        subjectList.add(new Subject("Physics"));
        subjectList.add(new Subject("Biology"));
        subjectList.add(new Subject("Drama"));


        setupRecyclerview(subjectList);
    }
    private void setupRecyclerview(ArrayList<Subject> subjects) {
        ResultSubjectAdapter myAdapter = new ResultSubjectAdapter(getActivity(),subjects,mDialog);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(myAdapter);
    }
}
