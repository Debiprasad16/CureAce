package com.app.cureace.activity.medicines;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.cureace.R;
import com.app.cureace.activity.ambulance.AmbulancesActivity;
import com.app.cureace.adapter.AmbulancesAdapter;
import com.app.cureace.adapter.MedicinesAdapter;
import com.app.cureace.model.AmbulanceModel;
import com.app.cureace.model.MedicineModel;
import com.app.cureace.utils.AppUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MedicineListActivity extends AppCompatActivity {
    public FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    public DatabaseReference databaseReference = mDatabase.getReference().child(AppUtil.MEDICINES_TABLE_KEY);

    List<MedicineModel> medicineModelList;

    RecyclerView recyclerView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Medicines List");

        medicineModelList=new ArrayList<>();
        progressBar=findViewById(R.id.progressBar);
        recyclerView=findViewById(R.id.recyclerView);

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
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {


                    progressBar.setVisibility(View.GONE);

                    for (DataSnapshot ds: dataSnapshot.getChildren())
                    {


                        MedicineModel medicineModel=ds.getValue(MedicineModel.class);

                        medicineModelList.add(medicineModel);
                        MedicinesAdapter adapter = new MedicinesAdapter(medicineModelList,  MedicineListActivity.this);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MedicineListActivity.this));
                        recyclerView.setAdapter(adapter);
                    }



                }else
                {
                    Toast.makeText(MedicineListActivity.this, "No List Found.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MedicineListActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });




    }
}