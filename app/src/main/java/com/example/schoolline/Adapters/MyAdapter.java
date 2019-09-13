package com.example.schoolline.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolline.Model.Payment;
import com.example.schoolline.R;

import java.util.ArrayList;


/**
 * Created by Nethu Yahampath on 2019-09-13.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myViewHolder> {

        Context context;
        ArrayList<Payment> payments;

        public MyAdapter(Context c , ArrayList<Payment> p)
        {
            context = c;
            payments = p;
        }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_payment,parent,false));

    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
            holder.amount.setText(String.valueOf(payments.get(position).getAmount()));
            holder.payDate.setText(String.valueOf(payments.get(position).getDate()));
            holder.invoiceNo.setText(payments.get(position).getInvoiceNo());
            holder.studNo.setText(payments.get(position).getStudentNo());
    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{

            TextView studNo,amount,invoiceNo,payDate;

        public myViewHolder(View itemView) {
            super(itemView);
            studNo = (TextView) itemView.findViewById(R.id.txtPaymentStudentNumber);
            amount = (TextView) itemView.findViewById(R.id.txtPayAmount);
            invoiceNo = (TextView)itemView.findViewById(R.id.txtPayInvoiceNumber);
            payDate = (TextView)itemView.findViewById(R.id.txtPayDate);
        }
    }
}
