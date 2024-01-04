package com.lut.student.customerconactmgmt;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.GET;
public interface GetProfile {
    @Headers("Content-Type: application/json")
    @GET("users/profile")
    Call<ProfileResponse> getProfileService(@Header("Authorization") String authToken);

}
