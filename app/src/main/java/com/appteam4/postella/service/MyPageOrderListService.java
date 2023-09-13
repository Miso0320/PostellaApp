package com.appteam4.postella.service;


import android.widget.ImageView;

import com.appteam4.postella.dto.MyPageOrderList;
import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyPageOrderListService {
    @GET("getMyPageOrderList")
    Call<List<MyPageOrderList>> getMyPageOrderList();
    @GET
    static void loadImage(int orderListNo, ImageView imageView) {
        String url = NetworkInfo.BASE_URL + "myPageOrderList?orderListNo=" + orderListNo;
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

}
