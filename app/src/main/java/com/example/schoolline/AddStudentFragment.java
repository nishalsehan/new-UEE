package com.example.schoolline;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.schoolline.Model.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddStudentFragment extends Fragment {


    final Calendar myCalendar = Calendar.getInstance();
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextAge;
    private EditText editTextDOB;
    private EditText editTextAddress;
    private EditText editTextContact;
    private EditText editTextEmail;
    private DatePickerDialog.OnDateSetListener date;
    private Spinner dropDownGender;
    private View view;
    private String[] items = new String[]{"Select Gender","Male", "Female"};
    private Button btnAddStudent;
    private ProgressDialog progressDialog;


    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_add_student, container, false);
        editTextDOB=view.findViewById(R.id.editTextRegisterStudentDOB);
        dropDownGender=view.findViewById(R.id.spinnerRegisterStudentGender);
        editTextFirstName=view.findViewById(R.id.editTextRegisterStudentFirstName);
        editTextLastName=view.findViewById(R.id.editTextRegisterStudentLastName);
        editTextAge=view.findViewById(R.id.editTextRegisterStudentAge);
        editTextAddress=view.findViewById(R.id.editTextRegisterStudentAddress);
        editTextContact=view.findViewById(R.id.editTextRegisterStudentContact);
        editTextEmail=view.findViewById(R.id.editTextRegisterStudentEmail);
        btnAddStudent=view.findViewById(R.id.btnRegisterStudentAdd);
        progressDialog=new ProgressDialog(getActivity());
        databaseReference= FirebaseDatabase.getInstance().getReference("students");

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        editTextDOB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });






        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,items);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        dropDownGender.setAdapter(adapter);





        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id=databaseReference.push().getKey();
                String firstName=editTextFirstName.getText().toString().trim();
                String lastName=editTextLastName.getText().toString().trim();
                String name=firstName+" "+lastName;
                int age=Integer.parseInt(editTextAge.getText().toString());
                String dob=editTextDOB.getText().toString().trim();
                String gender=dropDownGender.getSelectedItem().toString();
                String address=editTextAddress.getText().toString().trim();
                String contact=editTextContact.getText().toString().trim();
                String email=editTextEmail.getText().toString().trim();


                if( TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) ){
                    Toast.makeText(getActivity(),"Enter Name",Toast.LENGTH_LONG).show();
                }else{

                    progressDialog.setTitle("Register Student");
                    progressDialog.setMessage("Please wait, Registering Student...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    Student student=new Student(name,age,dob,gender,address,contact,email);

                    addStudent(student,id);
                }
            }
        });
        return view;
    }



    public void addStudent(Student student,String id){


        databaseReference.child(id).setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                    progressDialog.dismiss();

                    Fragment fragment = new StudentsFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_fragment_area, fragment);
                    fragmentTransaction.commit();

                }else{
                    progressDialog.hide();
                    Toast.makeText(getActivity(),"Please Check Your Network Connection",Toast.LENGTH_LONG).show();
                }
            }
        });



    }


    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editTextDOB.setText(sdf.format(myCalendar.getTime()));
    }




}
