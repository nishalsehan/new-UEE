package com.example.schoolline;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolline.Adapters.ExpenseAdapter;
import com.example.schoolline.Model.Expense;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewExpensesFragment extends Fragment {

    private View view;

    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Expense> list;
    ExpenseAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_expenses, container, false);


        recyclerView = view.findViewById(R.id.expenseRecyclerView);
        recyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));

        reference = FirebaseDatabase.getInstance().getReference().child("Expense");
        //expSearch.setQueryHint("Search Expense");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<Expense>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Expense e = dataSnapshot1.getValue(Expense.class);
                    list.add(e);
                }
                adapter = new ExpenseAdapter(getActivity(),list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}

