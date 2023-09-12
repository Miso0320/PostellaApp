package com.appteam4.postella.ui;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.ActivityMainBinding;
import com.appteam4.postella.databinding.FragmentProdDetailBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ProdDetailFragment extends Fragment {
    private static final String TAG = "ProdDetailFragment";
    private FragmentProdDetailBinding binding;
    NavController navController;

    private TextView textView;
    private boolean isStrikeThrough = false;

    private ViewPager2 viewPager2;
    private LinearLayout linearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProdDetailBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);
        
        // 취소선 추가
        binding.prdOrgPrice.setPaintFlags(binding.prdOrgPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        // 이미지 슬라이더
        initPagerView();

        return binding.getRoot();
    }

    private void initPagerView() {
        ProductDetailAdapter productDetailAdapter = new ProductDetailAdapter(getContext());
        binding.productImageSlider.setAdapter(productDetailAdapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                binding.tabLayout, binding.productImageSlider, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
            }
        });

        tabLayoutMediator.attach();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }



}