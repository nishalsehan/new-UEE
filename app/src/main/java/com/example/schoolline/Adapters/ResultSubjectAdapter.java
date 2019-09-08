package com.example.schoolline.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolline.GradeDashboard;
import com.example.schoolline.Model.Subject;
import com.example.schoolline.R;
import com.example.schoolline.ResultSubjectActivity;
import com.example.schoolline.ViewResultsActivity;

import java.util.ArrayList;
import java.util.List;

public class ResultSubjectAdapter extends RecyclerView.Adapter<ResultSubjectAdapter.ResultSubjectViewHolder> {


    private Context mContext;
    private List<Subject> mData;
    ProgressDialog mDialog;

    public ResultSubjectAdapter(Context mContext, ArrayList<Subject> mData,ProgressDialog bar) {
        this.mContext = mContext;
        this.mData = mData;
        this.mDialog = bar;

    }

    @NonNull
    @Override
    public  ResultSubjectAdapter.ResultSubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.result_list,parent,false);
        return new ResultSubjectAdapter.ResultSubjectViewHolder(view,mContext,mData);
    }

    @Override
    public void onBindViewHolder(@NonNull final ResultSubjectAdapter.ResultSubjectViewHolder ViewHolder, int i) {
        ViewHolder.name.setText(mData.get(i).getName());

        if(i==mData.size()-1){
            mDialog.dismiss();
        }
    }



    @Override
    public int getItemCount() {

        return mData.size();
    }

    public static class ResultSubjectViewHolder extends RecyclerView.ViewHolder{


        TextView name;






        public ResultSubjectViewHolder(@NonNull View itemView, final Context context, final List<Subject> item) {
            super(itemView);

            name = itemView.findViewById(R.id.name);

            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    System.out.println(item.get(getAdapterPosition()).getName());

                    SharedPreferences.Editor editor = ((FragmentActivity)context).getSharedPreferences("My pref", Context.MODE_PRIVATE).edit();

                    editor.putString("subject", item.get(getAdapterPosition()).getName());

                    editor.apply();

                    FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_fragment_area, new ViewResultsActivity());
                    fragmentTransaction.addToBackStack(ResultSubjectActivity.class.getName());

                    fragmentTransaction.commit();
                }
            });
        }
    }
}

