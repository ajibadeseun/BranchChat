package com.branch.www;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.ParseException;
import android.util.Log;
import android.util.Patterns;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilFunctions {
    private Context mContext;

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public void saveToken(LoginRes loginRes, AppCompatActivity appCompatActivity){
        SharedPreferences sharedPreferences = appCompatActivity.getSharedPreferences("Keys", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token",loginRes.getAuth_token());
        editor.apply();
    }
    public String getToken(AppCompatActivity appCompatActivity){
        SharedPreferences sharedPreferences =  appCompatActivity.getSharedPreferences("Keys", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token","");
    }

    public UtilFunctions() {
    }

    public UtilFunctions(Context context) {
        this.mContext = context;
    }

    public boolean isEmailValid(String email) {

        return !Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private static Date currentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public static String getTimeAgo(long date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {



            SimpleDateFormat dateFormatTwo = new SimpleDateFormat("MMMM d, yyyy");

            if (date < 1000000000000L) {
                date *= 1000;
            }

            long now = currentDate().getTime();
            if (date > now || date <= 0) {
                return "in the future";
            }

            final long diff = now - date;
            if (diff < MINUTE_MILLIS) {
                return "moments ago";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "1 min ago";
            } else if (diff < 60 * MINUTE_MILLIS) {
                return String.valueOf(diff / MINUTE_MILLIS).concat(" mins ago");
            } else if (diff < 2 * HOUR_MILLIS) {
                return "1 hour ago";
            } else if (diff < 24 * HOUR_MILLIS) {
                return String.valueOf(diff / HOUR_MILLIS).concat(" hours ago");
            } else if (diff < 48 * HOUR_MILLIS) {
                return "yesterday";
            } else if (diff < 7 * 24 * HOUR_MILLIS) {
                return String.valueOf(diff / DAY_MILLIS).concat(" days ago");
            } else {
                return String.valueOf(dateFormatTwo.format(date));
            }
        } catch (ParseException e) {
            return null;

        }

    }


    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return !matcher.matches();

    }


}
