package com.example.schoolline.Adapters;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolline.GradeDashboard;
import com.example.schoolline.Model.Grades;
import com.example.schoolline.R;
import com.example.schoolline.ViewGradesActivity;
import com.example.schoolline.ViewResultsActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.List;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.GradeViewHolder> {


    private Context mContext;
    private List<Grades> mData;
    ProgressDialog mDialog;

    public GradeAdapter(Context mContext, ArrayList<Grades> mData, ProgressDialog bar) {
        this.mContext = mContext;
        this.mData = mData;
        this.mDialog = bar;
    }

    @NonNull
    @Override
    public  GradeAdapter.GradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.grade_list,parent,false);
        return new GradeAdapter.GradeViewHolder(view,mContext,mData);
    }

    @Override
    public void onBindViewHolder(@NonNull final GradeViewHolder myHistoryViewHolder, int i) {
        myHistoryViewHolder.name.setText(mData.get(i).getName());
        myHistoryViewHolder.desc.setText("     "+ mData.get(i).getDescription());
        if(i==mData.size()-1){
            mDialog.dismiss();
        }
    }



    @Override
    public int getItemCount() {

        return mData.size();
    }

    public static class GradeViewHolder extends RecyclerView.ViewHolder{


        TextView name;
        TextView desc;

        TextView deleteBtn;
        TextView editBtn;
        Dialog myDialog;





        public GradeViewHolder(@NonNull View itemView, final Context context, final List<Grades> item) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            desc = itemView.findViewById(R.id.desc);

            deleteBtn = itemView.findViewById(R.id.delete);
            editBtn = itemView.findViewById(R.id.edit);
            myDialog = new Dialog(context);
            deleteBtn.setOnClickListener(new View.OnClickListener() {
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
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Grades").child(item.get(getAdapterPosition()).getId());

                            reference.setValue(null);

                            myDialog.dismiss();

                            FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.main_fragment_area, new ViewGradesActivity());
                            fragmentTransaction.addToBackStack(ViewGradesActivity.class.getName());

                            fragmentTransaction.commit();
                        }
                    });
                    myDialog.show();                }
            });
            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final EditText name;
                    final EditText desc;
                    TextView cancel;
                    TextView update;

                    myDialog.setContentView(R.layout.grade_update);
                    myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    myDialog.setCancelable(false);
                    name = myDialog.findViewById(R.id.gradeName);
                    desc = myDialog.findViewById(R.id.description);
                    name.setText(item.get(getAdapterPosition()).getName());
                    desc.setText(item.get(getAdapterPosition()).getDescription());
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

                            Grades grades = new Grades();

                            grades.setName(name.getText().toString());

                            grades.setDescription(desc.getText().toString());

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Grades").child(item.get(getAdapterPosition()).getId());

                            reference.setValue(grades);

                            myDialog.dismiss();

                            FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.main_fragment_area, new ViewGradesActivity());
                            fragmentTransaction.addToBackStack(ViewGradesActivity.class.getName());
                            fragmentTransaction.commit();
                        }
                    });
                    myDialog.show();
                }
            });
        }
    }
}
