package com.mlclassifier.ecgclaissfier.ui.registration;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;

import com.mlclassifier.ecgclaissfier.MainActivity;
import com.mlclassifier.ecgclaissfier.R;
import com.mlclassifier.ecgclaissfier.model.User;
import com.mlclassifier.ecgclaissfier.repository.RegistrationRepository;

import java.util.UUID;

public class RegistrationViewModel extends AndroidViewModel {
    Application application;
    RegistrationRepository registrationRepository;



    public RegistrationViewModel(Application application) {
        super(application);

        this.application = application;
        registrationRepository = new RegistrationRepository();
    }


    public void registeredUser(User user, RegistrationActivity context){

        context.showProgressDialogue(context.getString(R.string.loading), context.getString(R.string.txt_login_loading));
        String uid = UUID.randomUUID().toString();
        user.setId(uid);
        registrationRepository.registeredUser(user, context);
    }

}
