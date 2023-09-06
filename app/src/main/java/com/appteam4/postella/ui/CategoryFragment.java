package com.appteam4.postella.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentCategoryBinding;

public class CategoryFragment extends Fragment {

    private static final String TAG = "CategoryFragment";
    private FragmentCategoryBinding binding;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);

        initBtnProdList();

        return binding.getRoot();
    }

    private void initBtnProdList() {
        binding.btnProdList.setOnClickListener(v -> {
            navController.navigate(R.id.action_dest_category_to_dest_prod_list);
        });
    }
}