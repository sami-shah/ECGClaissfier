package com.mlclassifier.ecgclaissfier.repository;

import android.util.Log;
import android.widget.Toast;

import com.mlclassifier.ecgclaissfier.MainActivity;
import com.mlclassifier.ecgclaissfier.model.EcgResult;
import com.mlclassifier.ecgclaissfier.network_calls.RestApi;
import com.mlclassifier.ecgclaissfier.network_calls.RestApiInterface;
import com.mlclassifier.ecgclaissfier.ui.history.HistoryViewModel;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryRepository {
    private static final String TAG = "HistoryRepository";

    public void retrieveHistories(HistoryViewModel viewModel, String id, MainActivity mainActivity){
        RestApiInterface service = RestApi.getApi();
        Call<List<EcgResult>> successCall = service.getHistory(id);
        successCall.enqueue(new Callback<List<EcgResult>>() {
            @Override
            public void onResponse(Call<List<EcgResult>> call, Response<List<EcgResult>> response) {
                if(response.isSuccessful()){
                    Toast.makeText(mainActivity, "Successfully retrieved", Toast.LENGTH_SHORT).show();
                    viewModel.setHistory(response.body());
                    Log.d(TAG, "onFailure: "+ response.body());
                }else {
                    mainActivity.showErrorSnackBar("Error on retrieve history");
                    Log.d(TAG, "onFailure: "+ response);
                    Log.d(TAG, "onFailure: "+ response.body());
                }
            }

            @Override
            public void onFailure(Call<List<EcgResult>> call, Throwable t) {
                mainActivity.showErrorSnackBar(t.getMessage());
                Log.d(TAG, "onFailure: "+ t.getMessage());
            }
        });

    }


}
