package com.appteam4.postella.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface InquiryService {
    @GET("inquiryForApp")
    Call<String>inquiryForApp(@Query("pg_no")int pg_no);

}
