package com.example.schoolline;

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

import com.example.schoolline.Model.Teacher;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class AddTeacherActivity extends Fragment {


    MaterialSpinner gender;
    String genderString = null;
    EditText fname,lname,email,phone;
    Button back,save;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_add_teacher,null);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fname = view.findViewById(R.id.fname);
        lname = view.findViewById(R.id.lname);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        gender = view.findViewById(R.id.gender);
        save = view.findViewById(R.id.save);
        back = view.findViewById(R.id.back);

        gender.setItems("Select Gender","Male","Female");

        gender.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if(position!=0){
                    genderString = item.toString();
                }else {
                    genderString = null;
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
                if (fname.getText().toString().isEmpty() || lname.getText().toString().isEmpty() || genderString == null || phone.getText().toString().isEmpty() || email.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please fill all", Toast.LENGTH_SHORT).show();
                } else {

                    Teacher teacher = new Teacher();

                    teacher.setfName(fname.getText().toString());
                    teacher.setlName(lname.getText().toString());
                    teacher.setGender(genderString);
                    teacher.setEmail(email.getText().toString());
                    teacher.setPhone(phone.getText().toString());

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    String key = reference.push().getKey();

                    reference.child("Teachers").child(key).setValue(teacher);


                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_fragment_area, new TeacherDashboardActivity());
                    fragmentTransaction.addToBackStack(MainActivity.class.getName());
                    fragmentTransaction.commit();
                    Toast.makeText(getActivity(),"Successfully added",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
