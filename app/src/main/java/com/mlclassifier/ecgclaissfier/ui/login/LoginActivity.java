package com.mlclassifier.ecgclaissfier.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import com.mlclassifier.ecgclaissfier.BaseActivity;
import com.mlclassifier.ecgclaissfier.MainActivity;
import com.mlclassifier.ecgclaissfier.R;
import com.mlclassifier.ecgclaissfier.model.Constants;
import com.mlclassifier.ecgclaissfier.model.User;
import com.mlclassifier.ecgclaissfier.ui.registration.RegistrationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
    private LoginViewModel loginViewModel;
    private Unbinder unbinder;
    private SharedPreferences sp;

    @BindView(R.id.username)
    EditText userName;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.registrationRoute)
    TextView registrationRoute;

    @BindView(R.id.ipAddress)
    EditText ipAddress;

    @BindView(R.id.loginSubmit)
    Button loginSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(unbinder == null){
            unbinder = ButterKnife.bind(this);
        }
        loginViewModel =
                ViewModelProviders.of(this).get(LoginViewModel.class);

        sp = getSharedPreferences(Constants.KEY_SHARED_PREFS, Context.MODE_PRIVATE);

        String savedIp = sp.getString(getString(R.string.ip_address), null);
        if(savedIp != null)
            ipAddress.setText(savedIp);
    }





    @OnClick(R.id.registrationRoute)
    public void RegistrationRoute(){
        startActivity(new Intent(this, RegistrationActivity.class));
    }



    @OnClick(R.id.loginSubmit)
    public void login(){

        // Update the IP
        String ip = ipAddress.getText().toString();
        if(!ip.isEmpty()) {
            Constants.URL_IMAGES = Constants.URL_IMAGES.replace("X", ip);
            Constants.REST_API = Constants.REST_API.replace("X", ip);
            sp.edit().putString(getString(R.string.ip_address), ip).apply();
        }

        String userName, password;
        userName = this.userName.getText().toString();
        password = this.password.getText().toString();
        if(userName.isEmpty()){
            this.userName.setError(getString(R.string.txt_username_error));
            this.userName.requestFocus();
        }else if(password.isEmpty()){
            this.password.setError(getString(R.string.txt_password_error));
            this.password.requestFocus();
//        }else if(!checkPasswordType(password)){
//            this.password.setError(getString(R.string.txt_password_error_length));
//            this.password.requestFocus();
        } else{
            User usser = new User();
            usser.setEmail(userName);
            usser.setPassword(password);
            loginViewModel.loginUser(usser, LoginActivity.this);


        }

    }
}
