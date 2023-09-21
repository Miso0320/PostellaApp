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
    /* 리뷰목록 전체 받아오기 */
    @GET("getReviewsForApp")
    Call<List<Review>> getReviewsForApp(@Query("pg_no") int pg_no, @Query("starRate") int starRate, @Query("kind") int kind);
    
    /* 리뷰목록 전체개수 받아오기 */
    @GET("getReviewCntForApp")
    Call<Integer> getReviewCntForApp(@Query("pg_no") int pg_no, @Query("starRate") int starRate);
    
    /* 찜목록 추가하기(하트아이콘) */
    @POST("addWishForApp")
    Call<WishResult> addWish(@Body MyWish wish);

    /* 찜목록 삭제하기(하트아이콘) */
    @POST("removeWishForApp")
    Call<WishResult> removeWish(@Body MyWish wish);
}
