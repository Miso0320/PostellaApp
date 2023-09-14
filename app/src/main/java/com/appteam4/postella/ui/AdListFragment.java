package com.appteam4.postella.ui;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.ActivityMainBinding;
import com.appteam4.postella.databinding.FragmentAdListBinding;

public class AdListFragment extends Fragment {

    private static final String TAG = "AdListFragment";
    private FragmentAdListBinding binding;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAdListBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);

        initBtnBottomSheetAd1();
        initBtnBottomSheetAd2();

        return binding.getRoot();
    }

    private void initBtnBottomSheetAd1() {
        /*binding.imgAd1.setOnClickListener(v -> {
            navController.navigate(R.id.action_dest_ad_list_to_dest_ad_detail_ad1);
        });*/

        binding.imgAd1.setOnClickListener(v -> {
            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.dest_ad_detail_ad1, false)
                    .setLaunchSingleTop(true)
                    .build();

            navController.navigate(R.id.dest_ad_detail_ad1, null, navOptions);
        });
    }

    private void initBtnBottomSheetAd2() {
        binding.imgAd2.setOnClickListener(v -> {
            navController.navigate(R.id.action_dest_ad_list_to_dest_ad_detail_ad2);
        });
    }
}