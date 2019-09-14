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

public class SubjectDetailsFragment extends Fragment {

    private View view;
    private DatabaseReference subjectDatabaseReference;
    private TextView textViewName;
    private TextView textViewGrade;
    private TextView textViewClassroom;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_subjects, container, false);

        Bundle bundle=getArguments();
        final String list_subject_id=bundle.getString("list_subject_id");

        subjectDatabaseReference= FirebaseDatabase.getInstance().getReference().child("subjects").child(list_subject_id);

        textViewName=view.findViewById(R.id.subject_details_value_name);
        textViewGrade=view.findViewById(R.id.subject_details_value_grade);
        textViewClassroom=view.findViewById(R.id.subject_details_value_classroom);



        subjectDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name=dataSnapshot.child("name").getValue().toString();
                String grade=dataSnapshot.child("grade").getValue().toString();
                String classroom=dataSnapshot.child("classroom").getValue().toString();



//                textViewName.setText(": "+name);
//                textViewGrade.setText(": "+grade);
//                textViewClassroom.setText(": "+classroom);

                Toast.makeText(getActivity(),list_subject_id,Toast.LENGTH_LONG).show();




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Toast.makeText(getActivity(),"Loading Details...",Toast.LENGTH_LONG).show();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
