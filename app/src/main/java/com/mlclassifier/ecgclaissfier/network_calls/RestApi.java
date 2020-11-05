package com.mlclassifier.ecgclaissfier.network_calls;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mlclassifier.ecgclaissfier.model.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApi {

        public static RestApiInterface getApi(){
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.REST_API)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            RestApiInterface service = retrofit.create(RestApiInterface.class);
            return service;
        }
}
