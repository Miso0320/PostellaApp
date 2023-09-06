package com.appteam4.postella.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentAdListBinding;
import com.appteam4.postella.databinding.FragmentMyOrderListBinding;


public class AdListFragment extends Fragment {
    private static final String TAG = "AdListFragment";
    private FragmentAdListBinding binding;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdListBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);

        initBtnGoBack();
        initBtnAdDetail();

        return binding.getRoot();
    }

    private void initBtnGoBack(){
        binding.btnGoBack.setOnClickListener(v->{
            navController.popBackStack();
        });
    }

    private  void initBtnAdDetail(){
        binding.btnAdDetail.setOnClickListener(v->{
            navController.navigate(R.id.action_dest_ad_list_to_dest_ad_detail);
        });
    }
}