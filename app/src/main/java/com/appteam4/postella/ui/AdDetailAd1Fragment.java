package com.appteam4.postella.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appteam4.postella.databinding.FragmentAdDetailAd1Binding;


public class AdDetailAd1Fragment extends Fragment {

    private static final String TAG = "AdDetailFragment";
    private FragmentAdDetailAd1Binding binding;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdDetailAd1Binding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);
        Log.i(TAG, "광고1 생성");

        /*initBtnGoBack();
        initBtnBottomSheet();*/

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "광고1 파괴");
    }
    /*private void initBtnGoBack(){
        binding.btnGoBack.setOnClickListener(v->{
            navController.popBackStack();
        });
    }

    private void initBtnBottomSheet() {
        binding.btnGoBack.setOnClickListener(v -> {
            dismiss();
        });
    }*/

    /*@Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.).setOnClickListener(v -> {
            dismiss();
        });
    }*/
}