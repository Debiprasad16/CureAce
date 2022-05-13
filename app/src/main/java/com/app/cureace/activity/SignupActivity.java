package com.app.cureace.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.cureace.R;
import com.app.cureace.model.UserModel;
import com.app.cureace.utils.AppUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG ="tag" ;
     EditText name_et,email_et,password_et,mobile_et,address_et,idproof_et;


    ProgressBar signUp_progress;
  public FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
     public DatabaseReference databaseReference = mDatabase.getReference().child("users");
    AppCompatButton signup_btn;
  private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        name_et=findViewById(R.id.name_et);
        email_et=findViewById(R.id.email_et);
        password_et=findViewById(R.id.password_et);
        mobile_et=findViewById(R.id.mobile_et);
        idproof_et=findViewById(R.id.idproof_et);
        address_et=findViewById(R.id.address_et);
         signUp_progress=findViewById(R.id.signUp_progress);
        signup_btn=findViewById(R.id.signup_btn);

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                signupFirebase(name_et.getText().toString(),email_et.getText().toString(),password_et.getText().toString(),mobile_et.getText().toString(),address_et.getText().toString(),idproof_et.getText().toString());

            }
        });
    }
    public void openLogin(View view) {
        AppUtil.startActivity(SignupActivity.this, LoginActivity.class);

    }

    private void signupFirebase(String name,String email,String password,String mob,String address,String idproof)
    {
        UserModel userModel =new UserModel();
        userModel.setUser_name(name);
        userModel.setUser_email(email);
        userModel.setUser_password(password);
        userModel.setUser_address(address);
        userModel.setUser_mobile_no(mob);
        userModel.setUser_id_proof(idproof);


        signUp_progress.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener
                (new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            userModel.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());

                            databaseReference
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(userModel).
                                    addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            signUp_progress.setVisibility(View.GONE);
                                            Toast.makeText(SignupActivity.this, "Signup Successfully", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });


                        } else {
                            //    progressbar GONE
                            signUp_progress.setVisibility(View.GONE);
                            Toast.makeText(SignupActivity.this, "Check Email id or Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}