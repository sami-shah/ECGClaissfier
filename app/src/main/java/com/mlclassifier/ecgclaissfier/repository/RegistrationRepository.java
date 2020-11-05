package com.mlclassifier.ecgclaissfier.repository;

import android.content.Intent;
import android.util.Log;

import com.mlclassifier.ecgclaissfier.MainActivity;
import com.mlclassifier.ecgclaissfier.model.Success;
import com.mlclassifier.ecgclaissfier.model.User;
import com.mlclassifier.ecgclaissfier.network_calls.RestApi;
import com.mlclassifier.ecgclaissfier.network_calls.RestApiInterface;
import com.mlclassifier.ecgclaissfier.ui.registration.RegistrationActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationRepository {
    private static final String TAG = "RegistrationRepository";

    public void registeredUser(User user, RegistrationActivity registrationActivity){
        RestApiInterface service = RestApi.getApi();
        Call<Success> successCall = service.registration(user.getId(), user.getFull_name(), user.getPhone(), user.getPassword(), user.getEmail());

        successCall.enqueue(new Callback<Success>() {
            @Override
            public void onResponse(Call<Success> call, Response<Success> response) {
                if(response.isSuccessful()){
                    registrationActivity.dissmissProgressDialogue();
                    registrationActivity.showSuccessSnackBar(response.body().getSuccess());
                    Intent intent = new Intent(registrationActivity, MainActivity.class);
                    registrationActivity.startActivity(intent);
                    registrationActivity.setPermanentLogin(user.getEmail());

                }else {
                    registrationActivity.dissmissProgressDialogue();
                    registrationActivity.showErrorSnackBar("Login failed" + response.toString());
                }
            }

            @Override
            public void onFailure(Call<Success> call, Throwable t) {
                registrationActivity.dissmissProgressDialogue();
                registrationActivity.showErrorSnackBar("Login failed" + t.getMessage());
                Log.d(TAG, "onFailure: "+ t.getMessage());

            }
        });

    }


}
