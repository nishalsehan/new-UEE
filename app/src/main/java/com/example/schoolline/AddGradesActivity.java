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
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.schoolline.Model.Grades;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddGradesActivity extends Fragment {

    EditText name, desc;
    Button save,back;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_add_grades,null);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = view.findViewById(R.id.name);
        desc =  view.findViewById(R.id.desc);

        back = view.findViewById(R.id.back);
        save = view.findViewById(R.id.save);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_fragment_area, new GradeDashboard());
                fragmentTransaction.addToBackStack(MainActivity.class.getName());
                fragmentTransaction.commit();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().isEmpty()||desc.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(),"Please fill all",Toast.LENGTH_SHORT).show();
                }else{
                    Grades grades = new Grades();

                    grades.setName(name.getText().toString());
                    grades.setDescription(desc.getText().toString());

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Grades");

                    reference.child(reference.push().getKey()).setValue(grades);

                    Toast.makeText(getActivity(),"Successfully added",Toast.LENGTH_SHORT).show();

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_fragment_area, new GradeDashboard());
                    fragmentTransaction.addToBackStack(MainActivity.class.getName());
                    fragmentTransaction.commit();

                }
            }
        });


    }
}
