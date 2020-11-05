package com.mlclassifier.ecgclaissfier.repository;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.mlclassifier.ecgclaissfier.MainActivity;
import com.mlclassifier.ecgclaissfier.model.Constants;
import com.mlclassifier.ecgclaissfier.model.EcgResult;
import com.mlclassifier.ecgclaissfier.model.Success;
import com.mlclassifier.ecgclaissfier.network_calls.RestApi;
import com.mlclassifier.ecgclaissfier.network_calls.RestApiInterface;
import com.mlclassifier.ecgclaissfier.ui.home.HomeViewModel;

import java.util.List;
import java.util.UUID;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeRepository {
    private static final String TAG = "HomeRepository";

    public void sendImageToServer(MultipartBody.Part image, String name, String fileType, String id, MainActivity mainActivity, HomeViewModel viewModel){
        mainActivity.showProgressDialogue("Sending image", "Please wait...");
        RestApiInterface service = RestApi.getApi();
        Call<List<EcgResult>> successCall = service.postAttachment(image, UUID.randomUUID().toString(), id,name, fileType);
        successCall.enqueue(new Callback<List<EcgResult>>() {
            @Override
            public void onResponse(Call<List<EcgResult>> call, Response<List<EcgResult>> response) {
                if(response.isSuccessful()){
                    mainActivity.dissmissProgressDialogue();
                    Log.d(TAG, "onResponse: " + response.body());
                    Toast.makeText(mainActivity, "Predicted", Toast.LENGTH_SHORT).show();
                    viewModel.setmPredictedResults(response.body());

                }else {
                    mainActivity.dissmissProgressDialogue();
                    mainActivity.showErrorSnackBar("Login failed" + response.toString());
                    Log.d(TAG, "onFailure: "+ response.toString());

                }
            }

            @Override
            public void onFailure(Call<List<EcgResult>> call, Throwable t) {
                mainActivity.dissmissProgressDialogue();
                mainActivity.showErrorSnackBar("Login failed" + t.getMessage());
                Log.d(TAG, "onFailure: "+ t.toString());

            }
        });
    }
}
