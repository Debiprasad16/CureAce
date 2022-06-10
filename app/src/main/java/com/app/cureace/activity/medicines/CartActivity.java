package com.app.cureace.activity.medicines;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.cureace.R;
import com.app.cureace.activity.ambulance.AmbulanceDetailsActivity;
import com.app.cureace.adapter.CartAdapter;
import com.app.cureace.adapter.MedicinesAdapter;
import com.app.cureace.model.AmbulanceBookingModel;
import com.app.cureace.model.MedicineCartModel;
import com.app.cureace.model.MedicineCheckoutModel;
import com.app.cureace.model.MedicineModel;
import com.app.cureace.utils.AppUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements PaymentResultListener {
    public FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    public DatabaseReference databaseReference = mDatabase.getReference().child(AppUtil.MEDICINES_CART_TABLE_KEY);

    List<MedicineCartModel> medicineModelList;

    RecyclerView recyclerView;
    ProgressBar progressBar;
    SharedPreferences sharedPref;
    public static double total_price=0;
    public static TextView total_tv;
    AppCompatButton book_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Cart List");
        sharedPref = getSharedPreferences(AppUtil.PREFS, 0);
        medicineModelList=new ArrayList<>();
        progressBar=findViewById(R.id.progressBar);
        recyclerView=findViewById(R.id.recyclerView);
        total_tv=findViewById(R.id.total_tv);
        book_btn=findViewById(R.id.book_btn);

        getAmbulanceData();
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void getAmbulanceData() {

        progressBar.setVisibility(View.VISIBLE);
        databaseReference.child(sharedPref.getString(AppUtil.USER_ID,"")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {


                    progressBar.setVisibility(View.GONE);

                    for (DataSnapshot ds: dataSnapshot.getChildren())
                    {


                        MedicineCartModel medicineModel=ds.getValue(MedicineCartModel.class);

                        medicineModelList.add(medicineModel);
                        CartAdapter adapter = new CartAdapter(medicineModelList,  CartActivity.this);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this));
                        recyclerView.setAdapter(adapter);
                    }



                }else
                {
                    total_tv.setVisibility(View.GONE);
                    book_btn.setVisibility(View.GONE);
                    Toast.makeText(CartActivity.this, "No List Found.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CartActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });




    }

    public void checkoutAllCart(View view) {
        makePayment();
    }

    private void checkout(String payment_id)
    {


          DatabaseReference databaseReference2 = mDatabase.getReference().child(AppUtil.MEDICINES_BOOKING_TABLE_KEY);

        String bed_booking_list_id=databaseReference2.push().getKey();
        MedicineCheckoutModel medicineCheckoutModel =new MedicineCheckoutModel();

        medicineCheckoutModel.setId(bed_booking_list_id);

        medicineCheckoutModel.setBooking_id(AppUtil.createOrderId());

        medicineCheckoutModel.setM_total_price(String.valueOf(total_price));
        //user
        medicineCheckoutModel.setUser_name(sharedPref.getString(AppUtil.USER_NAME,""));
        medicineCheckoutModel.setUser_email(sharedPref.getString(AppUtil.USER_EMAIL,""));
        medicineCheckoutModel.setUser_address(sharedPref.getString(AppUtil.USER_ADDRESS,""));
        medicineCheckoutModel.setUser_mobile_no(sharedPref.getString(AppUtil.USER_MOBILE,""));
        medicineCheckoutModel.setUser_id_proof(sharedPref.getString(AppUtil.USER_ID_PROOF,""));
        medicineCheckoutModel.setUser_id(sharedPref.getString(AppUtil.USER_ID,""));

        //doctor
        medicineCheckoutModel.setCartModelList(medicineModelList);


        //other

        medicineCheckoutModel.setDate_time(AppUtil.getCurrentDate()+" " + AppUtil.getCurrentTime());

        medicineCheckoutModel.setPayment_id(payment_id);

        progressBar.setVisibility(View.VISIBLE);

        databaseReference2.child(sharedPref.getString(AppUtil.USER_ID,"")).child(bed_booking_list_id).setValue(medicineCheckoutModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful())
                {

                    databaseReference.child(sharedPref.getString(AppUtil.USER_ID,"")).removeValue();
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(CartActivity.this, "Order Placed! Please check my orders  section.", Toast.LENGTH_LONG).show();
                    onBackPressed();
                }else
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(CartActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(CartActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void makePayment() {

        final Activity activity = this;

        final Checkout co = new Checkout();



        co.setKeyID(getResources().getString(R.string.api_key));
        int amount = Math.round(Float.parseFloat(String.valueOf(total_price)) * 100);
        try {
            JSONObject options = new JSONObject();
            options.put("name", "Medicine Cart");
            options.put("description", "");
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

        checkout(s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failed due to error : " + s, Toast.LENGTH_SHORT).show();
    }
}