package com.appteam4.postella.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentProductDetailImageBinding;
import com.bumptech.glide.Glide;

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

        // 페이지별로 UI 세팅
        initUIByPageNo(pageNo);

        return binding.getRoot();
    }

    private void initUIByPageNo(int pageNo) {
        String[] images = new String[] {
                "https://cdn.pixabay.com/photo/2019/12/26/10/44/horse-4720178_1280.jpg",
                "https://cdn.pixabay.com/photo/2020/11/04/15/29/coffee-beans-5712780_1280.jpg",
                "https://cdn.pixabay.com/photo/2020/03/08/21/41/landscape-4913841_1280.jpg",
                "https://cdn.pixabay.com/photo/2020/09/02/18/03/girl-5539094_1280.jpg",
                "https://cdn.pixabay.com/photo/2014/03/03/16/15/mosque-279015_1280.jpg"
        };

        //binding.imageSlider.setImageResource(R.drawable.ic_home_24dp);

        // pageNo가 배열을 넘어설 때 처리
        if (pageNo < 0) {
            pageNo = 0;
        } else if (pageNo >= images.length) {
            pageNo = images.length - 1;
        }
        
        Glide.with(this)
                .load(images[pageNo]) // pageNo에 해당하는 이미지 URL 로드
                .into(binding.imageSlider);
    }
}