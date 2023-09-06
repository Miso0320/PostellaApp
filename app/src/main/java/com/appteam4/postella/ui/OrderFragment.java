package com.appteam4.postella.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentLoginBinding;
import com.appteam4.postella.databinding.FragmentOrderBinding;

public class OrderFragment extends Fragment {
    private static final String TAG = "OrderFragment";
    private FragmentOrderBinding binding;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);

        initBtnMain();

        return binding.getRoot();
    }

    private void initBtnMain() {
        binding.btnMyPage.setOnClickListener(v -> {
            navController.navigate(R.id.action_dest_order_to_dest_mypage_order_list);
            //navController.popBackStack(R.id.dest_main,false);
        });
    }
}