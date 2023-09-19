package com.appteam4.postella.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.appteam4.postella.databinding.FragmentProductDetailImageBinding;
import com.appteam4.postella.dto.Image;
import com.appteam4.postella.service.NetworkInfo;
import com.appteam4.postella.service.ProductDetailService;
import com.appteam4.postella.service.ServiceProvider;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailImageFragment extends Fragment {
    private static final String TAG = "ProductDetailImageFragm";
    private FragmentProductDetailImageBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductDetailImageBinding.inflate(inflater);

        // 페이지 번호 받기
        Bundle bundle = getArguments();
        int pageNo = bundle.getInt("pageNo");
        int pg_no = bundle.getInt("pg_no");

        // 페이지별로 UI 세팅
        initUIByPageNo(pageNo, pg_no);

        return binding.getRoot();
    }

    private void initUIByPageNo(int pageNo, int pg_no) {
        // API 서버에서 썸네일 이미지 목록 받기
        ProductDetailService productDetailService = ServiceProvider.getProductDetailService(getContext());

        Call<List<Image>> call = productDetailService.loadProductImage(pg_no);
        call.enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
                List<Image> images = response.body();
                // 이미지 URL을 저장할 배열
                String[] imageArray = new String[images.size()];

                // 각 이미지를 별도의 ImageView에 로드하기
                for(int i = 0; i < images.size(); i++) {
                    Image image = images.get(i);
                    String imageUrl = NetworkInfo.BASE_URL + image.getImg_url();
                    imageArray[i] = imageUrl;
                }
                
                // 이미지 슬라이더에 이미지 추가
                Glide.with(ProductDetailImageFragment.this).load(imageArray[pageNo]).into(binding.imageSlider);
            }
            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}