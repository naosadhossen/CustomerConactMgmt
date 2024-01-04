package com.lut.student.customerconactmgmt;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.GET;

public interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("users/authenticate")
    Call<TokenResponse> loginUser(@Body RequestBody body);
}

