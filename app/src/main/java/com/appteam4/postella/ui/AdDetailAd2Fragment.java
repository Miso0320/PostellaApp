package com.appteam4.postella.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.appteam4.postella.databinding.FragmentAdDetailAd2Binding;

public class AdDetailAd2Fragment extends Fragment {

    private static final String TAG = "AdDetailAd2Fragment";
    private FragmentAdDetailAd2Binding binding;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdDetailAd2Binding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);

        /*initBtnGoBack();
        initBtnBottomSheet();*/

        return binding.getRoot();
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