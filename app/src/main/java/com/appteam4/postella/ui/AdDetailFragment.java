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
import com.appteam4.postella.databinding.FragmentAdListBinding;

public class AdDetailFragment extends Fragment {

    private static final String TAG = "AdDetailFragment";
    private FragmentAdDetailBinding binding;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdDetailBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);

        initBtnGoBack();

        return binding.getRoot();
    }

    private void initBtnGoBack(){
        binding.btnGoBack.setOnClickListener(v->{
            navController.popBackStack();
        });
    }

}