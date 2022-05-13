package com.app.cureace.activity;

import static androidx.core.view.GravityCompat.START;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.cureace.R;
import com.app.cureace.activity.bedbooking.BedListActivity;
import com.app.cureace.activity.bedbooking.MyBedBookingActivity;
import com.app.cureace.activity.doctor.DoctorListActivity;
import com.app.cureace.activity.doctor.MyAppointmentsActivity;
import com.app.cureace.utils.AppUtil;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    ImageView drawerIndicator;

    public DrawerLayout drawer;
    protected FrameLayout mDrawerLayout, actionBar;
     private NavigationView navigationView;
    private RelativeLayout drawerHead;
    private TextView nameTV;


    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    SharedPreferences sharedPref;

    public FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    public DatabaseReference databaseReference ;

RelativeLayout c_doc_btn,bed_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().hide();

        drawerIndicator = findViewById(R.id.drawer_indicator);
        actionBar = findViewById(R.id.actionBar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        c_doc_btn = findViewById(R.id.c_doc_btn);
        bed_btn = findViewById(R.id.bed_btn);


        sharedPref = getSharedPreferences(AppUtil.PREFS, 0);

       prepareNavList();
        setUpDrawer();


        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();



        c_doc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               AppUtil.startActivity(HomeActivity.this, DoctorListActivity.class);
            }
        });

        bed_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               AppUtil.startActivity(HomeActivity.this, BedListActivity.class);
            }
        });
    }



    private void setUpDrawer() {

        //HEADER
        View headerLayout = navigationView.getHeaderView(0);
        nameTV = headerLayout.findViewById(R.id.drawer_userName);
        drawerHead = headerLayout.findViewById(R.id.drawer_head);

        nameTV.setText("Welcome "+sharedPref.getString(AppUtil.USER_NAME,""));
        drawerIndicator.setImageResource(R.drawable.ic_drawer);
        drawerIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(START)) {
                    drawer.closeDrawer(START);
                } else {
                    drawer.openDrawer(START);
                }
            }
        });
    }



    private void prepareNavList() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();




                switch (menuItem.getItemId()) {

                    case R.id.nav_home:
                        Intent dashboard = new Intent(HomeActivity.this, HomeActivity.class);
                        dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(dashboard);
                        drawer.closeDrawer(START);
                        break;

                    case R.id.nav_profile:
                        Intent profile = new Intent(HomeActivity.this, ProfileActivity.class);
                        profile.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(profile);
                        drawer.closeDrawer(START);
                        break;

                    case R.id.nav_my_appointment:
                        Intent add_products = new Intent(HomeActivity.this, MyAppointmentsActivity.class);
                        add_products.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(add_products);
                        drawer.closeDrawer(START);
                        break;


                    case R.id.nav_bed_booking_list:
                        Intent manage_products = new Intent(HomeActivity.this, MyBedBookingActivity.class);
                        manage_products.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(manage_products);
                        drawer.closeDrawer(START);
                        break;
                     /*

                    case R.id.nav_product_ratings:
                        Intent nav_product_ratings = new Intent(HomeActivity.this, RatingListActivity.class);
                        nav_product_ratings.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(nav_product_ratings);
                        drawer.closeDrawer(START);
                        break;

                    case R.id.nav_manage_orders:
                        Intent nav_manage_orders = new Intent(HomeActivity.this, SellerOrdersActivity.class);
                        nav_manage_orders.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(nav_manage_orders);
                        drawer.closeDrawer(START);
                        break;

                    case R.id.nav_repair_requests:
                        Intent nav_repair_requests = new Intent(HomeActivity.this, SellerRepairRequestsActivity.class);
                        nav_repair_requests.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(nav_repair_requests);
                        drawer.closeDrawer(START);
                        break;*/

                    case R.id.nav_logout:
                        mAuth.signOut();
                        SharedPreferences preferences =getSharedPreferences(AppUtil.PREFS, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.apply();
                        Toast.makeText(HomeActivity.this, "Logout Successful ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                        drawer.closeDrawer(START);
                        break;


                }
                return false;
            }
        });
    }

}