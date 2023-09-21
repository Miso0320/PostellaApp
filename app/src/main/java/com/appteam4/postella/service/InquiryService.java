package com.appteam4.postella.service;

import com.appteam4.postella.dto.Inquiry;
import com.appteam4.postella.dto.MyWish;
import com.appteam4.postella.dto.WishResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InquiryService {
    /* 상품문의리스트 전체 받아오기 */
    @GET("getQnaListForApp")
    Call<List<Inquiry>> getQnaListForApp(@Query("pg_no") int pg_no);

    /* 찜목록 추가하기(하트아이콘) */
    @POST("addWishForApp")
    Call<WishResult> addWish(@Body MyWish wish);

    /* 찜목록 삭제하기(하트아이콘) */
    @POST("removeWishForApp")
    Call<WishResult> removeWish(@Body MyWish wish);

}
