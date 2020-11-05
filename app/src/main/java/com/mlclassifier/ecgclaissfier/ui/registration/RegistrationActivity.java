package com.mlclassifier.ecgclaissfier.ui.registration;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.lifecycle.ViewModelProviders;

import com.mlclassifier.ecgclaissfier.BaseActivity;
import com.mlclassifier.ecgclaissfier.MainActivity;
import com.mlclassifier.ecgclaissfier.R;
import com.mlclassifier.ecgclaissfier.model.User;
import com.mlclassifier.ecgclaissfier.ui.login.LoginViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RegistrationActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
    private RegistrationViewModel registrationViewModel;
    private Unbinder unbinder;

    @BindView(R.id.fullname)
    EditText fullnameEt;

    @BindView(R.id.phone)
    EditText phoneEt;

    @BindView(R.id.email)
    EditText emailEt;

    @BindView(R.id.password)
    EditText passwordEt;

    @BindView(R.id.registrationSubmit)
    Button registrationSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        if(unbinder == null){
            unbinder = ButterKnife.bind(this);
        }
        registrationViewModel =
                ViewModelProviders.of(this).get(RegistrationViewModel.class);



    }





    @OnClick(R.id.registrationSubmit)
    public void registrationSubmit(){
        String fullname, email, phone, password;
        fullname = this.fullnameEt.getText().toString();
        email = this.emailEt.getText().toString();
        phone = this.phoneEt.getText().toString();
        password = this.passwordEt.getText().toString();
        if(fullname.isEmpty()){
            this.fullnameEt.setError(getString(R.string.txt_username_error));
            this.fullnameEt.requestFocus();
        }else if(email.isEmpty()){
            this.emailEt.setError(getString(R.string.txt_email_error));
            this.emailEt.requestFocus();
        }else if(phone.isEmpty()){
            this.phoneEt.setError(getString(R.string.txt_phone_error));
            this.phoneEt.requestFocus();
        }else if(password.isEmpty()){
            this.passwordEt.setError(getString(R.string.txt_password_error));
            this.passwordEt.requestFocus();
        }else if(!checkPasswordType(password)){
            this.passwordEt.setError(getString(R.string.txt_password_error_length));
            this.passwordEt.requestFocus();
        } else{
            User usser = new User();
            usser.setFull_name(fullname);
            usser.setEmail(email);
            usser.setPassword(password);
            usser.getPhone();
            usser.setLogin(true);
            registrationViewModel.registeredUser(usser, RegistrationActivity.this);
//                Intent intent =  new Intent(this, MainActivity.class);
//                intent.putExtra(getString(R.string.txt_entity),usser);
//                startActivity(intent);
//                dissmissProgressDialogue();
//                finish();


        }

    }
}
