package com.appteam4.postella.service;

import com.appteam4.postella.dto.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductGroupService {
    @GET("getProductList")
    Call<List<Product>> getProductList();

}
