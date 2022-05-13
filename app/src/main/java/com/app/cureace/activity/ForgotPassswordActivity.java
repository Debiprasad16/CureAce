package com.app.cureace.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.app.cureace.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassswordActivity extends AppCompatActivity {
    EditText email_et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_passsword);
        email_et=findViewById(R.id.email_et);
    }

    public void sendForgotPwsReq(View view) {

        if (TextUtils.isEmpty(email_et.getText().toString()))
        {
            Toast.makeText(this, "Enter email address.", Toast.LENGTH_SHORT).show();
        }else {
            FirebaseAuth auth = FirebaseAuth.getInstance();

            auth.sendPasswordResetEmail(email_et.getText().toString().trim())
                    .addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgotPassswordActivity.this, "Email sent to your registered email address.", Toast.LENGTH_SHORT).show();
                             } else {
                                 Toast.makeText(ForgotPassswordActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}