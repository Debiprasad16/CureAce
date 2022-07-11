package com.app.cureace.activity.ambulance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.cureace.R;
import com.app.cureace.activity.bedbooking.BedDetailsActivity;
import com.app.cureace.model.AmbulanceBookingModel;
import com.app.cureace.model.AmbulanceModel;
import com.app.cureace.model.BedBookingModel;
import com.app.cureace.model.BedModel;
import com.app.cureace.utils.AppUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.Locale;

public class AmbulanceDetailsActivity extends AppCompatActivity implements PaymentResultListener {
    public TextView name_tv,h_name_tv,email_tv,mobile_tv,working_days_tv;

    AmbulanceModel ambulanceModel;
    SharedPreferences sharedPref;

    ProgressBar progressBar;
    public FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    public DatabaseReference databaseReference = mDatabase.getReference().child(AppUtil.AMBULANCE_BOOKING_TABLE_KEY);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name_tv =  findViewById(R.id.name_tv);
         h_name_tv = findViewById(R.id.h_name_tv);
        email_tv = findViewById(R.id.email_tv);
        mobile_tv = findViewById(R.id.mobile_tv);
        progressBar = findViewById(R.id.progressBar);
        working_days_tv = findViewById(R.id.working_days_tv);
        sharedPref = getSharedPreferences(AppUtil.PREFS, 0);
         ambulanceModel = (AmbulanceModel) getIntent().getSerializableExtra("data");

        name_tv.setText(ambulanceModel.getHospital_name());
        h_name_tv.setText("Ambulance number : "+ambulanceModel.getAmbulance_number());
        email_tv.setText("Address : " +ambulanceModel.getAddress());
        mobile_tv.setText("Mobile No: " +ambulanceModel.getMobile_no());
        working_days_tv.setText("Cost: ₹" +ambulanceModel.getCost());



    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openMaps(View view) {
        String map = "http://maps.google.co.in/maps?q=" + ambulanceModel.getAddress();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
         startActivity(intent);
    }

    public void bookAmbulance(View view) {
       makePayment();


    }

    private void saveAmbulanceBooking(String payment_id)
    {


        String bed_booking_list_id=databaseReference.push().getKey();
        AmbulanceBookingModel ambulanceBookingModel =new AmbulanceBookingModel();

        ambulanceBookingModel.setId(bed_booking_list_id);

        ambulanceBookingModel.setAmbulance_booking_id(AppUtil.createOrderId());
        //user
        ambulanceBookingModel.setUser_name(sharedPref.getString(AppUtil.USER_NAME,""));
        ambulanceBookingModel.setUser_email(sharedPref.getString(AppUtil.USER_EMAIL,""));
        ambulanceBookingModel.setUser_address(sharedPref.getString(AppUtil.USER_ADDRESS,""));
        ambulanceBookingModel.setUser_mobile_no(sharedPref.getString(AppUtil.USER_MOBILE,""));
        ambulanceBookingModel.setUser_id_proof(sharedPref.getString(AppUtil.USER_ID_PROOF,""));
        ambulanceBookingModel.setUser_id(sharedPref.getString(AppUtil.USER_ID,""));

        //doctor
        ambulanceBookingModel.setAmbulance_id(ambulanceModel.getId());
        ambulanceBookingModel.setAmbulance_number(ambulanceModel.getAmbulance_number());
         ambulanceBookingModel.setHospital_mobile_no(ambulanceModel.getMobile_no());
        ambulanceBookingModel.setHospital_name(ambulanceModel.getHospital_name());
        ambulanceBookingModel.setHospital_address(ambulanceModel.getAddress());
        ambulanceBookingModel.setAmbulance_cost(ambulanceModel.getCost());


        //other

        ambulanceBookingModel.setDate_and_time(AppUtil.getCurrentDate()+" " + AppUtil.getCurrentTime());

        ambulanceBookingModel.setPayment_id(payment_id);

        progressBar.setVisibility(View.VISIBLE);

        databaseReference.child(sharedPref.getString(AppUtil.USER_ID,"")).child(bed_booking_list_id).setValue(ambulanceBookingModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful())
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(AmbulanceDetailsActivity.this, "Your ambulance has been booked successfully! Please check my Ambulance Booking section.", Toast.LENGTH_LONG).show();
                    onBackPressed();
                }else
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(AmbulanceDetailsActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(AmbulanceDetailsActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();

            }
        });

    }




    public void makePayment() {

        final Activity activity = this;

        final Checkout co = new Checkout();

        int cost=Integer.parseInt(ambulanceModel.getCost());

        co.setKeyID(getResources().getString(R.string.api_key));
        int amount = Math.round(Float.parseFloat(String.valueOf(cost)) * 100);
        try {
            JSONObject options = new JSONObject();
            options.put("name", ambulanceModel.getHospital_name());
            options.put("description", ambulanceModel.getAmbulance_number() + " \n " +ambulanceModel.getAddress());
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

        saveAmbulanceBooking(s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failed due to error : " + s, Toast.LENGTH_SHORT).show();
    }
}