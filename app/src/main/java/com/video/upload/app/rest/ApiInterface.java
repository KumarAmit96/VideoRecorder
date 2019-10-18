package com.video.upload.app.rest;

import com.video.upload.app.CountResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface
{

    @Multipart
    @POST("uploadFile")
    Call<CountResponse> getAgentOfficeList(@Part MultipartBody.Part file);

}
