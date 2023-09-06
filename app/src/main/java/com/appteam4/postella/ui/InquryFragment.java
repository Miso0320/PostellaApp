package com.appteam4.postella.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentInquryBinding;
import com.appteam4.postella.databinding.FragmentReviewBinding;

public class InquryFragment extends Fragment {
    private static final String TAG = "InquryFragment";
    private FragmentInquryBinding binding;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInquryBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);

        initBtnOrder();
        initBtnReview();
        initBtnInquiry();

        return binding.getRoot();
    }

    private void initBtnOrder() {
        binding.btnOrder.setOnClickListener(v -> {
            navController.navigate(R.id.action_dest_review_to_dest_order);
        });
    }

    private void initBtnReview() {
        binding.btnReview.setOnClickListener(v -> {
            navController.navigate(R.id.action_dest_inquiry_to_dest_review);
        });
    }

    private void initBtnInquiry() {
        binding.btnInquiry.setOnClickListener(v -> {
            navController.navigate(R.id.action_dest_review_to_dest_inquiry);
        });
    }
}