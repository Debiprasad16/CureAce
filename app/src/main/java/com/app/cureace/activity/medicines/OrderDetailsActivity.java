package com.app.cureace.activity.medicines;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.app.cureace.R;
import com.app.cureace.adapter.CartAdapter;
import com.app.cureace.adapter.MyOrdersAdapter;
import com.app.cureace.adapter.OrderCartItemsAdapter;
import com.app.cureace.model.MedicineCheckoutModel;
import com.app.cureace.model.MedicineModel;
import com.app.cureace.utils.AppUtil;

public class OrderDetailsActivity extends AppCompatActivity {
    public TextView date_tv,name_tv,h_name_tv,email_tv,mobile_tv,working_days_tv;
    MedicineCheckoutModel medicineModel;
    SharedPreferences sharedPref;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name_tv =  findViewById(R.id.name_tv);
        sharedPref = getSharedPreferences(AppUtil.PREFS, 0);
        h_name_tv = findViewById(R.id.h_name_tv);
        email_tv = findViewById(R.id.email_tv);
        mobile_tv = findViewById(R.id.mobile_tv);
        working_days_tv = findViewById(R.id.working_days_tv);
        recyclerView=findViewById(R.id.recyclerView);
        medicineModel= (MedicineCheckoutModel) getIntent().getSerializableExtra("data");

        name_tv.setText("Order id : "+medicineModel.getBooking_id());
        h_name_tv.setText("User Name : "+medicineModel.getUser_name());
        email_tv.setText("Price : " +medicineModel.getM_total_price()+" Rs");
        mobile_tv.setText("Delivery Address: " +medicineModel.getUser_address());
        working_days_tv.setText("Order Date: " +medicineModel.getDate_time());

        OrderCartItemsAdapter adapter = new OrderCartItemsAdapter(medicineModel.getCartModelList(),  OrderDetailsActivity.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(OrderDetailsActivity.this));
        recyclerView.setAdapter(adapter);

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