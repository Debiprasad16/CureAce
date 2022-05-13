package com.app.cureace.activity.bedbooking;

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
import com.app.cureace.activity.doctor.MyAppointmentsActivity;
import com.app.cureace.adapter.BedBookingAdapter;
import com.app.cureace.adapter.MyAppointmentsAdapter;
import com.app.cureace.model.AppointmentModel;
import com.app.cureace.model.BedBookingModel;
import com.app.cureace.utils.AppUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyBedBookingActivity extends AppCompatActivity {

    public FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    public DatabaseReference databaseReference = mDatabase.getReference().child(AppUtil.BED_BOOKING_TABLE_KEY);

    List<BedBookingModel> userModelList;

    RecyclerView recyclerView;
    ProgressBar progressBar;
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bed_booking);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("My BedBooking List");
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



                        BedBookingModel bedBookingModel=new BedBookingModel();
                        bedBookingModel.setId(ds.child("id").getValue().toString());
                        bedBookingModel.setBed_booking_id(ds.child("bed_booking_id").getValue().toString());
                        bedBookingModel.setUser_id(ds.child("user_id").getValue().toString());
                        bedBookingModel.setUser_email(ds.child("user_email").getValue().toString());
                        bedBookingModel.setUser_mobile_no(ds.child("user_mobile_no").getValue().toString());
                        bedBookingModel.setUser_address(ds.child("user_address").getValue().toString());
                        bedBookingModel.setUser_id_proof(ds.child("user_id_proof").getValue().toString());
                        bedBookingModel.setBed_id(ds.child("bed_id").getValue().toString());
                        bedBookingModel.setHospital_name(ds.child("hospital_name").getValue().toString());
                        bedBookingModel.setHospital_address(ds.child("hospital_address").getValue().toString());
                        bedBookingModel.setDate_and_time(ds.child("date_and_time").getValue().toString());
                        bedBookingModel.setNo_of_days(ds.child("no_of_days").getValue().toString());
                        bedBookingModel.setHospital_mobile_no(ds.child("hospital_mobile_no").getValue().toString());
                        bedBookingModel.setHospital_email(ds.child("hospital_email").getValue().toString());
                        bedBookingModel.setBed_cost(ds.child("bed_cost").getValue().toString());
                        bedBookingModel.setPayment_id(ds.child("payment_id").getValue().toString());
                        bedBookingModel.setStart_date(ds.child("start_date").getValue().toString());
                        userModelList.add(bedBookingModel);

                        Collections.reverse(userModelList);
                        BedBookingAdapter adapter = new BedBookingAdapter(userModelList, MyBedBookingActivity.this);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MyBedBookingActivity.this));
                        recyclerView.setAdapter(adapter);
                    }



                }else
                {
                    Toast.makeText(MyBedBookingActivity.this, "No List Found.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyBedBookingActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });




    }

}