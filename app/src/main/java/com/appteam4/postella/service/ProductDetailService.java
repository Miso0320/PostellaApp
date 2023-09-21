package com.appteam4.postella.service;

import android.widget.ImageView;

import com.appteam4.postella.dto.Image;
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

public interface ProductDetailService {
    /* 상품상세 정보 받아오기 */
    @GET("detailViewForApp")
    Call<Product> getDetailView(@Query("pg_no") int pg_no);
    
    /* 상품 썸네일 이미지 리스트 받아오기 */
    @GET("loadProductImage")
    Call<List<Image>> loadProductImage(@Query("pg_no") int pg_no);
    
    /* 상품 썸네일 이미지 리스트 개수 받아오기 */
    @GET("loadProductImageCnt")
    Call<Integer> loadProductImageCnt(@Query("pg_no") int pg_no);
    
    /* 찜목록 추가여부 검사 */
    @GET("checkWishForApp")
    Call<WishResult> checkWishForApp(@Query("pg_no") int pg_no, @Query("us_no") int us_no);

    /* 찜목록 추가하기(하트아이콘) */
    @POST("addWishForApp")
    Call<WishResult> addWish(@Body MyWish wish);

    /* 찜목록 삭제하기(하트아이콘) */
    @POST("removeWishForApp")
    Call<WishResult> removeWish(@Body MyWish wish);
    
    /* 상품상세 이미지 받아오기 */
    @GET
    static void loadDetailImage(int pg_no, ImageView imageView) {
        String url = NetworkInfo.BASE_URL + "loadDetailImage?pg_no=" + pg_no;
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }
}
