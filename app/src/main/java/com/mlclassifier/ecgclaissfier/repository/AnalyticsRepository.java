package com.mlclassifier.ecgclaissfier.repository;

import android.util.Log;

import com.mlclassifier.ecgclaissfier.MainActivity;
import com.mlclassifier.ecgclaissfier.model.EcgResult;
import com.mlclassifier.ecgclaissfier.network_calls.RestApi;
import com.mlclassifier.ecgclaissfier.network_calls.RestApiInterface;
import com.mlclassifier.ecgclaissfier.ui.analytics.AnalyticsViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnalyticsRepository {

    private static final String TAG = "AnalyticsRepository";
    public void fetchAnalytics(MainActivity activity, AnalyticsViewModel viewModel){
        activity.showProgressDialogue("Sending image", "Please wait...");
        RestApiInterface service = RestApi.getApi();
        Call<List<EcgResult>> call = service.getStatisticsData();
        call.enqueue(new Callback<List<EcgResult>>() {
            @Override
            public void onResponse(Call<List<EcgResult>> call, Response<List<EcgResult>> response) {
                if(response.isSuccessful()){
                    activity.dissmissProgressDialogue();
                    Log.d(TAG, "onResponse: " + response);
                    Log.d(TAG, "onResponse: " + response.body());
                    viewModel.setListMutableLiveData(response.body());

                }else {
                    activity.dissmissProgressDialogue();
                    activity.showErrorSnackBar("Error in fetching data for analytics");
                    Log.d(TAG, "onResponse: " + response);
                }
            }

            @Override
            public void onFailure(Call<List<EcgResult>> call, Throwable t) {
                activity.dissmissProgressDialogue();
                activity.showErrorSnackBar(t.getMessage());
                Log.d(TAG, "onResponse: " + t.getMessage());
            }
        });

    }

}
