package com.example.schoolline;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.schoolline.Model.Results;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class AddResultsActivity extends Fragment {


    MaterialSpinner grade,classroom,student,subject;
    String gradeString,classroomString,studentString,subjectString;
    int gradePosition = 0;
    int classPosition = 0;
    int studentPosition = 0;
    int subjectPosition = 0;
    Button add,back;
    EditText col01,col02,col03,col04,col05,col06,col07,sum,avg,total;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_add_results,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        grade = view.findViewById(R.id.grade);
        classroom = view.findViewById(R.id.classroom);
        student = view.findViewById(R.id.student);
        subject = view.findViewById(R.id.subject);
        back = view.findViewById(R.id.back);
        add = view.findViewById(R.id.save);
        col01 = view.findViewById(R.id.col01);
        col02 = view.findViewById(R.id.col02);
        col03 = view.findViewById(R.id.col03);
        col04 = view.findViewById(R.id.col04);
        col05 = view.findViewById(R.id.col05);
        col06 = view.findViewById(R.id.col06);
        col07 = view.findViewById(R.id.col07);
        sum = view.findViewById(R.id.sum);
        avg = view.findViewById(R.id.avg);
        total = view.findViewById(R.id.total);

        grade.setItems("Choose the Grade", "Grade 01", "Grade 02", "Grade 03", "Grade 04", "Grade 05");
        classroom.setItems("Choose Grade first");
        subject.setItems("Choose Classroom first");
        student.setItems("Choose Subject first");

        grade.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                System.out.println(item.toString());
                if(position != 0){
                    classroom.setItems("Choose the class","Class A","Class B","Class C","Class D","Class E","Class F");
                    gradeString = item.toString();

                }
                gradePosition = position;
            }
        });

        classroom.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                System.out.println(item.toString());
                if(position != 0){
                    subject.setItems("Choose the subject","English","Buddhism","Sinhala","Environment");
                    classroomString = item.toString();
                }

                classPosition = position;
            }
        });
        subject.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                System.out.println(item.toString());
                if(position != 0){
                    student.setItems("Choose the student","Akitha","Ganesh","Nethu","Dilshan","Some");
                    subjectString = item.toString();
                }

                subjectPosition = position;
            }
        });

        student.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                System.out.println(item.toString());
                if(position!=0) {
                    studentString = item.toString();

                    studentPosition = position;
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_fragment_area, new ResultDashboardActivity());
                fragmentTransaction.addToBackStack(MainActivity.class.getName());
                fragmentTransaction.commit();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(gradePosition != 0 && classPosition != 0 && studentPosition != 0 && subjectPosition != 0 && !sum.getText().toString().isEmpty() && !avg.getText().toString().isEmpty() && !total.getText().toString().isEmpty()) {
                    final Results results = new Results();

                    results.setStudentName(studentString);
                    results.setClassroom(classroomString);
                    results.setSubject(subjectString);
                    results.setGrade(gradeString);
                    results.setCol01(col01.getText().toString());
                    results.setCol02(col02.getText().toString());
                    results.setCol03(col03.getText().toString());
                    results.setCol04(col04.getText().toString());
                    results.setCol05(col05.getText().toString());
                    results.setCol06(col06.getText().toString());
                    results.setCol07(col07.getText().toString());
                    results.setAvg(Double.parseDouble(avg.getText().toString()));
                    results.setSum(Double.parseDouble(sum.getText().toString()));
                    results.setTotal(Double.parseDouble(total.getText().toString()));


                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Results");

                    reference.child(reference.push().getKey()).setValue(results);

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_fragment_area, new ResultDashboardActivity());
                    fragmentTransaction.addToBackStack(MainActivity.class.getName());
                    fragmentTransaction.commit();
                    Toast.makeText(getActivity(),"Successfully added",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getActivity(),"Please fill all",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }



}
