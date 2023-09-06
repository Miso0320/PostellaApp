package com.appteam4.postella.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {

    private static final String TAG = "MainFragment";
    private FragmentMainBinding binding;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);

        initBtnLogin();
        initBtnMyPage();
        initBtnSearch();
        initBtnCategory();
        initBtnWishList();
        initBtnAdList();
        initBtnProdDetail();
        initBtnCart();

        return binding.getRoot();
    }

    private void initBtnLogin() {
        binding.btnLogin.setOnClickListener(v -> {
            navController.navigate(R.id.action_dest_main_to_dest_login);
        });
    }

    private void initBtnMyPage() {
        binding.btnMypage.setOnClickListener(v -> {
            navController.navigate(R.id.action_dest_main_to_dest_mypage_order_list);
        });
    }

    private void initBtnSearch() {
        binding.btnSearch.setOnClickListener(v -> {
            navController.navigate(R.id.action_dest_main_to_dest_search);
        });
    }

    private void initBtnCategory() {
        binding.btnCategory.setOnClickListener(v -> {
            navController.navigate(R.id.action_dest_main_to_dest_category);
        });
    }

    private void initBtnWishList() {
        binding.btnWishList.setOnClickListener(v -> {
            navController.navigate(R.id.action_dest_main_to_dest_wish_list);
        });
    }

    private void initBtnAdList() {
        binding.btnAdList.setOnClickListener(v -> {
            navController.navigate(R.id.action_dest_main_to_dest_ad_list);
        });
    }

    private void initBtnProdDetail() {
        binding.btnProdDetail.setOnClickListener(v -> {
            navController.navigate(R.id.action_dest_main_to_dest_prod_detail);
        });
    }

    private void initBtnCart() {
        binding.btnCart.setOnClickListener(v -> {
            navController.navigate(R.id.action_dest_main_to_dest_cart);
        });
    }
}