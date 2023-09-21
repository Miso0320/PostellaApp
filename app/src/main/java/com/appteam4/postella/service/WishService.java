package com.appteam4.postella.service;

import android.widget.ImageView;

import com.appteam4.postella.dto.Image;
import com.appteam4.postella.dto.MyWish;
import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WishService {
    @GET("getWishListForApp")
    Call<List<MyWish>> getWishListForApp(@Query("us_no") int us_no);

    @GET("loadProductImage")
    Call<List<Image>> loadProductImage(@Query("pg_no") int pg_no);

    @GET("getWishCntForApp")
    Call<Integer> getWishCntForApp(@Query("us_no") int us_no);

    @GET
    static void loadImage(int pg_no, ImageView imageView) {
        String url = NetworkInfo.BASE_URL + "productGroup?pg_no=" + pg_no;
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }
}