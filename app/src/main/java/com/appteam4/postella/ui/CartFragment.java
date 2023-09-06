package com.appteam4.postella.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentAdDetailBinding;
import com.appteam4.postella.databinding.FragmentCartBinding;

public class CartFragment extends Fragment {
    private static final String TAG = "CartFragment";
    private FragmentCartBinding binding;

    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);

        initBtnGoBack();
        initBtnprodDtail();
        initBtnOrder();

        return binding.getRoot();
    }

    private void initBtnGoBack(){
        binding.btnGoBack.setOnClickListener(v->{
            navController.popBackStack();
        });
    }

    private void initBtnprodDtail(){
        binding.btnProdDetail.setOnClickListener(v->{
            navController.navigate(R.id.action_dest_cart_to_dest_prod_detail);
        });
    }

    private void initBtnOrder(){
        binding.btnOrder.setOnClickListener(v->{
            navController.navigate(R.id.action_dest_cart_to_dest_order);
        });
    }
}