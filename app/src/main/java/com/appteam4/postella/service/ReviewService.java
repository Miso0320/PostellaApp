package com.appteam4.postella.service;

import android.widget.ImageView;

import com.appteam4.postella.dto.MyWish;
import com.appteam4.postella.dto.Product;
import com.appteam4.postella.dto.Review;
import com.appteam4.postella.dto.WishResult;
import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ReviewService {
    @GET("getReviewsForApp")
    Call<List<Review>> getReviewsForApp(@Query("pg_no") int pg_no, @Query("starRate") int starRate, @Query("kind") int kind);

}
