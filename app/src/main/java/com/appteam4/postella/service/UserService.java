package com.appteam4.postella.service;

import com.appteam4.postella.dto.LoginResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserService {
    @GET("user/login")
    Call<LoginResult> login(@Query("us_email") String us_email, @Query("us_password") String us_password);
}
