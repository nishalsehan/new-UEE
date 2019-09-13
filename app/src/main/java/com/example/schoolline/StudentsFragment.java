package com.example.schoolline;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class StudentsFragment extends Fragment {

    private CardView addStudentBtn;
    private CardView viewStudentsBtn;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        view = inflater.inflate(R.layout.fragment_students, container, false);
        addStudentBtn = view.findViewById(R.id.students_add_student_card_view);
        viewStudentsBtn=view.findViewById(R.id.students_view_students_card_view);


        addStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Fragment fragment = new AddStudentFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_fragment_area, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                } catch(Exception e) {
                    Toast.makeText(getActivity(),"Please Check Your Network Connection",Toast.LENGTH_LONG).show();
                }





            }
        });


        viewStudentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Fragment fragment = new ViewStudentsFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_fragment_area, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                } catch(Exception e) {
                    Toast.makeText(getActivity(),"Please Check Your Network Connection",Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
