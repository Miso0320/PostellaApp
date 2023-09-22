package com.appteam4.postella.service;

import android.widget.ImageView;

import com.appteam4.postella.dto.DeliveryAddress;
import com.appteam4.postella.dto.MyWish;
import com.appteam4.postella.dto.WishResult;
import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OrderService {
    /* 기본배송지 정보 받아오기 */
    @GET("selectDeliveryForApp")
    Call<DeliveryAddress> selectDeliveryForApp(@Query("us_no") int us_no);
    
    /* 주문예정목록 썸네일 받아오기 */
    @GET
    static void loadImage(int prd_no, ImageView imageView) {
        String url = NetworkInfo.BASE_URL + "getPrdImgForApp?prd_no=" + prd_no;
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }
}
