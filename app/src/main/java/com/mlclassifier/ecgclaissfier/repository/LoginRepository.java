package com.mlclassifier.ecgclaissfier.repository;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.mlclassifier.ecgclaissfier.MainActivity;
import com.mlclassifier.ecgclaissfier.model.Constants;
import com.mlclassifier.ecgclaissfier.model.Success;
import com.mlclassifier.ecgclaissfier.model.User;
import com.mlclassifier.ecgclaissfier.network_calls.RestApi;
import com.mlclassifier.ecgclaissfier.network_calls.RestApiInterface;
import com.mlclassifier.ecgclaissfier.ui.login.LoginActivity;
import com.mlclassifier.ecgclaissfier.ui.registration.RegistrationActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.mlclassifier.ecgclaissfier.model.Constants.MY_PREFS_NAME;

public class LoginRepository {
    private static final String TAG = "RegistrationRepository";

    public void loginUser(String email, String pass, LoginActivity login){
        RestApiInterface service = RestApi.getApi();
        Call<List<User>> successCall = service.login(pass, email);

        successCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){
                        login.dissmissProgressDialogue();
                    Log.d(TAG, "onResponse: " + response.body());
                    Log.d(TAG, "onResponse: " + response.body().get(0).getSuccess());
                    if(response.body().get(0).getSuccess() == null){
                        setPermanentLogin(login, email, response.body().get(0).getId());
                        login.showSuccessSnackBar("Successfully login");
                        Intent intent = new Intent(login, MainActivity.class);
                        login.startActivity(intent);
                        Toast.makeText(login, Constants.LOGGED_IN, Toast.LENGTH_SHORT).show();
                    }else {
                        login.showErrorSnackBar("Login failed " + response.body().get(0).getSuccess());
                        Log.d(TAG, "onResponse: "+ response.body().get(0).getSuccess());
                    }

                }else {
                    login.dissmissProgressDialogue();
                    login.showErrorSnackBar("Login failed" + response.toString());
//                    Log.d(TAG, "onResponse: "+ response.body());

                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                login.dissmissProgressDialogue();
                login.showErrorSnackBar("Login failed" + t.getMessage());
                Log.d(TAG, "onFailure: "+ t.getMessage());

            }
        });

    }

    void setPermanentLogin(LoginActivity loginActivity, String email, String id){
        SharedPreferences.Editor editor = loginActivity.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(Constants.IS_LOGGED_IN, true);
        editor.putString(Constants.EMAIL, email);
        editor.putString(Constants.ID, id);
        editor.apply();
    }




}
