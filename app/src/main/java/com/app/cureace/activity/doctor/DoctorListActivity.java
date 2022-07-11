package com.app.cureace.activity.doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.cureace.R;
import com.app.cureace.adapter.DoctorListAdapter;
import com.app.cureace.model.DoctorModel;
import com.app.cureace.utils.AppUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DoctorListActivity extends AppCompatActivity {
    public FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    public DatabaseReference databaseReference = mDatabase.getReference().child(AppUtil.DOCTORS_TABLE_KEY);

    List<DoctorModel> userModelList;

    RecyclerView recyclerView;
    ProgressBar progressBar;
    String category="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Doctors List");

        category=getIntent().getStringExtra("category");
        userModelList=new ArrayList<>();
        progressBar=findViewById(R.id.progressBar);
        recyclerView=findViewById(R.id.recyclerView);

        getUserData();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getUserData() {

        progressBar.setVisibility(View.VISIBLE);
        databaseReference.orderByChild("expertise").equalTo(category).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {


                    progressBar.setVisibility(View.GONE);

                    for (DataSnapshot ds: dataSnapshot.getChildren())
                    {
                        DoctorModel doctorModel=new DoctorModel();
                        doctorModel.setId(ds.child("id").getValue().toString());
                        doctorModel.setName(ds.child("name").getValue().toString());
                        doctorModel.setEmail(ds.child("email").getValue().toString());
                        doctorModel.setMobile_no(ds.child("mobile_no").getValue().toString());
                        doctorModel.setExpertise(ds.child("expertise").getValue().toString());
                        doctorModel.setHospital_name(ds.child("hospital_name").getValue().toString());
                        doctorModel.setHospital_address(ds.child("hospital_address").getValue().toString());
                        doctorModel.setWorking_days(ds.child("working_days").getValue().toString());
                        doctorModel.setAppointment_fee( ds.child("appointment_fee").getValue().toString());
                        userModelList.add(doctorModel);
                        DoctorListAdapter adapter = new DoctorListAdapter(userModelList,DoctorListActivity.this);
                         recyclerView.setHasFixedSize(true);
                         recyclerView.setLayoutManager(new LinearLayoutManager(DoctorListActivity.this));
                         recyclerView.setAdapter(adapter);
                     }



                }else
                {
                    Toast.makeText(DoctorListActivity.this, "No List Found.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DoctorListActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });




    }
}