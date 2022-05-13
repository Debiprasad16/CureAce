package com.app.cureace.utils;

import android.content.Context;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class AppUtil {

    public static final String USERS_TABLE_KEY ="users" ;
    public static final String DOCTORS_TABLE_KEY ="doctors" ;
    public static final String BEDS_TABLE_KEY ="beds" ;
    public static final String APPOINTMENTS_TABLE_KEY ="appointments" ; //where store data of user consultants
    public static final String BED_BOOKING_TABLE_KEY ="bed_booking" ; //where store data of user bed booking
    public static final String PREFS = "prefs";
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_MOBILE= "user_mobile";
    public static final String USER_ADDRESS = "user_address";
    public static final String USER_ID_PROOF = "user_id_proof";
    public static final String IS_LOGIN = "is_login";

    public static void startActivity(Context context, Class<?> cls )
    {

        Intent intent = new Intent(context, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);


    }
    public static String getCurrentDate()
    {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
        String formattedDate = df.format(c);

        return formattedDate;
    }

    public static String getCurrentTime()
    {
        return new SimpleDateFormat("hh:mm:ss a", Locale.getDefault()).format(new Date());
    }

    public static String createOrderId(){
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(20);
    }

}
