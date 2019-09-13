package com.example.schoolline;

import android.app.ProgressDialog;
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


import com.example.schoolline.Adapters.GradeAdapter;
import com.example.schoolline.Model.Grades;
import com.example.schoolline.Model.Results;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewGradesActivity extends Fragment {

    RecyclerView recyclerView;
    ProgressDialog mDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_view_grades,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDialog = new ProgressDialog(getActivity());
        mDialog.setMessage("Please wait...");
        mDialog.setCancelable(false);
        mDialog.show();
        recyclerView = view.findViewById(R.id.gradeList);

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
        final ArrayList<Grades> gradeList = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Grades");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                        Grades grade = ds.getValue(Grades.class);
                        System.out.println(ds.getKey());
                        grade.setId(ds.getKey());
                        gradeList.add(grade);

                    }
                    setupRecyclerview(gradeList);
                }else {
                    mDialog.dismiss();
                    Toast.makeText(getActivity(),"No Grades yet",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void setupRecyclerview(ArrayList<Grades> grades) {
        GradeAdapter myAdapter = new GradeAdapter(getActivity(),grades,mDialog);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(myAdapter);
    }
}
