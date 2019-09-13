package com.example.schoolline.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolline.GradeDashboard;
import com.example.schoolline.Model.Results;
import com.example.schoolline.Model.Subject;
import com.example.schoolline.R;
import com.example.schoolline.ResultSubjectActivity;
import com.example.schoolline.ViewResultsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        int gradeA = 0;
        int gradeB = 0;
        int gradeC = 0;
        int gradeS = 0;
        int gradeF = 0;




        public ResultSubjectViewHolder(@NonNull View itemView, final Context context, final List<Subject> item) {
            super(itemView);

            name = itemView.findViewById(R.id.name);

            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    System.out.println(item.get(getAdapterPosition()).getName());

                    final SharedPreferences.Editor editor = ((FragmentActivity)context).getSharedPreferences("My pref", Context.MODE_PRIVATE).edit();

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Results");



                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                for(DataSnapshot ds:dataSnapshot.getChildren()){
                                    Results results = ds.getValue(Results.class);
                                    System.out.println(results.getAvg());
                                    if(results.getAvg()>=75){
                                        gradeA++;
                                    }else if(results.getAvg()>=65){
                                        gradeB++;
                                    }else if(results.getAvg()>=50){
                                        gradeC++;
                                    }else if(results.getAvg()>=35){
                                        gradeS++;
                                    }else{
                                        gradeF++;
                                    }


                                }
                                editor.putInt("gradeA", gradeA);
                                editor.putInt("gradeB", gradeB);
                                editor.putInt("gradeC", gradeC);
                                editor.putInt("gradeS", gradeS);
                                editor.putInt("gradeF", gradeF);
                                editor.putString("subject", item.get(getAdapterPosition()).getName());

                                editor.apply();

                                FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.main_fragment_area, new ViewResultsActivity());
                                fragmentTransaction.addToBackStack(ResultSubjectActivity.class.getName());

                                fragmentTransaction.commit();
                            }else {
                                editor.putInt("gradeA", gradeA);
                                editor.putInt("gradeB", gradeB);
                                editor.putInt("gradeC", gradeC);
                                editor.putInt("gradeS", gradeS);
                                editor.putInt("gradeF", gradeF);
                                editor.putString("subject", item.get(getAdapterPosition()).getName());

                                editor.apply();

                                FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.main_fragment_area, new ViewResultsActivity());
                                fragmentTransaction.addToBackStack(ResultSubjectActivity.class.getName());

                                fragmentTransaction.commit();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            });
        }
    }
}

