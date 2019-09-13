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

public class PaymentsExpensesFragment extends Fragment {

    private CardView addPayment , viewPayment , addExpense , viewExpense;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_payments_expenses, container, false);
        addPayment = view.findViewById(R.id.addStudentPayment);
        viewPayment = view.findViewById(R.id.viewStudentPayments);
        addExpense = view.findViewById(R.id.addExpenses);
        viewExpense = view.findViewById(R.id.viewExpenses);


        addPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Fragment fragment = new AddPaymentFragment();
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


        viewPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Fragment fragment = new ViewPaymentsFragment();
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



        addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Fragment fragment = new AddExpenseFragment();
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


        viewExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Fragment fragment = new ViewExpensesFragment();
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
