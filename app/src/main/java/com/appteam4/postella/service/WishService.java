package com.appteam4.postella.service;

import android.widget.ImageView;

import com.appteam4.postella.dto.Image;
import com.appteam4.postella.dto.MyWish;
import com.appteam4.postella.dto.WishResult;
import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WishService {
    /* 찜목록 전체 받아오기 */
    @GET("getWishListForApp")
    Call<List<MyWish>> getWishListForApp(@Query("us_no") int us_no);
    
    /* 찜목록 전체개수 받아오기 */
    @GET("getWishCntForApp")
    Call<Integer> getWishCntForApp(@Query("us_no") int us_no);
    
    /* 찜목록 삭제하기 */
    @POST("removeWishForApp")
    Call<WishResult> removeWish(@Body MyWish wish);
    
    /* 찜목록 상품 썸네일 받아오기 */
    @GET
    static void loadImage(int pg_no, ImageView imageView) {
        String url = NetworkInfo.BASE_URL + "productGroup?pg_no=" + pg_no;
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }
}
