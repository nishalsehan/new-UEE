package com.example.schoolline;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.example.schoolline.Adapters.Common;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Dialog myDialog;

    CardView student,teacher,classroom,payment,subject,result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if(!Common.isConnectedToInternet(this)){
            Toast.makeText(this,"No connection",Toast.LENGTH_LONG).show();

        }

        isNetworkConnected();

        student = findViewById(R.id.student);
        classroom = findViewById(R.id.classroom);
        payment = findViewById(R.id.payment);
        teacher = findViewById(R.id.teacher);
        subject = findViewById(R.id.subject);
        result = findViewById(R.id.result);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_fragment_area, new StudentsFragment());
                fragmentTransaction.addToBackStack(MainActivity.class.getName());
                fragmentTransaction.commit();
            }
        });
        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_fragment_area, new TeacherDashboardActivity());
                fragmentTransaction.addToBackStack(MainActivity.class.getName());
                fragmentTransaction.commit();
            }
        });
        subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_fragment_area, new SubjectsFragment());
                fragmentTransaction.addToBackStack(MainActivity.class.getName());
                fragmentTransaction.commit();
            }
        });

        classroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_fragment_area, new ClassroomDashboardActivity());
                fragmentTransaction.addToBackStack(MainActivity.class.getName());
                fragmentTransaction.commit();
            }
        });

        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_fragment_area, new ResultDashboardActivity());
                fragmentTransaction.addToBackStack(MainActivity.class.getName());
                fragmentTransaction.commit();
            }
        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_fragment_area, new PaymentsExpensesFragment());
                fragmentTransaction.addToBackStack(MainActivity.class.getName());
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            System.out.println(getFragmentManager().getBackStackEntryCount());
            if (getFragmentManager().getBackStackEntryCount() != 0) {
                getFragmentManager().popBackStack();
            } else {
//                TextView quit,cancel;
//                myDialog = new Dialog(this);
//                myDialog.setContentView(R.layout.quit_dialog);
//                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                myDialog.setCancelable(false);
//                cancel = myDialog.findViewById(R.id.no);
//                cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        myDialog.dismiss();
//                    }
//                });
//                quit = myDialog.findViewById(R.id.yes);
//                quit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        MainActivity.super.onBackPressed();
//                    }
//                });
//                myDialog.show();
                System.out.println("back");
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment = null;

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent(this,MainActivity.class));
        } else if (id == R.id.nav_students) {
            fragment = new StudentsFragment();
        } else if (id == R.id.nav_teachers) {
            fragment = new TeacherDashboardActivity();
        } else if (id == R.id.nav_payments) {
            fragment = new PaymentsExpensesFragment();
        } else if (id == R.id.nav_subjects) {
            fragment = new SubjectsFragment();
        } else if (id == R.id.nav_results) {
            fragment = new ResultDashboardActivity();
        } else if (id == R.id.nav_grades) {
            fragment = new GradeDashboard();
        } else if (id == R.id.nav_classrooms) {
            fragment = new ClassroomDashboardActivity();
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_fragment_area, fragment);
            if (id == R.id.nav_students) {
                fragmentTransaction.addToBackStack(StudentsFragment.class.getName());
            } else if (id == R.id.nav_teachers) {
                fragmentTransaction.addToBackStack(TeacherDashboardActivity.class.getName());
            } else if (id == R.id.nav_payments) {

            } else if (id == R.id.nav_subjects) {
                fragmentTransaction.addToBackStack(SubjectsFragment.class.getName());
            } else if (id == R.id.nav_results) {
                fragmentTransaction.addToBackStack(ResultDashboardActivity.class.getName());
            } else if (id == R.id.nav_grades) {
                fragmentTransaction.addToBackStack(GradeDashboard.class.getName());
            } else if (id == R.id.nav_classrooms) {
                fragmentTransaction.addToBackStack(ClassroomDashboardActivity.class.getName());
            }
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
