package com.appteam4.postella.service;

import com.appteam4.postella.dto.Inquiry;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface InquiryService {
    @GET("getQnaListForApp")
    Call<List<Inquiry>> getQnaListForApp(@Query("pg_no")int pg_no);

}
