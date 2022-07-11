package com.app.cureace.activity.doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.app.cureace.R;

public class DoctorCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Doctors Category");
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void openDentist(View view) {
        Intent intent = new Intent(DoctorCategoryActivity.this, DoctorListActivity.class);
        intent.putExtra("category","Dentists");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
         startActivity(intent);
    }

    public void openCardio(View view) {
        Intent intent = new Intent(DoctorCategoryActivity.this, DoctorListActivity.class);
        intent.putExtra("category","Cardiologist");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void openGyna(View view) {
        Intent intent = new Intent(DoctorCategoryActivity.this, DoctorListActivity.class);
        intent.putExtra("category","Gynecologist");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void openPsy(View view) {
        Intent intent = new Intent(DoctorCategoryActivity.this, DoctorListActivity.class);
        intent.putExtra("category","Psychiatrist");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void openRadio(View view) {
        Intent intent = new Intent(DoctorCategoryActivity.this, DoctorListActivity.class);
        intent.putExtra("category","Radiologist");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void openNeu(View view) {
        Intent intent = new Intent(DoctorCategoryActivity.this, DoctorListActivity.class);
        intent.putExtra("category","Neurologist");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    public void openDer(View view) {
        Intent intent = new Intent(DoctorCategoryActivity.this, DoctorListActivity.class);
        intent.putExtra("category","Dermatologist");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void openNeph(View view) {
        Intent intent = new Intent(DoctorCategoryActivity.this, DoctorListActivity.class);
        intent.putExtra("category","Nephrologist");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void openEndo(View view) {
        Intent intent = new Intent(DoctorCategoryActivity.this, DoctorListActivity.class);
        intent.putExtra("category","Endocrinologist");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}