package com.app.cureace.activity.ambulance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.app.cureace.R;
import com.app.cureace.model.AmbulanceBookingModel;
import com.app.cureace.model.BedBookingModel;
import com.app.cureace.utils.AppUtil;

public class MyAmbulanceBookingDetailsActivity extends AppCompatActivity {
    public TextView appointmentid_tv,name_tv,expertise_tv,h_name_tv,email_tv,mobile_tv,working_days_tv,date_tv;
    SharedPreferences sharedPref;

    AmbulanceBookingModel ambulanceBookingModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ambulance_booking_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPref = getSharedPreferences(AppUtil.PREFS, 0);

        ambulanceBookingModel= (AmbulanceBookingModel) getIntent().getSerializableExtra("data");
        appointmentid_tv =  findViewById(R.id.appointmentid_tv);
        name_tv =  findViewById(R.id.name_tv);
        expertise_tv =findViewById(R.id.expertise_tv);
        h_name_tv = findViewById(R.id.h_name_tv);
        email_tv = findViewById(R.id.email_tv);
        mobile_tv = findViewById(R.id.mobile_tv);
        date_tv = findViewById(R.id.date_tv);

        getSupportActionBar().setTitle(ambulanceBookingModel.getAmbulance_booking_id());
        appointmentid_tv.setText("Id :" + ambulanceBookingModel.getAmbulance_booking_id());
        name_tv.setText("Ambulance Number : "+ambulanceBookingModel.getAmbulance_number());
        expertise_tv.setText("Name :" + ambulanceBookingModel.getHospital_name());
        h_name_tv.setText("Address :  "+ambulanceBookingModel.getHospital_address());
        email_tv.setText("Cost : " +ambulanceBookingModel.getAmbulance_cost()+" Rs");
        mobile_tv.setText("Mobile No. : " +ambulanceBookingModel.getHospital_mobile_no());
        date_tv.setText("date: " +ambulanceBookingModel.getDate_and_time());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}