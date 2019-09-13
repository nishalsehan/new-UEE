package com.example.schoolline;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.schoolline.Model.Expense;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class AddExpenseFragment extends Fragment {


    private View view;


    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private final String TAG = "AddExpensesFragment";

    EditText name,description,amount,paymentDate;
    Button save,cancel;
    DatabaseReference dataRef;
    Expense expense;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_expenses, container, false);





        name = view.findViewById(R.id.txtExpName);
        description = view.findViewById(R.id.txtExpDesc);
        amount = view.findViewById(R.id.txtExpAmount);
        paymentDate = view.findViewById(R.id.txtExpDate);
        save = view.findViewById(R.id.btnExpSave);
        cancel = view.findViewById(R.id.btnExpCancel);

        expense = new Expense();
        dataRef = FirebaseDatabase.getInstance().getReference().child("Expense");

        paymentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datedialog = new DatePickerDialog(getActivity() , android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,year,month,day);

                datedialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datedialog.show();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setText(null);
                description.setText(null);
                amount.setText(null);
                paymentDate.setText(null);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setMessage("Do you want to save the Expense ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                float expAmount = Float.parseFloat(amount.getText().toString().trim());
                                expense.setExpenseName(name.getText().toString().trim());
                                expense.setDescription(description.getText().toString().trim());
                                expense.setAmount(expAmount);
                                expense.setDate(new Date());
                                dataRef.push().setValue(expense);

                                name.setText(null);
                                description.setText(null);
                                amount.setText(null);
                                paymentDate.setText(null);

                                Toast.makeText(getActivity(),"Expense Added Successfully",Toast.LENGTH_LONG).show();
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });


        mDateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker date , int year , int month , int day){
                month = month+1;
                Log.d(TAG , "ondateSet : date yyyy/mm/dd: " + year + "/" + month + "/" + day);
                String theDate = year+ " / " + month + " / " + day;
                paymentDate.setText(theDate);

            }
        };





        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}

