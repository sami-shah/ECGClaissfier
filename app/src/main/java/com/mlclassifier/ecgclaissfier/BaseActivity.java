package com.mlclassifier.ecgclaissfier;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.mlclassifier.ecgclaissfier.model.Constants;
import com.mlclassifier.ecgclaissfier.ui.login.LoginActivity;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.mlclassifier.ecgclaissfier.model.Constants.MY_PREFS_NAME;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    Snackbar snackbar;
    ProgressDialog dialog;
    public Long created_at,  updated_at;



    public void showProgressDialogue(String title, String message){
        dialog = new ProgressDialog(BaseActivity.this);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.show();
    }
    public void dissmissProgressDialogue(){
        dialog.dismiss();
    }
    public  void showErrorSnackBar(String dialogue){
        snackbar =Snackbar.make((findViewById(android.R.id.content)), dialogue, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        TextView textView = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);
        view.setBackgroundColor(Color.RED);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }
    public  void showSuccessSnackBar(String dialogue){
        snackbar =Snackbar.make((findViewById(android.R.id.content)), dialogue, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        TextView textView = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);
        view.setBackgroundColor(Color.GREEN);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }
    public boolean checkPasswordType(String passwordS) {
        return passwordS.length() > 7;
    }

    public Long getCreated_at(){
        created_at = getCurrentTimestamp().getTime();
        Log.d(TAG, "getCreated_at: " + created_at);
        return created_at;
    }

    public Long getUpdated_at(){
        updated_at = getCurrentTimestamp().getTime();
        Log.d(TAG, "getUpdatedAt: " + updated_at);
        return updated_at;
    }

    public static Timestamp getCurrentTimestamp(){
        return new Timestamp(System.currentTimeMillis());
    }
    public static Date getDate(String dateString){
        String myFormat = "dd/MM/yyyy"; //In which you need put here

        SimpleDateFormat formatter = new SimpleDateFormat(myFormat);
        try {
            java.util.Date dateUtil = formatter.parse(dateString);
            Date date = new Date(dateUtil.getTime());
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String UnixToHuman(String publish_date){
        // ***** Convert Time *****
        long unixSeconds = Long.valueOf(publish_date);
        java.util.Date date = new java.util.Date(unixSeconds*1000L); // convert seconds to milliseconds
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy/MM/dd"); // - HH:mm --> The format of your date
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+3:30")); // give a timezone reference for formatting
        String humanDate = sdf.format(date);

        return humanDate;
    }


    //==========================================================================//
    public static String TimeAgo(Long millis){
        // Guide: https://github.com/bancek/android-timeago/blob/master/src/com/lukazakrajsek/timeago/TimeAgo.java
        // ***** Convert Time *****
//        long unixSeconds = Long.valueOf(publish_date);

//        long diff = new java.util.Date().getTime() - (unixSeconds*1000L);
        long diff = new java.util.Date().getTime() - millis;
        String prefix = "";
        String suffix = "ago";

        double seconds = Math.abs(diff) / 1000;
        double minutes = seconds / 60;
        double hours = minutes / 60;
        double days = hours / 24;
        double years = days / 365;

        String words;

        if (seconds < 45) {
            words = Math.round(seconds)+" Seceond";
        } else if (seconds < 90) {
            words = "1 Minute";
        } else if (minutes < 45) {
            words = Math.round(minutes)+" Minutes" ;
        } else if (minutes < 90) {
            words = "1 Hour";
        } else if (hours < 24) {
            words = Math.round(hours)+" Hours";
        } else if (hours < 42) {
            words = "1 Day";
        } else if (days < 30) {
            words = Math.round(days)+" Days";
        } else if (days < 45) {
            words = "1 Month";
        } else if (days < 365) {
            words = Math.round(days / 30) + " Months";
        } else if (years < 1.5) {
            words = "1 Year";
        } else {
            words = Math.round(years) + " Years";
        }

        StringBuilder sb = new StringBuilder();

        if (prefix != null && prefix.length() > 0) {
            sb.append(prefix).append(" ");
        }

        sb.append(words);

        if (suffix != null && suffix.length() > 0) {
            sb.append(" ").append(suffix);
        }

        return sb.toString().trim();
    }

    public void setPermanentLogin(String email){
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(Constants.IS_LOGGED_IN, true);
        editor.putString(Constants.EMAIL, email);
        editor.apply();
    }

}
