package com.app.cureace.activity.medicines;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.cureace.R;
import com.app.cureace.model.MedicineCartModel;
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
import com.squareup.picasso.Picasso;

public class MedicinesDetailsActivity extends AppCompatActivity {
    public TextView date_tv,name_tv,h_name_tv,email_tv,mobile_tv,working_days_tv;
    MedicineModel medicineModel;
    EditText quantity_et;
    AppCompatButton start_date_btn,book_btn;
    SharedPreferences sharedPref;
    ImageView imageView;

    ProgressBar progressBar;
    public FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    public DatabaseReference databaseReference = mDatabase.getReference().child(AppUtil.MEDICINES_CART_TABLE_KEY);

    String start_date="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicines_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name_tv =  findViewById(R.id.name_tv);
        sharedPref = getSharedPreferences(AppUtil.PREFS, 0);
        h_name_tv = findViewById(R.id.h_name_tv);
        email_tv = findViewById(R.id.email_tv);
        mobile_tv = findViewById(R.id.mobile_tv);
        imageView = findViewById(R.id.imageView);
        working_days_tv = findViewById(R.id.working_days_tv);
        quantity_et = findViewById(R.id.quantity_et);
        start_date_btn = findViewById(R.id.start_date_btn);
        book_btn = findViewById(R.id.book_btn);
        progressBar = findViewById(R.id.progressBar);
        date_tv = findViewById(R.id.date_tv);
        medicineModel= (MedicineModel) getIntent().getSerializableExtra("data");


        getSupportActionBar().setTitle(medicineModel.getM_title());

        name_tv.setText(medicineModel.getM_title());
        h_name_tv.setText("Description : "+medicineModel.getM_description());
        email_tv.setText("Price : " +medicineModel.getM_price()+" Rs");
        mobile_tv.setText("Mfg Date : " +medicineModel.getM_mf_date());
        working_days_tv.setText("Expiry Date: " +medicineModel.getM_exp_date());



        Picasso.with(MedicinesDetailsActivity.this).load(medicineModel.getM_image()).into(imageView);


        book_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity_et.getText().toString().isEmpty())
                    Toast.makeText(MedicinesDetailsActivity.this, "please enter quantity", Toast.LENGTH_SHORT).show();
                else
                    addToCart( quantity_et.getText().toString());
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

    private void addToCart(String s)
    {


        String bed_booking_list_id=databaseReference.push().getKey();
        MedicineCartModel medicineCartModel =new MedicineCartModel();

        medicineCartModel.setId(bed_booking_list_id);

        medicineCartModel.setM_booking_id(AppUtil.createOrderId());
        //user


        //medi
        medicineCartModel.setM_id(medicineModel.getM_id());
        medicineCartModel.setM_title(medicineModel.getM_title());
        medicineCartModel.setM_image(medicineModel.getM_image());
        medicineCartModel.setM_description(medicineModel.getM_description());
        medicineCartModel.setM_price(medicineModel.getM_price());
        medicineCartModel.setM_mf_date(medicineModel.getM_mf_date());
        medicineCartModel.setM_exp_date(medicineModel.getM_exp_date());
        medicineCartModel.setM_quantity(s);

        double subtotal= Double.parseDouble(medicineModel.getM_price()) *  Double.parseDouble(s);

        medicineCartModel.setM_sub_total(String.valueOf(subtotal));


        //other
       // medicineCartModel.setDate_and_time(AppUtil.getCurrentDate()+" " + AppUtil.getCurrentTime());


        progressBar.setVisibility(View.VISIBLE);


        databaseReference.child(sharedPref.getString(AppUtil.USER_ID,"")).orderByChild("m_id").equalTo(medicineModel.getM_id()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists())
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MedicinesDetailsActivity.this, "Product already added in cart.", Toast.LENGTH_SHORT).show();
                }else
                {

                    databaseReference.child(sharedPref.getString(AppUtil.USER_ID,"")).child(bed_booking_list_id).setValue(medicineCartModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(MedicinesDetailsActivity.this, "product added to cart! Please check my cart section.", Toast.LENGTH_LONG).show();
                                onBackPressed();
                            }else
                            {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(MedicinesDetailsActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                            }


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(MedicinesDetailsActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();

                        }
                    });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MedicinesDetailsActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void decreaseQuant(View view) {
        int count= Integer.parseInt(String.valueOf(quantity_et.getText()));
        if (count == 1) {
            quantity_et.setText("1");
        } else {
            count -= 1;
            quantity_et.setText("" + count);
        }
    }

    public void increaseQuant(View view) {
        int count= Integer.parseInt(quantity_et.getText().toString());
        count++;
        quantity_et.setText("" + count);
    }
}