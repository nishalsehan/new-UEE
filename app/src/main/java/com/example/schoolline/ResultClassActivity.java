package com.example.schoolline;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.schoolline.Adapters.ResultClassAdapter;
import com.example.schoolline.Model.Classrooms;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


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
        mDialog.setCancelable(true);
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
        final ArrayList<Classrooms> classList = new ArrayList<>();
        SharedPreferences prefs = getActivity().getSharedPreferences("My pref", Context.MODE_PRIVATE);

        String grade = prefs.getString("grade", null);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Grades").child(grade).child("Classrooms");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot ds:dataSnapshot.getChildren()){
                        Classrooms classrooms = ds.getValue(Classrooms.class);
                        classList.add(classrooms);
                    }
                    setupRecyclerview(classList);
                }else{
                    mDialog.dismiss();
                    Toast.makeText(getContext(),"No classes added yet",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    private void setupRecyclerview(ArrayList<Classrooms> grades) {
        ResultClassAdapter myAdapter = new ResultClassAdapter(getActivity(),grades,mDialog);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(myAdapter);
    }
}
