package com.appteam4.postella.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentProdListBinding;

public class ProdListFragment extends Fragment {

    private static final String TAG = "ProdListFragment";
    private FragmentProdListBinding binding;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProdListBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);

        initBtnProdDetail();

        return binding.getRoot();
    }

    private void initBtnProdDetail() {
        binding.btnProdDetail.setOnClickListener(v -> {
            navController.navigate(R.id.action_dest_prod_list_to_dest_prod_detail);
        });
    }
}