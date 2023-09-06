package com.appteam4.postella.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

        // 메뉴 초기화
        initMenu();

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

    private void initMenu() {
        MenuProvider menuProvider = new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.nav_menu_top, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.dest_search) {
                    navController.navigate(R.id.action_dest_main_to_dest_search);
                    return true;
                } else if(menuItem.getItemId() == R.id.dest_cart) {
                    navController.navigate(R.id.action_dest_main_to_dest_cart);
                    return true;
                }
                return false;
            }
        };

        getActivity().addMenuProvider(menuProvider, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
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