package com.appteam4.postella.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentWishListBinding;

public class WishListFragment extends Fragment {

    private static final String TAG = "WishListFragment";
    private FragmentWishListBinding binding;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWishListBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);
        // 주석
        initBtnProdDetail();

        return binding.getRoot();
    }

    private void initBtnProdDetail() {
        binding.btnProdDetail.setOnClickListener(v -> {
            navController.navigate(R.id.action_dest_wish_list_to_dest_prod_detail);
        });
    }
}