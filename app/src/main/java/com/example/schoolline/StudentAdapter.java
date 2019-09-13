package com.example.schoolline;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolline.Model.Student;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyStudentViewHolder> {

    ArrayList<Student> studentArrayList;

    public StudentAdapter(ArrayList<Student> studentArrayList) {
        this.studentArrayList = studentArrayList;
    }

    @NonNull
    @Override
    public MyStudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_student,parent,false);

        return new MyStudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyStudentViewHolder holder, int position) {

        holder.studentName.setText(studentArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return studentArrayList.size();
    }

    class MyStudentViewHolder extends RecyclerView.ViewHolder{

        TextView studentName;

        public MyStudentViewHolder(@NonNull View itemView) {
            super(itemView);

            studentName=itemView.findViewById(R.id.tv_student_name);
        }
    }
}
