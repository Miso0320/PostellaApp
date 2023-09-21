package com.appteam4.postella.service;

import com.appteam4.postella.dto.Image;
import com.appteam4.postella.dto.MyWish;
import com.appteam4.postella.dto.Review;
import com.appteam4.postella.dto.WishResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WishService {
    @GET("getWishListForApp")
    Call<List<MyWish>> getWishListForApp(@Query("us_no") int us_no);

    @GET("loadProductImage")
    Call<List<Image>> loadProductImage(@Query("pg_no") int pg_no);

    @GET("getWishCntForApp")
    Call<Integer> getWishCntForApp(@Query("us_no") int us_no);

}
