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

import com.example.schoolline.Model.Classrooms;
import com.example.schoolline.R;
import com.example.schoolline.ResultClassActivity;
import com.example.schoolline.ResultSubjectActivity;
import com.example.schoolline.ViewResultsActivity;

import java.util.ArrayList;
import java.util.List;

public class ResultClassAdapter extends RecyclerView.Adapter<ResultClassAdapter.ResultClassViewHolder> {


    private Context mContext;
    private List<Classrooms> mData;
    ProgressDialog mDialog;


    public ResultClassAdapter(Context mContext, ArrayList<Classrooms> mData, ProgressDialog bar) {
        this.mContext = mContext;
        this.mData = mData;
        this.mDialog = bar;

    }

    @NonNull
    @Override
    public  ResultClassAdapter.ResultClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.result_list,parent,false);
        return new ResultClassAdapter.ResultClassViewHolder(view,mContext,mData);
    }

    @Override
    public void onBindViewHolder(@NonNull final ResultClassAdapter.ResultClassViewHolder ViewHolder, int i) {
        ViewHolder.name.setText(mData.get(i).getName());
        if(i==mData.size()-1){
            mDialog.dismiss();
        }
    }



    @Override
    public int getItemCount() {

        return mData.size();
    }

    public static class ResultClassViewHolder extends RecyclerView.ViewHolder{


        TextView name;






        public ResultClassViewHolder(@NonNull View itemView, final Context context, final List<Classrooms> item) {
            super(itemView);

            name = itemView.findViewById(R.id.name);

            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor editor = ((FragmentActivity)context).getSharedPreferences("My pref", Context.MODE_PRIVATE).edit();

                    editor.putString("class", item.get(getAdapterPosition()).getName());

                    editor.apply();
                    FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_fragment_area, new ResultSubjectActivity());
                    fragmentTransaction.addToBackStack(ResultClassActivity.class.getName());
                    fragmentTransaction.commit();
                }
            });
        }
    }
}
