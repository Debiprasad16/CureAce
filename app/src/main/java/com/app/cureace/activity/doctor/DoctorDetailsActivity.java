package com.app.cureace.activity.doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.cureace.R;
import com.app.cureace.model.AppointmentModel;
import com.app.cureace.model.DoctorModel;
import com.app.cureace.utils.AppUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class DoctorDetailsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, PaymentResultListener {
    public TextView name_tv,expertise_tv,h_name_tv,email_tv,mobile_tv,working_days_tv,date_tv;
    DoctorModel doctorModel;
    AppCompatButton date_btn,time_btn,appointment_btn;
    EditText extra_et;
    private TimePickerDialog tpd;
    StringBuilder sb=new StringBuilder();

    ProgressBar progressBar;
    public FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    public DatabaseReference databaseReference = mDatabase.getReference().child(AppUtil.APPOINTMENTS_TABLE_KEY);

    String selected_date="";
    String selected_time="";
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPref = getSharedPreferences(AppUtil.PREFS, 0);

        name_tv =  findViewById(R.id.name_tv);
        expertise_tv =findViewById(R.id.expertise_tv);
        h_name_tv = findViewById(R.id.h_name_tv);
        email_tv = findViewById(R.id.email_tv);
        mobile_tv = findViewById(R.id.mobile_tv);
        working_days_tv = findViewById(R.id.working_days_tv);

        date_btn = findViewById(R.id.date_btn);
        time_btn = findViewById(R.id.time_btn);
        appointment_btn = findViewById(R.id.appointment_btn);
        date_tv = findViewById(R.id.date_tv);
        extra_et = findViewById(R.id.extra_et);
        progressBar = findViewById(R.id.progressBar);

        doctorModel= (DoctorModel) getIntent().getSerializableExtra("data");


        getSupportActionBar().setTitle(doctorModel.getName());

        name_tv.setText(doctorModel.getName());
        expertise_tv.setText(doctorModel.getExpertise());
        h_name_tv.setText("At "+doctorModel.getHospital_name()+ "\n" +doctorModel.getHospital_address());
        email_tv.setText("Email : " +doctorModel.getEmail());
        mobile_tv.setText("Mobile : " +doctorModel.getMobile_no());
        working_days_tv.setText("Working Days : " +doctorModel.getWorking_days());

        date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        DoctorDetailsActivity.this,
                        now.get(Calendar.YEAR), // Initial year selection
                        now.get(Calendar.MONTH), // Initial month selection
                        now.get(Calendar.DAY_OF_MONTH) // Inital day selection
                );

                dpd.show(getSupportFragmentManager(), "Datepickerdialog");

            }
        });

        time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();

                if (tpd == null) {
                    tpd = TimePickerDialog.newInstance(
                            DoctorDetailsActivity.this,
                            now.get(Calendar.HOUR_OF_DAY),
                            now.get(Calendar.MINUTE), false
                    );
                } else {
                    tpd.initialize(
                            DoctorDetailsActivity.this,
                            now.get(Calendar.HOUR_OF_DAY),
                            now.get(Calendar.MINUTE),
                            now.get(Calendar.SECOND),
                            true
                    );
                }
                tpd.vibrate(true);

                tpd.dismissOnPause(true);

                tpd.setOnCancelListener(dialogInterface -> {
                    Log.d("TimePicker", "Dialog was cancelled");
                    tpd = null;
                });
                tpd.show(getSupportFragmentManager(), "Timepickerdialog");
            }

        });

        appointment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (selected_date.isEmpty() && selected_time.isEmpty())
                {
                    Toast.makeText(DoctorDetailsActivity.this, "Please choose date and time", Toast.LENGTH_SHORT).show();
                }else
                {
                    makePayment();
                    //saveAppointment();
                }



            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;

        selected_date=dayOfMonth+"/"+(monthOfYear+1)+"/"+year;;
        sb.append(date);

        date_tv.setText(sb.toString());
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {


        String time = "time: "+hourOfDay+":"+minute;

        selected_time=hourOfDay+":"+minute;
        sb.append(" and " + time);


        date_tv.setText(sb.toString());

    }

    private void saveAppointment(String payment_id)
    {


        String appointment_id=databaseReference.push().getKey();
         AppointmentModel appointmentModel =new AppointmentModel();

        appointmentModel.setId(appointment_id);

        appointmentModel.setAppointment_id(AppUtil.createOrderId());
        //user
        appointmentModel.setUser_name(sharedPref.getString(AppUtil.USER_NAME,""));
        appointmentModel.setUser_email(sharedPref.getString(AppUtil.USER_EMAIL,""));
        appointmentModel.setUser_address(sharedPref.getString(AppUtil.USER_ADDRESS,""));
        appointmentModel.setUser_mobile_no(sharedPref.getString(AppUtil.USER_MOBILE,""));
        appointmentModel.setUser_id_proof(sharedPref.getString(AppUtil.USER_ID_PROOF,""));
        appointmentModel.setUser_id(sharedPref.getString(AppUtil.USER_ID,""));

        //doctor
        appointmentModel.setDoctor_id(doctorModel.getId());
        appointmentModel.setDoctor_name(doctorModel.getName());
        appointmentModel.setExpertise(doctorModel.getExpertise());
        appointmentModel.setDoctor_email(doctorModel.getEmail());
        appointmentModel.setDoctor_mobile_no(doctorModel.getMobile_no());
        appointmentModel.setHospital_name(doctorModel.getHospital_name());
        appointmentModel.setHospital_address(doctorModel.getHospital_address());
        appointmentModel.setAppointment_fee(doctorModel.getAppointment_fee());


        //other
        appointmentModel.setExtra_information(extra_et.getText().toString());
        appointmentModel.setDate_and_time(AppUtil.getCurrentDate()+" " + AppUtil.getCurrentTime());
        appointmentModel.setSelected_date(selected_date);
        appointmentModel.setSelected_time(selected_time);

        appointmentModel.setPayment_id(payment_id);

        progressBar.setVisibility(View.VISIBLE);

        databaseReference.child(sharedPref.getString(AppUtil.USER_ID,"")).child(appointment_id).setValue(appointmentModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful())
                {
                    //sendEmail(doctorModel.getEmail(),"Appointment "+ appointment_id + "received","Patient Details : " + patientdetails  );
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(DoctorDetailsActivity.this, "Your appointment has been booked successfully! Please check my appointment section.", Toast.LENGTH_LONG).show();
                    onBackPressed();
                }else
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(DoctorDetailsActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(DoctorDetailsActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();

            }
        });

    }




    public void makePayment() {

        final Activity activity = this;

        final Checkout co = new Checkout();


        co.setKeyID(getResources().getString(R.string.api_key));
        int amount = Math.round(Float.parseFloat(String.valueOf(doctorModel.getAppointment_fee())) * 100);
        try {
            JSONObject options = new JSONObject();
            options.put("name", doctorModel.getName());
            options.put("description", doctorModel.getHospital_name() + ", " +doctorModel.getHospital_address());
            options.put("send_sms_hash",true);
            options.put("allow_rotation", true);
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", amount);
            options.put("international", false);

            JSONObject preFill = new JSONObject();
            preFill.put("email",sharedPref.getString(AppUtil.USER_EMAIL,"") );
            preFill.put("contact", sharedPref.getString(AppUtil.USER_MOBILE,""));

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
         Toast.makeText(this, "Payment is successful : " + s, Toast.LENGTH_SHORT).show();

        Log.e("chchchchhcsadsada",s);
        saveAppointment(s);
    }

    @Override
    public void onPaymentError(int i, String s) {
         Toast.makeText(this, "Payment Failed due to error : " + s, Toast.LENGTH_SHORT).show();
    }
}