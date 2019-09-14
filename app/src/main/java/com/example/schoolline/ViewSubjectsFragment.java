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
import com.example.schoolline.Model.Subject;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewSubjectsFragment extends Fragment {

    private RecyclerView subjectListRecyclerView;
    private SearchView subjectsSearchView;
    private DatabaseReference subjectDatabaseReference;
    private View viewViewSubjectsFragment;


    private ArrayList<Student> subjectArrayList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewViewSubjectsFragment=inflater.inflate(R.layout.fragment_view_subjects,container,false);
        subjectDatabaseReference= FirebaseDatabase.getInstance().getReference().child("subjects");
        subjectDatabaseReference.keepSynced(true);

        subjectListRecyclerView=(RecyclerView)viewViewSubjectsFragment.findViewById(R.id.rv_all_subjects);
        subjectListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        subjectsSearchView=viewViewSubjectsFragment.findViewById(R.id.subjectSearchView);

        Toast.makeText(getActivity(),"Please Wait... Loading Subject List...",Toast.LENGTH_LONG).show();

        return viewViewSubjectsFragment;
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





        FirebaseRecyclerOptions<Subject> subjectFirebaseRecyclerOptions= new FirebaseRecyclerOptions.Builder<Subject>()
                .setQuery(subjectDatabaseReference,Subject.class)
                .build();


        FirebaseRecyclerAdapter<Subject, ViewSubjectsFragment.SubjectsViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Subject, ViewSubjectsFragment.SubjectsViewHolder>(subjectFirebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull ViewSubjectsFragment.SubjectsViewHolder subjectsViewHolder, int i, @NonNull Subject subject) {



                subjectsViewHolder.subjectName.setText(subject.getName());
                final String list_subject_id=getRef(i).getKey();

                subjectsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {


                            Bundle bundle=new Bundle();
                            bundle.putString("list_subject_id",list_subject_id);

                            
                            Fragment fragment = new SubjectDetailsFragment();
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
            public ViewSubjectsFragment.SubjectsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_subject,parent,false);

                ViewSubjectsFragment.SubjectsViewHolder subjectsViewHolder=new ViewSubjectsFragment.SubjectsViewHolder(view);



                return subjectsViewHolder;
            }
        };



        subjectListRecyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();

    }

    public static class SubjectsViewHolder extends RecyclerView.ViewHolder{

        TextView subjectName;

        public SubjectsViewHolder(@NonNull View itemView) {
            super(itemView);

            subjectName=itemView.findViewById(R.id.tv_subject_name);
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
        subjectListRecyclerView.setAdapter(myStudentAdapter);
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
