package com.appteam4.postella.service;

import android.widget.ImageView;

import com.appteam4.postella.dto.Cart;
import com.appteam4.postella.dto.Product;
import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CartService {

    @GET("cart/getCartList")
    Call<List<Cart>>getCartList(@Query("us_no") int us_no);

    @FormUrlEncoded
    @POST("cart/updateCart")
    Call<Void> updateCart(
            @Field("crt_qty") int crt_qty,
            @Field("us_no") int us_no,
            @Field("prd_no") int prd_no
    );

    @GET
    static void loadImage(int prd_no, ImageView imageView) {
        String url = NetworkInfo.BASE_URL + "cart/getThmbnailImg?prd_no=" + prd_no;
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

}
