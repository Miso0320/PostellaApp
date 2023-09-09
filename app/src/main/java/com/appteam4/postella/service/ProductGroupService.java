package com.appteam4.postella.service;

import android.widget.ImageView;

import com.appteam4.postella.dto.Product;
import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductGroupService {
    @GET("getProductList")
    Call<List<Product>> getProductList();

    static void loadImage(int pg_no, ImageView imageView) {
        String url = NetworkInfo.BASE_URL + "productGroup?pg_no=" + pg_no;
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }


}
