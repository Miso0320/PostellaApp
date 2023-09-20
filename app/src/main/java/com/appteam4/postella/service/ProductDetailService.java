package com.appteam4.postella.service;

import android.widget.ImageView;

import com.appteam4.postella.dto.Image;
import com.appteam4.postella.dto.MyWish;
import com.appteam4.postella.dto.Product;
import com.appteam4.postella.dto.WishResult;
import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ProductDetailService {
    @GET("detailViewForApp")
    Call<Product> getDetailView(@Query("pg_no") int pg_no);

    @GET("loadProductImage")
    Call<List<Image>> loadProductImage(@Query("pg_no") int pg_no);

    @GET("loadProductImageCnt")
    Call<Integer> loadProductImageCnt(@Query("pg_no") int pg_no);

    @GET("checkWishForApp")
    Call<WishResult> checkWishForApp(@Query("pg_no") int pg_no, @Query("us_no") int us_no);

    @POST("addWishForApp")
    Call<WishResult> addWish(@Body MyWish wish);

    @POST("removeWishForApp")
    Call<WishResult> removeWish(@Body MyWish wish);

    @GET
    static void loadDetailImage(int pg_no, ImageView imageView) {
        String url = NetworkInfo.BASE_URL + "loadDetailImage?pg_no=" + pg_no;
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }
}
