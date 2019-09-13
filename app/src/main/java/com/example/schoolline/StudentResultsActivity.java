package com.example.schoolline;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.example.schoolline.Adapters.StudentResultAdapter;
import com.example.schoolline.Model.Results;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import javax.xml.transform.Result;

public class StudentResultsActivity extends Fragment {

    TextView title;
    RecyclerView recyclerView;
    ProgressDialog mDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_student_results,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDialog = new ProgressDialog(getActivity());
        mDialog.setMessage("Loading...");
        mDialog.setCancelable(false);
        mDialog.show();


        title = view.findViewById(R.id.resultTitle);

        SharedPreferences prefs = getActivity().getSharedPreferences("My pref", Context.MODE_PRIVATE);

        String subject = prefs.getString("subject", null);

        title.setText("Individual Results of "+subject);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv);

        getData();

        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.swiperefresh);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(); // your code
                System.out.println("refresh");
                pullToRefresh.setRefreshing(false);
            }
        });

    }

    private void getData() {
        final ArrayList<Results> resultList = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Results");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                        Results results = ds.getValue(Results.class);
                        System.out.println(ds.getKey());
                        resultList.add(results);

                    }
                    setupRecyclerView(resultList);
                }else {
                    mDialog.dismiss();
                    Toast.makeText(getActivity(),"No Results yet",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setupRecyclerView(ArrayList<Results> resultList){
        StudentResultAdapter recyclerAdapter = new StudentResultAdapter(getActivity(), resultList,mDialog);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(recyclerAdapter);
    }
}
