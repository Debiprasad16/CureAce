package com.app.cureace.activity.bedbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
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
import com.app.cureace.activity.doctor.DoctorDetailsActivity;
import com.app.cureace.model.AppointmentModel;
import com.app.cureace.model.BedBookingModel;
import com.app.cureace.model.BedModel;
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

import org.json.JSONObject;

import java.util.Calendar;

public class BedDetailsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,PaymentResultListener {
    public TextView date_tv,name_tv,h_name_tv,email_tv,mobile_tv,working_days_tv;
    BedModel bedModel;
    EditText days_et;
    AppCompatButton start_date_btn,book_btn;
    SharedPreferences sharedPref;

    ProgressBar progressBar;
    public FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    public DatabaseReference databaseReference = mDatabase.getReference().child(AppUtil.BED_BOOKING_TABLE_KEY);

    String start_date="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bed_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name_tv =  findViewById(R.id.name_tv);
        sharedPref = getSharedPreferences(AppUtil.PREFS, 0);
        h_name_tv = findViewById(R.id.h_name_tv);
        email_tv = findViewById(R.id.email_tv);
        mobile_tv = findViewById(R.id.mobile_tv);
        working_days_tv = findViewById(R.id.working_days_tv);
        days_et = findViewById(R.id.days_et);
        start_date_btn = findViewById(R.id.start_date_btn);
        book_btn = findViewById(R.id.book_btn);
        progressBar = findViewById(R.id.progressBar);
        date_tv = findViewById(R.id.date_tv);
        bedModel= (BedModel) getIntent().getSerializableExtra("data");


        getSupportActionBar().setTitle(bedModel.getHospital_name());

        name_tv.setText(bedModel.getHospital_name());
        h_name_tv.setText("Address : "+bedModel.getHospital_address());
        email_tv.setText("Email : " +bedModel.getEmail());
        mobile_tv.setText("Mobile : " +bedModel.getMobile_no());
        working_days_tv.setText("Available Beds: " +bedModel.getAvailable_beds() + "\nBed Cost : ₹" + bedModel.getBed_cost()+"/Day for 1 Bed.");

        start_date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        BedDetailsActivity.this,
                        now.get(Calendar.YEAR), // Initial year selection
                        now.get(Calendar.MONTH), // Initial month selection
                        now.get(Calendar.DAY_OF_MONTH) // Inital day selection
                );

                dpd.show(getSupportFragmentManager(), "Datepickerdialog");

            }
        });
        book_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (start_date.isEmpty())
                    Toast.makeText(BedDetailsActivity.this, "Please select a date", Toast.LENGTH_SHORT).show();
               else if(days_et.getText().toString().isEmpty())
                    days_et.setError("Please enter no. of days like(1,2,3,5,10)");
               else

                  makePayment();
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;

        start_date=dayOfMonth+"/"+(monthOfYear+1)+"/"+year;;


        date_tv.setText(date);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveAppointment(String payment_id)
    {


        String bed_booking_list_id=databaseReference.push().getKey();
        BedBookingModel bedBookingModel =new BedBookingModel();

        bedBookingModel.setId(bed_booking_list_id);

        bedBookingModel.setBed_booking_id(AppUtil.createOrderId());
        //user
        bedBookingModel.setUser_name(sharedPref.getString(AppUtil.USER_NAME,""));
        bedBookingModel.setUser_email(sharedPref.getString(AppUtil.USER_EMAIL,""));
        bedBookingModel.setUser_address(sharedPref.getString(AppUtil.USER_ADDRESS,""));
        bedBookingModel.setUser_mobile_no(sharedPref.getString(AppUtil.USER_MOBILE,""));
        bedBookingModel.setUser_id_proof(sharedPref.getString(AppUtil.USER_ID_PROOF,""));
        bedBookingModel.setUser_id(sharedPref.getString(AppUtil.USER_ID,""));

        //doctor
        bedBookingModel.setBed_id(bedModel.getId());
         bedBookingModel.setHospital_email(bedModel.getEmail());
        bedBookingModel.setHospital_mobile_no(bedModel.getMobile_no());
        bedBookingModel.setHospital_name(bedModel.getHospital_name());
        bedBookingModel.setHospital_address(bedModel.getHospital_address());
        bedBookingModel.setBed_cost(bedModel.getBed_cost());


        //other
        bedBookingModel.setNo_of_days(days_et.getText().toString());
        bedBookingModel.setDate_and_time(AppUtil.getCurrentDate()+" " + AppUtil.getCurrentTime());

        bedBookingModel.setStart_date(start_date);
        bedBookingModel.setPayment_id(payment_id);

        progressBar.setVisibility(View.VISIBLE);

        databaseReference.child(sharedPref.getString(AppUtil.USER_ID,"")).child(bed_booking_list_id).setValue(bedBookingModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful())
                {
                     progressBar.setVisibility(View.GONE);
                    Toast.makeText(BedDetailsActivity.this, "Your bed has been booked successfully! Please check My Bed Booking Section.", Toast.LENGTH_LONG).show();
                    onBackPressed();
                }else
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(BedDetailsActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(BedDetailsActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();

            }
        });

    }




    public void makePayment() {

        final Activity activity = this;

        final Checkout co = new Checkout();

        int cost=Integer.parseInt(bedModel.getBed_cost()) * Integer.parseInt(days_et.getText().toString());

        co.setKeyID(getResources().getString(R.string.api_key));
        int amount = Math.round(Float.parseFloat(String.valueOf(cost)) * 100);
        try {
            JSONObject options = new JSONObject();
            options.put("name", bedModel.getHospital_name());
            options.put("description", bedModel.getHospital_address() + " \n " +bedModel.getEmail());
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

        saveAppointment(s);
    }

    @Override
    public void onPaymentError(int i, String s) {
         Toast.makeText(this, "Payment Failed due to error : " + s, Toast.LENGTH_SHORT).show();
    }


}