package com.mlclassifier.ecgclaissfier.network_calls;

import com.mlclassifier.ecgclaissfier.model.EcgResult;
import com.mlclassifier.ecgclaissfier.model.Success;
import com.mlclassifier.ecgclaissfier.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RestApiInterface {

    @POST("varify_user")
    @FormUrlEncoded
    Call<Success> registration(
            @Field("id") String id,
            @Field("full_name") String full_name,
            @Field("contact") String contact,
            @Field("password") String password,
            @Field("email") String email
    );


    @POST("login")
    @FormUrlEncoded
    Call<List<User>> login(
            @Field("password") String password,
            @Field("email") String email);

    @Multipart
    @POST("post_image")
    Call<List<EcgResult>> postAttachment(
            @Part MultipartBody.Part attachment,
            @Part("attachment_id") String attachment_id,
            @Part("user_id") String user_id,
            @Part("attachment_name") String attachment_name,
            @Part("attachment_file_type") String attachment_file_type);


    @POST("get_history")
    @FormUrlEncoded
    Call<List<EcgResult>> getHistory(
            @Field("user_id") String user_id

            );


    @GET("statistics_data")
    Call<List<EcgResult>> getStatisticsData();


}
