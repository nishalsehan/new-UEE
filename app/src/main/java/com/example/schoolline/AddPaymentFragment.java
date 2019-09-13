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

import com.example.schoolline.Model.Payment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class AddPaymentFragment extends Fragment {

    private View view;


    private DatePickerDialog.OnDateSetListener mDateSetListener;
    int count = 1;
    EditText studentNo , amount , invoiceNo , payDate;
    Button submit , cancel;
    DatabaseReference dataRef;
    Payment payment;


    private final String TAG = "AddPaymentFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_payments, container, false);




        studentNo = view.findViewById(R.id.txtStudNumber);
        amount = view.findViewById(R.id.txtAmount);
        invoiceNo =  view.findViewById(R.id.txtInvoiceNumber);
        payDate =  view.findViewById(R.id.txtDate);
        submit =  view.findViewById(R.id.btnSubmit);
        cancel =  view.findViewById(R.id.btnCancel);

        payment = new Payment();
        dataRef = FirebaseDatabase.getInstance().getReference().child("Payment");

        payDate.setOnClickListener(new View.OnClickListener() {
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
                studentNo.setText(null);
                amount.setText(null);
                invoiceNo.setText(null);
                payDate.setText(null);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setMessage("Do you want to save the Payment ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                float payAmount = Float.parseFloat(amount.getText().toString().trim());
                                payment.setStudentNo(studentNo.getText().toString().trim());
                                payment.setAmount(payAmount);
                                payment.setInvoiceNo(invoiceNo.getText().toString().trim());
                                payment.setDate(new Date());
                                dataRef.push().setValue(payment);

                                studentNo.setText(null);
                                amount.setText(null);
                                invoiceNo.setText(null);
                                payDate.setText(null);


                                Toast.makeText(getActivity(),"Payment Added Successfully",Toast.LENGTH_LONG).show();
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
                payDate.setText(theDate);

            }
        };





        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
