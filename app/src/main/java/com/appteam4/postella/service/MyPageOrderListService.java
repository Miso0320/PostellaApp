package com.appteam4.postella.service;


import android.widget.ImageView;

import com.appteam4.postella.dto.MyPageOrderList;
import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyPageOrderListService {
    @GET("mypage/getMyPageOrderList")
    Call<List<MyPageOrderList>> getMyPageOrderList(@Query("us_no") int us_no);

    @GET
    static void loadImage(int prd_no, ImageView imageView) {
        String url = NetworkInfo.BASE_URL + "mypage/getThmbnailImg?prd_no=" + prd_no;
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

}
