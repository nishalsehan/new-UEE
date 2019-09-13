package com.example.schoolline.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolline.Model.Expense;
import com.example.schoolline.R;

import java.util.ArrayList;


/**
 * Created by Nethu Yahampath on 2019-09-13.
 */

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.myViewHolder> {
    Context context;
    ArrayList<Expense> expenses;

    public ExpenseAdapter(Context c , ArrayList<Expense> e)
    {
        context = c;
        expenses = e;
    }

    @NonNull
    @Override
    public ExpenseAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExpenseAdapter.myViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_expense,parent,false));
    }

    @Override
    public void onBindViewHolder(ExpenseAdapter.myViewHolder holder, int position) {
        holder.expName.setText(expenses.get(position).getExpenseName());
        holder.expDesc.setText(expenses.get(position).getDescription());
        holder.expAmount.setText(String.valueOf(expenses.get(position).getAmount()));
        holder.expDate.setText(String.valueOf(expenses.get(position).getDate()));
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView expName,expDesc,expAmount,expDate;

        public myViewHolder(View itemView) {
            super(itemView);
            expName = (TextView) itemView.findViewById(R.id.txtExpenseName);
            expDesc = (TextView) itemView.findViewById(R.id.txtExpDescription);
            expAmount = (TextView)itemView.findViewById(R.id.txtExpenseAmount);
            expDate = (TextView)itemView.findViewById(R.id.txtExpenseDate);
        }
    }
}
