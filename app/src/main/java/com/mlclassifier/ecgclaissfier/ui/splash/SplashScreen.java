package com.mlclassifier.ecgclaissfier.ui.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.mlclassifier.ecgclaissfier.BaseActivity;
import com.mlclassifier.ecgclaissfier.MainActivity;
import com.mlclassifier.ecgclaissfier.R;
import com.mlclassifier.ecgclaissfier.model.Constants;
import com.mlclassifier.ecgclaissfier.ui.login.LoginActivity;

import static com.mlclassifier.ecgclaissfier.model.Constants.MY_PREFS_NAME;


public class SplashScreen extends BaseActivity {
    private static final String TAG = "SplashScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Log.d(TAG, "onCreate: "+ prefs.getBoolean("isLoggedIn", false));
        prefs.getBoolean(Constants.IS_LOGGED_IN, false);
        prefs.getString(Constants.EMAIL, "email@gmail.com");

        



        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(4000);

                    if(prefs.getBoolean(Constants.IS_LOGGED_IN, false)){
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }



                }catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        thread.start();

    }


}
