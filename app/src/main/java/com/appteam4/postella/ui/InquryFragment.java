package com.appteam4.postella.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

        // 앱바 설정
        initMenu();

        // 탭 메뉴 이동
        initTabPage();

        return binding.getRoot();
    }

    private void initMenu() {
        MenuProvider menuProvider = new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.nav_menu_top, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.dest_search) {
                    navController.navigate(R.id.dest_search, null);
                    return true;
                } else if(menuItem.getItemId() == R.id.dest_cart) {
                    navController.navigate(R.id.dest_cart, null);
                    return true;
                }
                return false;
            }
        };
        getActivity().addMenuProvider(menuProvider, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    private void initTabPage() {
        binding.tabProductDetail.setOnClickListener(v -> {
            //navController.navigate(R.id.action_dest_prod_detail_to_dest_review);
            //navController.popBackStack(R.id.dest_prod_detail, false);
            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.dest_prod_detail, false)
                    .setLaunchSingleTop(true)
                    .build();

            navController.navigate(R.id.dest_prod_detail, null, navOptions);
        });

        binding.tabProductReviews.setOnClickListener(v -> {
            //navController.navigate(R.id.action_dest_prod_detail_to_dest_review);
            //navController.popBackStack(R.id.dest_review, false);
            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.dest_review, false)
                    .setLaunchSingleTop(true)
                    .build();

            navController.navigate(R.id.dest_review, null, navOptions);
        });
    }
}