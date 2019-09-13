package com.example.schoolline;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.schoolline.Model.Subject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddSubjectFragment extends Fragment {

    private View view;

    private EditText editTextName;
    private EditText editTextGrade;
    private EditText editTextClassroom;
    private Button btnAddSubject;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_subject, container, false);

        editTextName=view.findViewById(R.id.editTextAddSubjectName);
        editTextGrade=view.findViewById(R.id.editTextAddSubjectGrade);
        editTextClassroom=view.findViewById(R.id.editTextAddSubjectClassRoom);
        btnAddSubject=view.findViewById(R.id.btnAddSubjectAdd);
        progressDialog=new ProgressDialog(getActivity());
        databaseReference= FirebaseDatabase.getInstance().getReference("subjects");

        btnAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id=databaseReference.push().getKey();
                String name=editTextName.getText().toString().trim();
                String grade=editTextGrade.getText().toString().trim();
                String classroom=editTextClassroom.getText().toString().trim();



                if( TextUtils.isEmpty(name) || TextUtils.isEmpty(grade) ){
                    Toast.makeText(getActivity(),"Enter Name",Toast.LENGTH_LONG).show();
                }else{

                    progressDialog.setTitle("Add Subject");
                    progressDialog.setMessage("Please wait, Adding Subject...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    Subject subject=new Subject(name,grade,classroom);

                    addSubject(subject,id);
                }
            }
        });



        return view;
    }


    public void addSubject(Subject subject,String id) {


        databaseReference.child(id).setValue(subject).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    progressDialog.dismiss();

                    Fragment fragment = new SubjectsFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_fragment_area, fragment);
                    fragmentTransaction.commit();

                } else {
                    progressDialog.hide();
                    Toast.makeText(getActivity(), "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
