package com.app.cureace.activity.doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.cureace.R;
import com.app.cureace.adapter.DoctorListAdapter;
import com.app.cureace.adapter.MyAppointmentsAdapter;
import com.app.cureace.model.AppointmentModel;
import com.app.cureace.model.DoctorModel;
import com.app.cureace.utils.AppUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyAppointmentsActivity extends AppCompatActivity {
    public FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    public DatabaseReference databaseReference = mDatabase.getReference().child(AppUtil.APPOINTMENTS_TABLE_KEY);

    List<AppointmentModel> userModelList;

    RecyclerView recyclerView;
    ProgressBar progressBar;
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointments);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("My Appointments List");
        sharedPref = getSharedPreferences(AppUtil.PREFS, 0);

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
        databaseReference.child(sharedPref.getString(AppUtil.USER_ID,"")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {


                    progressBar.setVisibility(View.GONE);

                    for (DataSnapshot ds: dataSnapshot.getChildren())
                    {



                        AppointmentModel appointmentModel=new AppointmentModel();
                        appointmentModel.setId(ds.child("id").getValue().toString());
                        appointmentModel.setAppointment_id(ds.child("appointment_id").getValue().toString());
                        appointmentModel.setUser_id(ds.child("user_id").getValue().toString());
                        appointmentModel.setUser_email(ds.child("user_email").getValue().toString());
                        appointmentModel.setUser_mobile_no(ds.child("user_mobile_no").getValue().toString());
                        appointmentModel.setUser_address(ds.child("user_address").getValue().toString());
                        appointmentModel.setUser_id_proof(ds.child("user_id_proof").getValue().toString());
                        appointmentModel.setDoctor_id(ds.child("doctor_id").getValue().toString());
                        appointmentModel.setDoctor_name(ds.child("doctor_name").getValue().toString());
                        appointmentModel.setExpertise(ds.child("expertise").getValue().toString());
                        appointmentModel.setHospital_name(ds.child("hospital_name").getValue().toString());
                        appointmentModel.setHospital_address(ds.child("hospital_address").getValue().toString());
                        appointmentModel.setSelected_date(ds.child("selected_date").getValue().toString());
                        appointmentModel.setSelected_time(ds.child("selected_time").getValue().toString());
                        appointmentModel.setDate_and_time(ds.child("date_and_time").getValue().toString());
                        appointmentModel.setExtra_information(ds.child("extra_information").getValue().toString());
                        appointmentModel.setDoctor_id(ds.child("user_name").getValue().toString());
                        appointmentModel.setDoctor_mobile_no(ds.child("doctor_mobile_no").getValue().toString());
                        appointmentModel.setDoctor_email(ds.child("doctor_email").getValue().toString());
                        appointmentModel.setAppointment_fee(ds.child("appointment_fee").getValue().toString());
                        appointmentModel.setPayment_id(ds.child("payment_id").getValue().toString());
                        userModelList.add(appointmentModel);

                        Collections.reverse(userModelList);
                        MyAppointmentsAdapter adapter = new MyAppointmentsAdapter(userModelList,MyAppointmentsActivity.this);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MyAppointmentsActivity.this));
                        recyclerView.setAdapter(adapter);
                    }



                }else
                {
                    Toast.makeText(MyAppointmentsActivity.this, "No List Found.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyAppointmentsActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });




    }
}