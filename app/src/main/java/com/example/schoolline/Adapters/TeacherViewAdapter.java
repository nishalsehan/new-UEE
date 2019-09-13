package com.example.schoolline.Adapters;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolline.MainActivity;
import com.example.schoolline.Model.Grades;
import com.example.schoolline.R;
import com.example.schoolline.TeacherDashboardActivity;
import com.example.schoolline.TeacherDetailsActivity;
import com.example.schoolline.Model.Teacher;
import com.example.schoolline.TeacherViewActivity;
import com.example.schoolline.ViewGradesActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.List;

public class TeacherViewAdapter extends RecyclerView.Adapter<TeacherViewAdapter.TeacherListViewHolder> {


    private Context mContext;
    ProgressDialog dialog;
    private List<Teacher> mData;

    public TeacherViewAdapter(Context mContext, List<Teacher> mData,ProgressDialog mDialog) {
        this.mContext = mContext;
        this.mData = mData;
        dialog = mDialog;
    }

    @NonNull
    @Override
    public TeacherViewAdapter.TeacherListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.teacher_list,parent,false);
        return new TeacherViewAdapter.TeacherListViewHolder(view,mContext,mData);
}

    @Override
    public void onBindViewHolder(@NonNull final TeacherViewAdapter.TeacherListViewHolder ViewHolder, int i) {
        ViewHolder.fname.setText(mData.get(i).getfName());
        ViewHolder.lname.setText(mData.get(i).getlName());
        ViewHolder.gender.setText(mData.get(i).getGender());
        ViewHolder.email.setText(mData.get(i).getEmail());
        ViewHolder.phone.setText(mData.get(i).getPhone());

        dialog.dismiss();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class TeacherListViewHolder extends RecyclerView.ViewHolder {

       TextView edit;
       TextView delete,fname,lname,email,gender,phone;
       Dialog myDialog;


        public TeacherListViewHolder(@NonNull View itemView, final Context context, final List<Teacher> item) {
            super(itemView);


            edit =itemView.findViewById(R.id.edit);
            delete =itemView.findViewById(R.id.delete);
            fname =itemView.findViewById(R.id.fname);
            lname =itemView.findViewById(R.id.lname);
            phone =itemView.findViewById(R.id.phone);
            email =itemView.findViewById(R.id.email);
            gender =itemView.findViewById(R.id.gender);

            myDialog = new Dialog(context);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView delete,cancel;
                    myDialog.setContentView(R.layout.delete_dialog);
                    myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    myDialog.setCancelable(false);
                    cancel = myDialog.findViewById(R.id.cancel);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myDialog.dismiss();
                        }
                    });
                    delete = myDialog.findViewById(R.id.delete);
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                            reference.child("Teachers").child(item.get(getAdapterPosition()).getId()).setValue(null);

                            FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.main_fragment_area, new TeacherViewActivity());
                            fragmentTransaction.addToBackStack(TeacherDashboardActivity.class.getName());

                            fragmentTransaction.commit();

                            Toast.makeText(context,"Successfully Deleted",Toast.LENGTH_SHORT).show();

                            myDialog.dismiss();
                        }
                    });
                    myDialog.show();                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    TextView cancel;
                    TextView update;
                    final EditText fname,lname,email,phone;
                    final RadioButton male,female;
                    myDialog.setContentView(R.layout.teacher_update);
                    myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    myDialog.setCancelable(false);
                    cancel = myDialog.findViewById(R.id.cancel);
                    fname = myDialog.findViewById(R.id.fname);
                    lname = myDialog.findViewById(R.id.lname);
                    email = myDialog.findViewById(R.id.email);
                    phone = myDialog.findViewById(R.id.teleNo);
                    male = myDialog.findViewById(R.id.male);
                    female = myDialog.findViewById(R.id.female);

                    fname.setText(item.get(getAdapterPosition()).getfName());
                    lname.setText(item.get(getAdapterPosition()).getlName());
                    email.setText(item.get(getAdapterPosition()).getEmail());
                    phone.setText(item.get(getAdapterPosition()).getPhone());

                    if(item.get(getAdapterPosition()).getGender().equals("Male")){
                        male.setChecked(true);
                    }else{
                        female.setChecked(true);
                    }
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myDialog.dismiss();
                        }
                    });
                    update = myDialog.findViewById(R.id.update);
                    update.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Teacher teacher = new Teacher();

                            teacher.setfName(fname.getText().toString());
                            teacher.setlName(lname.getText().toString());
                            if(male.isChecked()){
                                teacher.setGender("Male");
                            }else{
                                teacher.setGender("Female");
                            }
                            teacher.setEmail(email.getText().toString());
                            teacher.setPhone(phone.getText().toString());

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                            reference.child("Teachers").child(item.get(getAdapterPosition()).getId()).setValue(teacher);


                            FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.main_fragment_area, new TeacherViewActivity());
                            fragmentTransaction.addToBackStack(TeacherDashboardActivity.class.getName());

                            fragmentTransaction.commit();

                            Toast.makeText(context,"Successfully added",Toast.LENGTH_SHORT).show();
                            myDialog.dismiss();
                        }
                    });
                    myDialog.show();
                }
            });


        }
    }
}
