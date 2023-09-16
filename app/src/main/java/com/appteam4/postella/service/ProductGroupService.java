package com.appteam4.postella.service;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.appteam4.postella.dto.Product;
import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
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
            @Nullable @Query("message") String message
    );
    @GET
    static void loadImage(int pg_no, ImageView imageView) {
        String url = NetworkInfo.BASE_URL + "productGroup?pg_no=" + pg_no;
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }


}
