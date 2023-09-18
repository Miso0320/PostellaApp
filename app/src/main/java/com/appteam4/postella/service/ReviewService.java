package com.appteam4.postella.service;

import android.widget.ImageView;

import com.appteam4.postella.dto.MyWish;
import com.appteam4.postella.dto.WishResult;
import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ReviewService {
    @GET("reviewForApp")
    Call<String>reviewForApp(@Query("pg_no")int pg_no);

}
