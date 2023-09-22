package com.appteam4.postella.service;

import android.widget.ImageView;

import androidx.annotation.Nullable;

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

public interface ProductGroupService {
    @GET("getProductListForApp")
    Call<List<Product>> getProductList();

    @GET("getProductsForApp")
    Call<List<Product>>getProudcts(@Query("pg_no")int pg_no);
    @GET("getFilteringProductsForApp")
    Call<List<Product>> getFilteringProducts(
            @Nullable @Query("keyword") String keyword,
            @Nullable @Query("prd_category") String prd_category,
            @Nullable @Query("brand") String brand,
            @Nullable @Query("message") String message,
            @Nullable @Query("kind") int kind
    );

    @POST("addWishForApp")
    Call<WishResult> addWish(@Body MyWish wish);
    @POST("removeWishForApp")
    Call<WishResult> removeWish(@Body MyWish wish);
    @GET("getWishListForApp")
    Call<List<MyWish>> getWishList(@Query("us_no")int us_no);
    @GET
    static void loadImage(int pg_no, ImageView imageView) {
        String url = NetworkInfo.BASE_URL + "productGroup?pg_no=" + pg_no;
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }


}
