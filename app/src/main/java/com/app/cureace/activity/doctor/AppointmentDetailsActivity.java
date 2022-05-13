package com.app.cureace.activity.doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.app.cureace.R;
import com.app.cureace.model.AppointmentModel;
import com.app.cureace.utils.AppUtil;

public class AppointmentDetailsActivity extends AppCompatActivity {
    public TextView appointmentid_tv,name_tv,expertise_tv,h_name_tv,email_tv,mobile_tv,working_days_tv,date_tv;
    SharedPreferences sharedPref;

    AppointmentModel appointmentModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPref = getSharedPreferences(AppUtil.PREFS, 0);

        appointmentModel= (AppointmentModel) getIntent().getSerializableExtra("data");
        appointmentid_tv =  findViewById(R.id.appointmentid_tv);
        name_tv =  findViewById(R.id.name_tv);
        expertise_tv =findViewById(R.id.expertise_tv);
        h_name_tv = findViewById(R.id.h_name_tv);
        email_tv = findViewById(R.id.email_tv);
        mobile_tv = findViewById(R.id.mobile_tv);
        date_tv = findViewById(R.id.date_tv);

        getSupportActionBar().setTitle(appointmentModel.getAppointment_id());
        appointmentid_tv.setText("Id :" + appointmentModel.getAppointment_id());
        name_tv.setText("Doctor Name : "+appointmentModel.getDoctor_name());
        expertise_tv.setText("Expertise :" + appointmentModel.getExpertise());
        h_name_tv.setText("at "+appointmentModel.getHospital_name()+ " Hospital, " +appointmentModel.getHospital_address());
        email_tv.setText("email : " +appointmentModel.getDoctor_email());
        mobile_tv.setText("mobile : " +appointmentModel.getDoctor_mobile_no());
        date_tv.setText("Appointment Date : " +appointmentModel.getSelected_date()+" " +appointmentModel.getSelected_time());
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