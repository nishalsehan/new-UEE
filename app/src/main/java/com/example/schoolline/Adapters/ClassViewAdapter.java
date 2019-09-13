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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolline.ClassDetailsActivity;
import com.example.schoolline.ClassViewActivity;
import com.example.schoolline.ClassroomDashboardActivity;
import com.example.schoolline.Model.Classrooms;
import com.example.schoolline.R;
import com.example.schoolline.Model.Classroom;
import com.example.schoolline.ViewGradesActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.List;

public class ClassViewAdapter extends RecyclerView.Adapter<ClassViewAdapter.ResultClassViewHolder> {

    private Context mContext;
    private List<Classrooms> mData;
    ProgressDialog mDialog;

    public ClassViewAdapter(Context mContext, List<Classrooms> mData,ProgressDialog dialog) {
        this.mContext = mContext;
        mDialog = dialog;
        this.mData = mData;
    }

    @NonNull
    @Override
    public  ClassViewAdapter.ResultClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.classroom_list,parent,false);
        return new ClassViewAdapter.ResultClassViewHolder(view,mContext,mData);
    }



    @Override
    public void onBindViewHolder(@NonNull final ClassViewAdapter.ResultClassViewHolder ViewHolder, int i) {
        ViewHolder.name.setText(mData.get(i).getName());
        ViewHolder.desc.setText(mData.get(i).getDescription());
        ViewHolder.grade.setText(mData.get(i).getGrade());
        ViewHolder.teacher01.setText(mData.get(i).getTeacher01());
        ViewHolder.teacher02.setText(mData.get(i).getTeacher02());
        mDialog.dismiss();
    }



    @Override
    public int getItemCount() {

        return mData.size();
    }

    public static class ResultClassViewHolder extends RecyclerView.ViewHolder{


        TextView edit,name,desc,grade,teacher01,teacher02;
        TextView delete;
        Dialog myDialog;






        public ResultClassViewHolder(@NonNull View itemView, final Context context, final List<Classrooms> item) {
            super(itemView);

            edit =itemView.findViewById(R.id.edit);
            delete =itemView.findViewById(R.id.delete);
            name =itemView.findViewById(R.id.name);
            desc =itemView.findViewById(R.id.desc);
            grade =itemView.findViewById(R.id.grade);
            teacher01 =itemView.findViewById(R.id.teacher01);
            teacher02 =itemView.findViewById(R.id.teacher02);

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
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Classrooms").child(item.get(getAdapterPosition()).getId());

                            reference.setValue(null);

                            myDialog.dismiss();

                            FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.main_fragment_area, new ClassViewActivity());
                            fragmentTransaction.addToBackStack(ClassroomDashboardActivity.class.getName());

                            fragmentTransaction.commit();
                        }
                    });
                    myDialog.show();                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    TextView cancel;
                    TextView update;
                    final EditText dName,dDesc;
                    MaterialSpinner dGrade,dTeacher01,dTeacher02;

                    myDialog.setContentView(R.layout.activity_class_details);
                    myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    dGrade = myDialog.findViewById(R.id.grade);
                    dTeacher01 = myDialog.findViewById(R.id.teacher2);
                    dTeacher02 = myDialog.findViewById(R.id.teacher1);
                    dName = myDialog.findViewById(R.id.name);
                    dDesc = myDialog.findViewById(R.id.description);

                    dName.setText(item.get(getAdapterPosition()).getName());
                    dDesc.setText(item.get(getAdapterPosition()).getDescription());

                    dGrade.setItems(item.get(getAdapterPosition()).getGrade(),"Grade 01","Grade 02","Grade03","Grade 04");
                    dTeacher01.setItems(item.get(getAdapterPosition()).getTeacher01(), "Ms. Kamali", "Mr. Prasanna", "Ms. Anuradhi", "Ms. Madhawi", "Mr. Ariyadasa");
                    dTeacher02.setItems(item.get(getAdapterPosition()).getTeacher02(), "Ms. Kamali", "Mr. Prasanna", "Ms. Anuradhi", "Ms. Madhawi", "Mr. Ariyadasa");

                    myDialog.setCancelable(false);
                    cancel = myDialog.findViewById(R.id.cancel);
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
                            myDialog.dismiss();
                        }
                    });
                    myDialog.show();
                }
            });

        }
    }

}
