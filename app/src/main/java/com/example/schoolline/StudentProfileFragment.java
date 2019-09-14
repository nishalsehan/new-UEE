package com.example.schoolline;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentProfileFragment extends Fragment {

    private View view;
    private DatabaseReference studentDatabaseReference;
    private TextView textViewName;
    private TextView textViewAge;
    private TextView textViewDOB;
    private TextView textViewGender;
    private TextView textViewAddress;
    private TextView textViewContact;
    private TextView textViewEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_student_profile, container, false);

        Bundle bundle=getArguments();
        String list_student_id=bundle.getString("list_student_id");

        studentDatabaseReference= FirebaseDatabase.getInstance().getReference().child("students").child(list_student_id);

        textViewName=view.findViewById(R.id.student_profile_value_name);
        textViewAge=view.findViewById(R.id.student_profile_value_age);
        textViewDOB=view.findViewById(R.id.student_profile_value_dob);
        textViewGender=view.findViewById(R.id.student_profile_value_gender);
        textViewAddress=view.findViewById(R.id.student_profile_value_address);
        textViewContact=view.findViewById(R.id.student_profile_value_contact);
        textViewEmail=view.findViewById(R.id.student_profile_value_email);


        studentDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name=dataSnapshot.child("name").getValue().toString();
                String age=dataSnapshot.child("age").getValue().toString();
                String dob=dataSnapshot.child("dob").getValue().toString();
                String gender=dataSnapshot.child("gender").getValue().toString();
                String address=dataSnapshot.child("address").getValue().toString();
                String contact=dataSnapshot.child("contact").getValue().toString();
                String email=dataSnapshot.child("email").getValue().toString();


                textViewName.setText(": "+name);
                textViewAge.setText(": "+age);
                textViewDOB.setText(": "+dob);
                textViewGender.setText(": "+gender);
                textViewAddress.setText(": "+address);
                textViewContact.setText(": "+contact);
                textViewEmail.setText(": "+email);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Toast.makeText(getActivity(),"Loading Profile...",Toast.LENGTH_LONG).show();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
