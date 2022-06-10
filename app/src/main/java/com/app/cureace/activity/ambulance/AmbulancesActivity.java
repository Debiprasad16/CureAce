package com.app.cureace.activity.ambulance;

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
import com.app.cureace.activity.bedbooking.BedListActivity;
import com.app.cureace.adapter.AmbulancesAdapter;
import com.app.cureace.adapter.BedListAdapter;
import com.app.cureace.model.AmbulanceModel;
import com.app.cureace.model.BedModel;
import com.app.cureace.utils.AppUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AmbulancesActivity extends AppCompatActivity {
    public FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    public DatabaseReference databaseReference = mDatabase.getReference().child(AppUtil.AMBULANCES_TABLE_KEY);

    List<AmbulanceModel> ambulanceModelList;

    RecyclerView recyclerView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulances);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Ambulance List");

        ambulanceModelList=new ArrayList<>();
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


                        AmbulanceModel ambulanceModel=new AmbulanceModel();
                        ambulanceModel.setId(ds.child("id").getValue().toString());
                        ambulanceModel.setCost(ds.child("cost").getValue().toString());
                        ambulanceModel.setAmbulance_number(ds.child("ambulance_number").getValue().toString());
                        ambulanceModel.setMobile_no(ds.child("mobile_no").getValue().toString());
                        ambulanceModel.setHospital_name(ds.child("hospital_name").getValue().toString());
                        ambulanceModel.setAddress(ds.child("address").getValue().toString());
                        ambulanceModelList.add(ambulanceModel);
                        AmbulancesAdapter adapter = new AmbulancesAdapter(ambulanceModelList,  AmbulancesActivity.this);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(AmbulancesActivity.this));
                        recyclerView.setAdapter(adapter);
                    }



                }else
                {
                    Toast.makeText(AmbulancesActivity.this, "No List Found.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AmbulancesActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });




    }
}