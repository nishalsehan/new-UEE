package com.example.schoolline.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolline.Model.Results;
import com.example.schoolline.R;

import java.util.ArrayList;
import java.util.List;

public class StudentResultAdapter extends RecyclerView.Adapter<StudentResultAdapter.StudentResultViewHolder> {


    private Context mContext;
    private List<Results> mData;
    ProgressDialog mDialog;
    public StudentResultAdapter(Context mContext, ArrayList<Results> mData,ProgressDialog bar) {
        this.mContext = mContext;
        this.mData = mData;
        this.mDialog = bar;


    }

    @NonNull
    @Override
    public  StudentResultAdapter.StudentResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.result_item,parent,false);
        return new StudentResultAdapter.StudentResultViewHolder(view,mContext,mData);
    }

    @Override
    public void onBindViewHolder(@NonNull final StudentResultAdapter.StudentResultViewHolder ViewHolder, int i) {
        ViewHolder.grade.setText(": "+mData.get(i).getGrade());
        ViewHolder.classroom.setText(": "+mData.get(i).getClassroom());
        ViewHolder.subject.setText(": "+mData.get(i).getSubject());
        ViewHolder.student.setText(mData.get(i).getStudentName());
        ViewHolder.col01.setText(mData.get(i).getCol01());
        ViewHolder.col02.setText(mData.get(i).getCol02());
        ViewHolder.col03.setText(mData.get(i).getCol03());
        ViewHolder.col04.setText(mData.get(i).getCol04());
        ViewHolder.col05.setText(mData.get(i).getCol05());
        ViewHolder.col06.setText(mData.get(i).getCol06());
        ViewHolder.col07.setText(mData.get(i).getCol07());
        ViewHolder.avg.setText(""+mData.get(i).getAvg());
        ViewHolder.sum.setText(""+mData.get(i).getSum());
        ViewHolder.total.setText(""+mData.get(i).getTotal());

        System.out.println(mData.size());

        mDialog.dismiss();

    }



    @Override
    public int getItemCount() {

        return mData.size();
    }

    public static class StudentResultViewHolder extends RecyclerView.ViewHolder{


        TextView grade;
        TextView subject;
        TextView classroom;
        TextView col01;
        TextView col02;
        TextView col03;
        TextView col04;
        TextView col05;
        TextView col06;
        TextView col07;
        TextView sum;
        TextView avg;
        TextView total;
        TextView student;

        public StudentResultViewHolder(@NonNull View itemView, final Context context, final List<Results> item) {
            super(itemView);


            grade = itemView.findViewById(R.id.grade);
            subject = itemView.findViewById(R.id.subject);
            classroom = itemView.findViewById(R.id.classroom);
            col01 = itemView.findViewById(R.id.col01);
            col02 = itemView.findViewById(R.id.col02);
            col03 = itemView.findViewById(R.id.col03);
            col04 = itemView.findViewById(R.id.col04);
            col05 = itemView.findViewById(R.id.col05);
            col06 = itemView.findViewById(R.id.col06);
            col07 = itemView.findViewById(R.id.col07);
            sum = itemView.findViewById(R.id.sum);
            avg = itemView.findViewById(R.id.avg);
            total = itemView.findViewById(R.id.total);
            student = itemView.findViewById(R.id.stdName);
        }
    }
}

