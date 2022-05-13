package com.app.cureace.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    private EditText email_et, password_et;
    private AppCompatButton login_btn;

    ProgressBar login_progress;


    public FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    public DatabaseReference databaseReference = mDatabase.getReference().child(AppUtil.USERS_TABLE_KEY);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        email_et = findViewById(R.id.email_et);
        password_et = findViewById(R.id.password_et);
        login_progress = findViewById(R.id.login_progress);
        mAuth = FirebaseAuth.getInstance();
       FirebaseAuth.getInstance().signOut();

    }
    public void openSignup(View view) {
        AppUtil.startActivity(LoginActivity.this, SignupActivity.class);
    }

    public void openHome(View view) {

        login_progress.setVisibility(View.VISIBLE);
         mAuth.signInWithEmailAndPassword(email_et.getText().toString(), password_et.getText().toString()).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //    progressbar GONE
                        login_progress.setVisibility(View.GONE);
                        if (task.isSuccessful()) {

                            databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    if (dataSnapshot.exists()) {
                                        UserModel user = dataSnapshot.getValue(UserModel.class);
                                         SharedPreferences.Editor editor = getSharedPreferences(AppUtil.PREFS, MODE_PRIVATE).edit();
                                         editor.putString(AppUtil.USER_ID, FirebaseAuth.getInstance().getCurrentUser().getUid());
                                          editor.putString(AppUtil.USER_NAME, user.getUser_name());
                                          editor.putString(AppUtil.USER_EMAIL, user.getUser_email());
                                          editor.putString(AppUtil.USER_MOBILE, user.getUser_mobile_no());
                                          editor.putString(AppUtil.USER_ADDRESS, user.getUser_address());
                                          editor.putString(AppUtil.USER_ID_PROOF, user.getUser_id_proof());
                                          editor.putBoolean(AppUtil.IS_LOGIN, true);
                                            editor.apply();
                                            Toast.makeText(LoginActivity.this, "Login Successful "  , Toast.LENGTH_SHORT).show();
                                             Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                             startActivity(intent);
                                              finish();


                                    }else {
                                        Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }

                            });




                        } else {

                            //    progressbar GONE
                            login_progress.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }


                });


    }

    public void openForgotPwd(View view) {
        Intent intent = new Intent(LoginActivity.this, ForgotPassswordActivity.class);
        startActivity(intent);

    }
}