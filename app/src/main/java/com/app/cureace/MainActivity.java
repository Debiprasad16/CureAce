package com.app.cureace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.app.cureace.activity.HomeActivity;
import com.app.cureace.activity.LoginActivity;
import com.app.cureace.utils.AppUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        SharedPreferences sharedPref = getSharedPreferences(AppUtil.PREFS, 0);
        final boolean is_login  = sharedPref.getBoolean(AppUtil.IS_LOGIN,false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                if (is_login ==false)
                {
                AppUtil.startActivity(MainActivity.this, LoginActivity.class);
                finish();
               }else
                {
                    AppUtil.startActivity(MainActivity.this, HomeActivity.class);
                    finish();
                }


            }
        },3000);
    }
}