package com.appteam4.postella.service;

import android.content.Context;

import com.appteam4.postella.datastore.AppKeyValueStore;
import com.appteam4.postella.ui.MainAdapter;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceProvider {
    private static final String TAG = "ServiceProvider";

    public static Retrofit getRetrofit(Context context) {
        OkHttpClient okHttpClinet = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        // 오리지널 요청 내용을 가지고 있는 요청 객체
                        Request request = chain.request();
                        //공통 파라미터 설정---------------------------------
                        /*HttpUrl httpUrl = request.url().newBuilder()
                                .addQueryParameter("param1", "value1")
                                .addQueryParameter("param2", "value2")
                                .build();*/
                        HttpUrl.Builder httpUrlBuilder = request.url().newBuilder();
                        String mid = AppKeyValueStore.getValue(context, "mid");
                        String mpassword = AppKeyValueStore.getValue(context, "mpassword");
                        if (mid != null && mpassword != null) {
                            //쿼리스트링(mid로 이름을 주었을 경우 게시물 쓰기의 mid와 중복이 됨)
                            //인증 정보로 보낼 때 이름을 UserId로 변경
                            httpUrlBuilder.addQueryParameter("userId", mid);
                            httpUrlBuilder.addQueryParameter("userPassword", mpassword);
                        }
                        HttpUrl newUrl = httpUrlBuilder.build();

                        // 공통 헤더 설정------------------------------------
                        Request updateRequest = request.newBuilder()
                                .url(newUrl)
                                //.addHeader("name1", "value1")
                                //.addHeader("name2", "value2")
                                .build();

                        return chain.proceed(updateRequest);
                    }
                }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkInfo.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClinet)
                .build();
        return retrofit;
    }

    //모든 상품목록
    public static ProductGroupService getProductList(Context context) {
        ProductGroupService productGroupService = getRetrofit(context).create(ProductGroupService.class);

        return productGroupService;
    }

    //필터링 된 상품 목록
    public static ProductGroupService getFilteringProducts(Context context) {
        ProductGroupService productGroupService = getRetrofit(context).create(ProductGroupService.class);

        return productGroupService;
    }

    public static MyPageOrderListService getMyPageOrderList(Context context) {
        MyPageOrderListService myPageOrderListService = getRetrofit(context).create(MyPageOrderListService.class);
        return myPageOrderListService;
    }

    // 장바구니
    public static CartService getCartService(Context context) {
        CartService cartService = getRetrofit(context).create(CartService.class);
        return cartService;
    }

    public static UserService getUserService(Context context) {
        UserService userService = getRetrofit(context).create(UserService.class);
        return userService;
    }

    // 상품상세
    public static ProductDetailService getProductDetailService(Context context) {
        ProductDetailService productDetailService = getRetrofit(context).create(ProductDetailService.class);
        return productDetailService;
    }

    // 상품평
    public static ReviewService getReviewService(Context context) {
        ReviewService reviewService = getRetrofit(context).create(ReviewService.class);
        return reviewService;
    }

    // 상품문의
    public static InquiryService getInquiryService(Context context) {
        InquiryService inquiryService = getRetrofit(context).create(InquiryService.class);
        return inquiryService;
    }

    // 찜목록
    public static WishService getWishService(Context context) {
        WishService wishService = getRetrofit(context).create(WishService.class);
        return wishService;
    }
}
