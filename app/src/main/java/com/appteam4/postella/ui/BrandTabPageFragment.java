package com.appteam4.postella.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentBrandTabPageBinding;

public class BrandTabPageFragment extends Fragment {

    private static final String TAG = "brandTabPageFragment";
    private FragmentBrandTabPageBinding binding;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBrandTabPageBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);

        //이미지 클릭이벤트 초기화
        initImgClick();
        return binding.getRoot();
    }

    //클릭한 이미지에 해당하는 브랜드를 번들에 담아 상품목록프레그먼트로 이동
    public void initImgClick(){
        Bundle bundle = new Bundle();
        binding.linearBrandHong.setOnClickListener(v->{
            bundle.putString("prd_category", "HON");
            navController.navigate(R.id.action_dest_category_to_dest_prod_list, bundle);
        });
        binding.linearBrandHong2.setOnClickListener(v->{
            bundle.putString("prd_category", "HON");
            navController.navigate(R.id.action_dest_category_to_dest_prod_list, bundle);
        });
        binding.linearBrandSong.setOnClickListener(v->{
            bundle.putString("prd_category", "FOO");
            navController.navigate(R.id.action_dest_category_to_dest_prod_list, bundle);
        });
        binding.linearBrandSong2.setOnClickListener(v->{
            bundle.putString("prd_category", "FOO");
            navController.navigate(R.id.action_dest_category_to_dest_prod_list, bundle);
        });
        binding.linearBrandSmile.setOnClickListener(v->{
            bundle.putString("prd_category", "SMI");
            navController.navigate(R.id.action_dest_category_to_dest_prod_list, bundle);
        });
        binding.linearBrandSmile2.setOnClickListener(v->{
            bundle.putString("prd_category", "SMI");
            navController.navigate(R.id.action_dest_category_to_dest_prod_list, bundle);
        });
        binding.linearBrandYuyu.setOnClickListener(v->{
            bundle.putString("prd_category", "UUU");
            navController.navigate(R.id.action_dest_category_to_dest_prod_list, bundle);
        });
        binding.linearBrandYuyu2.setOnClickListener(v->{
            bundle.putString("prd_category", "UUU");
            navController.navigate(R.id.action_dest_category_to_dest_prod_list, bundle);
        });
        binding.linearBrandGyu.setOnClickListener(v->{
            bundle.putString("prd_category", "GYU");
            navController.navigate(R.id.action_dest_category_to_dest_prod_list, bundle);
        });
        binding.linearBrandGyu.setOnClickListener(v->{
            bundle.putString("prd_category", "GYU");
            navController.navigate(R.id.action_dest_category_to_dest_prod_list, bundle);
        });
    }
}