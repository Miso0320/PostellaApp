package com.appteam4.postella.service;

import android.widget.ImageView;

import com.appteam4.postella.dto.Cart;
import com.appteam4.postella.dto.Product;
import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CartService {
    @GET("getCartListForApp")
    Call<List<Cart>> getCartList();

    @GET("getCartsForApp")
    Call<List<Cart>>getCarts(@Query("pg_no")int pg_no);
    @GET
    static void loadImage(int cartNo, ImageView imageView) {
        String url = NetworkInfo.BASE_URL + "getCarts?cartNo=" + cartNo;
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }
}
