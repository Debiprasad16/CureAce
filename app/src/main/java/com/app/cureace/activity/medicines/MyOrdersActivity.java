package com.app.cureace.activity.medicines;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.cureace.R;
import com.app.cureace.adapter.CartAdapter;
import com.app.cureace.adapter.MyOrdersAdapter;
import com.app.cureace.model.MedicineCartModel;
import com.app.cureace.model.MedicineCheckoutModel;
import com.app.cureace.utils.AppUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyOrdersActivity extends AppCompatActivity {

    public FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    public DatabaseReference databaseReference = mDatabase.getReference().child(AppUtil.MEDICINES_BOOKING_TABLE_KEY);

    List<MedicineCheckoutModel> medicineModelList;

    RecyclerView recyclerView;
    ProgressBar progressBar;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("My Orders");
        sharedPref = getSharedPreferences(AppUtil.PREFS, 0);
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
        databaseReference.child(sharedPref.getString(AppUtil.USER_ID,"")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {


                    progressBar.setVisibility(View.GONE);

                    for (DataSnapshot ds: dataSnapshot.getChildren())
                    {


                        MedicineCheckoutModel medicineModel=ds.getValue(MedicineCheckoutModel.class);

                        medicineModelList.add(medicineModel);
                        MyOrdersAdapter adapter = new MyOrdersAdapter(medicineModelList,  MyOrdersActivity.this);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MyOrdersActivity.this));
                        recyclerView.setAdapter(adapter);
                    }



                }else
                {
                    Toast.makeText(MyOrdersActivity.this, "No List Found.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyOrdersActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });




    }
}