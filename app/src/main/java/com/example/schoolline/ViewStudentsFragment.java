package com.example.schoolline;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolline.Model.Student;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ViewStudentsFragment extends Fragment {

    private RecyclerView studentListRecyclerView;
    private SearchView studentsSearchView;
    private DatabaseReference studentDatabaseReference;
    private View viewViewStudentsFragment;


    private ArrayList<Student> studentArrayList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewViewStudentsFragment=inflater.inflate(R.layout.fragment_view_students,container,false);
        studentDatabaseReference= FirebaseDatabase.getInstance().getReference().child("students");
        studentDatabaseReference.keepSynced(true);

        studentListRecyclerView=(RecyclerView)viewViewStudentsFragment.findViewById(R.id.rv_all_students);
        studentListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        studentsSearchView=viewViewStudentsFragment.findViewById(R.id.studentSearchView);

        Toast.makeText(getActivity(),"Please Wait... Loading Student List...",Toast.LENGTH_LONG).show();

        return viewViewStudentsFragment;
    }

    @Override
    public void onStart() {
        super.onStart();

//        if(studentDatabaseReference!=null){
//            studentDatabaseReference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                    if(dataSnapshot.exists()){
//                        studentArrayList=new ArrayList<>();
//
//                        for(DataSnapshot ds:dataSnapshot.getChildren()){
//                            studentArrayList.add(ds.getValue(Student.class));
//                        }
//
//                        StudentAdapter studentAdapter=new StudentAdapter(studentArrayList);
//                        studentListRecyclerView.setAdapter(studentAdapter);
//
//
//                    }
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    Toast.makeText(getActivity(),databaseError.getMessage(),Toast.LENGTH_LONG).show();
//                }
//            });
//        }
//
//
//        if(studentsSearchView!=null){
//            studentsSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextSubmit(String s) {
//                    return false;
//                }
//
//                @Override
//                public boolean onQueryTextChange(String s) {
//                    search(s);
//                    return true;
//                }
//            });
//        }





        FirebaseRecyclerOptions<Student> studentFirebaseRecyclerOptions= new FirebaseRecyclerOptions.Builder<Student>()
                .setQuery(studentDatabaseReference,Student.class)
                .build();


        FirebaseRecyclerAdapter<Student,StudentsViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Student, StudentsViewHolder>(studentFirebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull StudentsViewHolder studentsViewHolder, int i, @NonNull Student student) {



                studentsViewHolder.studentName.setText(student.getName());
                final String list_student_id=getRef(i).getKey();

                studentsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {


                            Bundle bundle=new Bundle();
                            bundle.putString("list_student_id",list_student_id);




                            Fragment fragment = new StudentProfileFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragment.setArguments(bundle);
                            fragmentTransaction.replace(R.id.main_fragment_area, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        } catch(Exception e) {
                            Toast.makeText(getActivity(),"Please Check Your Network Connection",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

            @NonNull
            @Override
            public StudentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_student,parent,false);

                StudentsViewHolder studentsViewHolder=new StudentsViewHolder(view);



                return studentsViewHolder;
            }
        };



        studentListRecyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();

    }

    public static class StudentsViewHolder extends RecyclerView.ViewHolder{

        TextView studentName;

        public StudentsViewHolder(@NonNull View itemView) {
            super(itemView);

            studentName=itemView.findViewById(R.id.tv_student_name);
        }



    }


    private void search(String str){
        ArrayList<Student> myStudentArrayList=new ArrayList<>();

        for(Student student:myStudentArrayList){
            if(student.getName().toLowerCase().contains(str.toLowerCase())){
                myStudentArrayList.add(student);
            }
        }

        StudentAdapter myStudentAdapter=new StudentAdapter(myStudentArrayList);
        studentListRecyclerView.setAdapter(myStudentAdapter);
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
