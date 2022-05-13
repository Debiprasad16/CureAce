package com.app.cureace.activity.bedbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.app.cureace.R;
import com.app.cureace.model.AppointmentModel;
import com.app.cureace.model.BedBookingModel;
import com.app.cureace.utils.AppUtil;

public class MyBedBookingDetailsActivity extends AppCompatActivity {
    public TextView appointmentid_tv,name_tv,expertise_tv,h_name_tv,email_tv,mobile_tv,working_days_tv,date_tv;
    SharedPreferences sharedPref;

    BedBookingModel bedBookingModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bed_booking_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPref = getSharedPreferences(AppUtil.PREFS, 0);

        bedBookingModel= (BedBookingModel) getIntent().getSerializableExtra("data");
        appointmentid_tv =  findViewById(R.id.appointmentid_tv);
        name_tv =  findViewById(R.id.name_tv);
        expertise_tv =findViewById(R.id.expertise_tv);
        h_name_tv = findViewById(R.id.h_name_tv);
        email_tv = findViewById(R.id.email_tv);
        mobile_tv = findViewById(R.id.mobile_tv);
        date_tv = findViewById(R.id.date_tv);

        getSupportActionBar().setTitle(bedBookingModel.getBed_booking_id());
        appointmentid_tv.setText("Id :" + bedBookingModel.getBed_booking_id());
        name_tv.setText("Hospital Name : "+bedBookingModel.getHospital_name());
        expertise_tv.setText("Address :" + bedBookingModel.getHospital_address());
        h_name_tv.setText("Email :  "+bedBookingModel.getHospital_email()+ "\nMobile No.: " +bedBookingModel.getHospital_mobile_no());
        email_tv.setText("Bed Cost : " +bedBookingModel.getBed_cost()+"/bed");
        mobile_tv.setText("Start Date : " +bedBookingModel.getStart_date());
        date_tv.setText("No. Of days: " +bedBookingModel.getNo_of_days());

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