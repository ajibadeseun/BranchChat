package com.branch.www;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface BranchApiInterface {

    @POST("api/login")
    Call<LoginRes> login(@Body LoginRequest loginRequest);
    @GET("api/messages")
    Call<List<MessagesRes>> getMessages(@Header("X-Branch-Auth-Token")String token);
    @POST("api/messages")
    Call<MessagesRes> sendMessage(@Body CreateMessageRequest messageRequest,
                                  @Header("X-Branch-Auth-Token")String token);
}
