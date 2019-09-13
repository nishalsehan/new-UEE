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

import com.example.schoolline.Adapters.TeacherViewAdapter;
import com.example.schoolline.Model.Classrooms;
import com.example.schoolline.Model.Teacher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TeacherViewActivity extends Fragment {

    RecyclerView recyclerView;
    ProgressDialog mDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_teacher_view,null);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDialog = new ProgressDialog(getActivity());
        mDialog.setMessage("Loading...");
        mDialog.setCancelable(false);
        mDialog.show();
        recyclerView = view.findViewById(R.id.TeacherList);
        displayTeachers();
    }



    private void displayTeachers() {

        final ArrayList<Teacher> teacherList = new ArrayList<>();

        DatabaseReference gradeRef = FirebaseDatabase.getInstance().getReference("Teachers");

        gradeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                        Teacher teacher = ds.getValue(Teacher.class);
                        teacher.setId(ds.getKey());
                        teacherList.add(teacher);
                    }
                    setupRecyclerview(teacherList);
                }else {
                    mDialog.dismiss();
                    Toast.makeText(getActivity(),"No Teachers yet",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void setupRecyclerview(ArrayList<Teacher> teachers) {
        TeacherViewAdapter myAdapter = new TeacherViewAdapter(getActivity(),teachers,mDialog);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(myAdapter);
    }
}
