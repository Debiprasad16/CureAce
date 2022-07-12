package com.app.cureace.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.cureace.R;
import com.app.cureace.model.UserModel;
import com.app.cureace.utils.AppUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG ="tag" ;
    EditText name_et,email_et,mobile_et,address_et,idproof_et;


    ProgressBar signUp_progress;
    public FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    public DatabaseReference databaseReference = mDatabase.getReference().child("users");
    AppCompatButton signup_btn,back_btn;
    private FirebaseAuth mAuth;

       SharedPreferences sharedPref;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();
        sharedPref = getSharedPreferences(AppUtil.PREFS, 0);
        mAuth = FirebaseAuth.getInstance();
        name_et=findViewById(R.id.name_et);
        email_et=findViewById(R.id.email_et);
        mobile_et=findViewById(R.id.mobile_et);
        idproof_et=findViewById(R.id.idproof_et);
        address_et=findViewById(R.id.address_et);
        signUp_progress=findViewById(R.id.signUp_progress);
        signup_btn=findViewById(R.id.signup_btn);
        back_btn=findViewById(R.id.back_btn);

        userModel = new UserModel();

        getProfileData();

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateProfile();
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    private void getProfileData() {



        signUp_progress.setVisibility(View.VISIBLE);


        databaseReference.child(sharedPref.getString(AppUtil.USER_ID, "0")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    Log.e("sdsdsd",dataSnapshot.getKey());

                    signUp_progress.setVisibility(View.GONE);


                    UserModel userModel2 = dataSnapshot.getValue(UserModel.class);



                        userModel.setUser_name(userModel2.getUser_name());
                        userModel.setUser_email(userModel2.getUser_email());
                        userModel.setUser_mobile_no(userModel2.getUser_mobile_no());
                        userModel.setUser_id_proof(userModel2.getUser_id_proof());
                        userModel.setUser_address(userModel2.getUser_address());

                        userModel.setUser_id(userModel2.getUser_id());


                        name_et.setText(userModel2.getUser_name());
                        email_et.setText(userModel2.getUser_email());
                         mobile_et.setText(userModel2.getUser_mobile_no());
                        idproof_et.setText(userModel2.getUser_id_proof());
                        address_et.setText(userModel2.getUser_address());



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
signUp_progress.setVisibility(View.GONE);
                Toast.makeText(ProfileActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
            }

        });

    }



    private void updateProfile()
    {

        signUp_progress.setVisibility(View.VISIBLE);

        databaseReference.child(userModel.getUser_id()).child("user_name").setValue(name_et.getText().toString());
        databaseReference.child(userModel.getUser_id()).child("user_mobile_no").setValue(mobile_et.getText().toString().trim());
        databaseReference.child(userModel.getUser_id()).child("user_id_proof").setValue(idproof_et.getText().toString().trim());


        databaseReference.child(userModel.getUser_id()).child("user_address").setValue(address_et.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    signUp_progress.setVisibility(View.GONE);

                    Toast.makeText(ProfileActivity.this, "Profile Updated Successfully.", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = getSharedPreferences(AppUtil.PREFS, MODE_PRIVATE).edit();
                    editor.putString(AppUtil.USER_NAME, name_et.getText().toString());
                    editor.apply();

                }
            }


        });

    }
}