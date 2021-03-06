package com.cse.timetableapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.cse.timetableapp.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    TabLayout tabLayout;
    ViewPager viewPager;
    private MyViewPager adapter;
    LinearLayout linearLayout;
    String val,flag;
    DatabaseReference databaseReference;
    ArrayList<FacultyData> mondayList,tuesdayList,wednesdayList,thursdayList,fridayList,saturdayList;
    ArrayList<StudentData> smondayList,stuesdayList,swednesdayList,sthursdayList,sfridayList,ssaturdayList;
    static final String filename = "remember";
    TextView textView;
    static int workload = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //toolbar.setNavigationIcon(R.drawable.ic_toolbar);


        textView = findViewById(R.id.textViewinToolbar);
        textView.setText("");
        //Receive Arguments
        Intent intent = getIntent();
        val = intent.getStringExtra("text");
        flag = intent.getStringExtra("type");
        if(flag.equalsIgnoreCase("student"))
            toolbar.setTitle("Section : "+val+" TimeTable");
        else{
            toolbar.setTitle("Faculty ID : "+val);
        }

        //arrayLists
        assignValues();

        //GetFacultyDatabase
        if(flag.equals("faculty")){
            workload = 0;
            GetDatabaseValues(val);
        }

        //GetStudentDatabase
        if(flag.equals("student"))
            GetStudentDatabaseValues(val);
    }
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.itemlist,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.care:
                Intent intent = new Intent(getApplicationContext(),Contact.class);
                intent.putExtra("text",val);
                intent.putExtra("type",flag);
                startActivity(intent);
                finish();
                break;
            case R.id.logout:
                //functionality implementation

                SharedPreferences preferences = getApplication().getSharedPreferences(filename, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember","false");
                editor.apply();
                Toast.makeText(getApplicationContext(), "Logging Out...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),StartUpActivity.class));
                finish();
                break;
        }

        return true;
    }

    private void assignValues() {
        //Faculty
        mondayList = new ArrayList<>();
        tuesdayList  = new ArrayList<>();
        wednesdayList = new ArrayList<>();
        thursdayList = new ArrayList<>();
        fridayList = new ArrayList<>();
        saturdayList = new ArrayList<>();


        //Student
        smondayList = new ArrayList<>();
        stuesdayList  = new ArrayList<>();
        swednesdayList = new ArrayList<>();
        sthursdayList = new ArrayList<>();
        sfridayList = new ArrayList<>();
        ssaturdayList = new ArrayList<>();

    }

    //new Modules tobe Addes
    public void newModules(){
        /*
        array = new ArrayList<>();
        rooms = new ArrayList<>();
        ArrayList<String> array;
        ArrayList<String> rooms;
        //Classrooms Free Data
        rooms.add("VPSF-1");
        rooms.add("VPSF-2");
        rooms.add("VPSF-3");
        rooms.add("VPSF-4");
        rooms.add("VPSF-5");
        rooms.add("VPSF-6");
        rooms.add("VPSF-7");
        rooms.add("VPSF-8");
        rooms.add("VPSF-9");
        rooms.add("VPSF-10");
        rooms.add("VPSF-11");
        rooms.add("VPSF-12");

        rooms.add("VPTF-1");
        rooms.add("VPTF-2");
        rooms.add("VPTF-3");
        rooms.add("VPTF-4");
        rooms.add("VPTF-5");
        rooms.add("VPTF-6");
        rooms.add("VPTF-7");
        rooms.add("VPTF-8");
        rooms.add("VPTF-9");
        rooms.add("VPTF-10");

        databaseReference = FirebaseDatabase.getInstance().getReference("ClassDetails");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap:snapshot.getChildren()){
                    StudentData studentData = snap.child("Friday").child("3").getValue(StudentData.class);
                    Log.e(studentData.getSec(),studentData.getRoom());
                    rooms.remove(studentData.getRoom());

                }

                Log.e("Free Rooms",rooms.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        //Faculty Free Data
        databaseReference = FirebaseDatabase.getInstance().getReference("FacultyDetails");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap:snapshot.getChildren()){
                    DataSnapshot s = snap.child("Monday");
                    if(!s.hasChild("3"))
                        array.add(snap.getKey());

                }
                Log.e("vals",array.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }

    //FacultyDatabase
    public void GetDatabaseValues(String val){
        databaseReference = FirebaseDatabase.getInstance().getReference("FacultyDetails").child(val);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap:snapshot.getChildren()){
                    //Log.e("Hello","hello");
                    if(!snap.getKey().equals("Details")) {
                        for(DataSnapshot snapper:snap.getChildren()){

                            FacultyData data = snapper.getValue(FacultyData.class);
                            workload++;
                            if (snap.getKey().equals("Monday")) {
                                mondayList.add(data);
                                //Log.e("Data Added", mondayList.toString());
                            } else if (snap.getKey().equals("Tuesday")) {
                                tuesdayList.add(data);
                                //Log.e("Data Added", tuesdayList.toString());
                            } else if (snap.getKey().equals("Wednesday")) {
                                wednesdayList.add(data);
                                //Log.e("Data Added", wednesdayList.toString());
                            } else if (snap.getKey().equals("Thursday")) {
                                thursdayList.add(data);
                                //Log.e("Data Added", thursdayList.toString());
                            } else if (snap.getKey().equals("Friday")) {
                                fridayList.add(data);
                                //Log.e("Data Added", fridayList.toString());
                            } else if (snap.getKey().equals("Saturday")) {
                                saturdayList.add(data);
                                //Log.e("Data Added", saturdayList.toString());
                            }

                        }

                    }
                }
                printReceivedData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void printReceivedData(){
        Monday monday = new Monday();
        Bundle mondaybundle = new Bundle();
        mondaybundle.putParcelableArrayList("faculty",mondayList);
        monday.setArguments(mondaybundle);

        Tuesday tuesday = new Tuesday();
        Bundle tuesdaybundle = new Bundle();
        tuesdaybundle.putParcelableArrayList("faculty",tuesdayList);
        tuesday.setArguments(tuesdaybundle);

        Wednesday wednesday = new Wednesday();
        Bundle wednesdaybundle = new Bundle();
        wednesdaybundle.putParcelableArrayList("faculty",wednesdayList);
        wednesday.setArguments(wednesdaybundle);


        Thursday thursday = new Thursday();
        Bundle thursdaybundle = new Bundle();
        thursdaybundle.putParcelableArrayList("faculty",thursdayList);
        thursday.setArguments(thursdaybundle);


        Friday friday = new Friday();
        Bundle fridaybundle = new Bundle();
        fridaybundle.putParcelableArrayList("faculty",fridayList);
        friday.setArguments(fridaybundle);


        Saturday saturday = new Saturday();
        Bundle saturdaybundle = new Bundle();
        saturdaybundle.putParcelableArrayList("faculty",saturdayList);
        saturday.setArguments(saturdaybundle);


        tabLayout = findViewById(R.id.TestTabLayout);
        viewPager = findViewById(R.id.ViewPager);
        adapter = new MyViewPager(getSupportFragmentManager());

        adapter.AddFragment(monday,"Monday");
        adapter.AddFragment(tuesday,"Tuesday");
        adapter.AddFragment(wednesday,"wednesday");
        adapter.AddFragment(thursday,"Thursday");
        adapter.AddFragment(friday,"Friday");
        adapter.AddFragment(saturday,"Saturday");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        textView.setText("Work Load :"+workload);
        if(workload>=20){
            textView.setTextColor(Color.parseColor("#FF0000"));
        }

    }






    //StudentDatabase
    public void GetStudentDatabaseValues(String val){
        databaseReference = FirebaseDatabase.getInstance().getReference("ClassDetails").child(val);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap:snapshot.getChildren()){
                    //Log.e("Ide Key",snap.getKey());
                    if(!snap.getKey().equals("Details")){
                        for(DataSnapshot snapper:snap.getChildren()){
                            StudentData studentData = snapper.getValue(StudentData.class);
                            if(snap.getKey().equals("Monday")){
                                smondayList.add(studentData);
                                Log.e("Data Added",mondayList.toString());
                            }
                            else if(snap.getKey().equals("Tuesday")){
                                stuesdayList.add(studentData);
                                Log.e("Data Added",tuesdayList.toString());
                            }
                            else if(snap.getKey().equals("Wednesday")){
                                swednesdayList.add(studentData);
                                Log.e("Data Added",wednesdayList.toString());
                            }
                            else if(snap.getKey().equals("Thursday")){
                                sthursdayList.add(studentData);
                                Log.e("Data Added",thursdayList.toString());
                            }
                            else if(snap.getKey().equals("Friday")){
                                sfridayList.add(studentData);
                                Log.e("Data Added",fridayList.toString());
                            }
                            else if(snap.getKey().equals("Saturday")){
                                ssaturdayList.add(studentData);
                                Log.e("Data Added",saturdayList.toString());
                            }



                        }}
                }
                printStudentReceivedData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void printStudentReceivedData(){


        StudentMonday monday = new StudentMonday();
        Bundle mondaybundle = new Bundle();
        mondaybundle.putParcelableArrayList("student",smondayList);
        monday.setArguments(mondaybundle);

        StudentTuesday tuesday = new StudentTuesday();
        Bundle tuesdaybundle = new Bundle();
        tuesdaybundle.putParcelableArrayList("student",stuesdayList);
        tuesday.setArguments(tuesdaybundle);

        StudentWednesday wednesday = new StudentWednesday();
        Bundle wednesdaybundle = new Bundle();
        wednesdaybundle.putParcelableArrayList("student",swednesdayList);
        wednesday.setArguments(wednesdaybundle);


        StudentThursday thursday = new StudentThursday();
        Bundle thursdaybundle = new Bundle();
        thursdaybundle.putParcelableArrayList("student",sthursdayList);
        thursday.setArguments(thursdaybundle);


        StudentFriday friday = new StudentFriday();
        Bundle fridaybundle = new Bundle();
        fridaybundle.putParcelableArrayList("student",sfridayList);
        friday.setArguments(fridaybundle);


        StudentSaturday saturday = new StudentSaturday();
        Bundle saturdaybundle = new Bundle();
        saturdaybundle.putParcelableArrayList("student",ssaturdayList);
        saturday.setArguments(saturdaybundle);


        tabLayout = findViewById(R.id.TestTabLayout);
        viewPager = findViewById(R.id.ViewPager);
        adapter = new MyViewPager(getSupportFragmentManager());

        adapter.AddFragment(monday,"Monday");
        adapter.AddFragment(tuesday,"Tuesday");
        adapter.AddFragment(wednesday,"wednesday");
        adapter.AddFragment(thursday,"Thursday");
        adapter.AddFragment(friday,"Friday");
        adapter.AddFragment(saturday,"Saturday");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);



    }

    @Override
    public void onBackPressed() {

        SharedPreferences preferences = getApplication().getSharedPreferences(filename, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("remember","false");
        editor.apply();
        Toast.makeText(getApplicationContext(), "Logging Out...", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),StartUpActivity.class));
        finish();

    }
}