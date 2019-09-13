package com.example.schoolline;

import android.app.ProgressDialog;
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

import com.example.schoolline.Model.Classroom;
import com.example.schoolline.Model.Classrooms;
import com.example.schoolline.Model.Grades;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;

public class AddClassroomActivity extends Fragment {

    MaterialSpinner grade,teacher01,teacher02;
    String gradeString = null;
    ArrayList<String> keyArray;
    String teacher1String = null;
    int gradeIndex;
    String teacher2String = null;
    EditText name,desc;
    ProgressDialog mDialog;

    Button save,back;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_add_classroom,null);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDialog = new ProgressDialog(getActivity());
        mDialog.setMessage("Loading...");
        mDialog.setCancelable(false);
        mDialog.show();
        keyArray = new ArrayList<>();
        grade = view.findViewById(R.id.grade);
        teacher01 = view.findViewById(R.id.teacher2);
        teacher02 = view.findViewById(R.id.teacher1);
        name = view.findViewById(R.id.name);
        desc = view.findViewById(R.id.desc);
        save = view.findViewById(R.id.save);
        back = view.findViewById(R.id.back);

        final ArrayList<String> gradeList = new ArrayList<>();
        DatabaseReference gradeRef =FirebaseDatabase.getInstance().getReference("Grades");
        gradeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                gradeList.add("Choose the Grade");
                keyArray.add("no");
                if(dataSnapshot.exists()){
                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                        Grades grades = ds.getValue(Grades.class);
                        gradeList.add(grades.getName());
                        keyArray.add(ds.getKey());
                    }
                    grade.setItems(gradeList);
                    mDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        teacher01.setItems("Choose the Teacher", "Ms. Kamali", "Mr. Prasanna", "Ms. Anuradhi", "Ms. Madhawi", "Mr. Ariyadasa");
        teacher02.setItems("Choose the Teacher", "Ms. Kamali", "Mr. Prasanna", "Ms. Anuradhi", "Ms. Madhawi", "Mr. Ariyadasa");

        grade.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if(position!=0){
                    gradeString = item.toString();
                    gradeIndex = position;
                }else{
                    gradeString = null;
                }
            }
        });

        teacher01.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if(position!=0){
                    teacher1String = item.toString();
                }else{
                    teacher1String = null;
                }
            }
        });

        teacher02.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if(position!=0){
                    teacher2String = item.toString();
                }else{
                    teacher2String = null;
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_fragment_area, new ClassroomDashboardActivity());
                fragmentTransaction.addToBackStack(MainActivity.class.getName());
                fragmentTransaction.commit();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().isEmpty()||desc.getText().toString().isEmpty()||grade==null||teacher1String==null||teacher2String==null){
                    Toast.makeText(getActivity(),"Please fill all",Toast.LENGTH_SHORT).show();
                }else {

                    Classrooms classroom = new Classrooms();
                    classroom.setName(name.getText().toString());
                    classroom.setDescription(desc.getText().toString());
                    classroom.setTeacher01(teacher1String);
                    classroom.setTeacher02(teacher2String);
                    classroom.setGrade(gradeString);
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    String key = reference.push().getKey();
                    reference.child("Grades").child(keyArray.get(gradeIndex)).child("Classrooms").child(key).setValue(classroom);

                    reference.child("Classrooms").child(key).setValue(classroom);



                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_fragment_area, new ClassroomDashboardActivity());
                    fragmentTransaction.addToBackStack(MainActivity.class.getName());
                    fragmentTransaction.commit();
                    Toast.makeText(getActivity(),"Successfully added",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
