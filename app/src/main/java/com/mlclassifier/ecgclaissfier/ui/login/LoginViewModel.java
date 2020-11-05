package com.mlclassifier.ecgclaissfier.ui.login;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;


import com.mlclassifier.ecgclaissfier.R;
import com.mlclassifier.ecgclaissfier.model.User;
import com.mlclassifier.ecgclaissfier.repository.LoginRepository;

import java.util.List;

public class LoginViewModel extends AndroidViewModel {

    LoginRepository loginRepository;

    public LoginViewModel(Application application) {
        super(application);
        this.loginRepository = new LoginRepository();

    }

    public void loginUser(User user, LoginActivity loginActivity){
        loginActivity.showProgressDialogue(loginActivity.getString(R.string.loading), loginActivity.getString(R.string.txt_login_loading));
        loginRepository.loginUser(user.getEmail(), user.getPassword(), loginActivity);
    }

}
