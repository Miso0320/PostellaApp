package com.appteam4.postella.ui;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.ActivityMainBinding;
import com.appteam4.postella.databinding.FragmentProdDetailBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ProdDetailFragment extends Fragment {
    private static final String TAG = "ProdDetailFragment";
    private FragmentProdDetailBinding binding;
    NavController navController;
    
    // 취소선 관련 필드
    private TextView textView;
    private boolean isStrikeThrough = false;
    
    // 이미지 슬라이더 관련 필드
    private ViewPager2 viewPager2;
    private LinearLayout linearLayout;

    private TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProdDetailBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);

        // 앱바 설정
        //initMenu();

        // 취소선 추가
        binding.prdOrgPrice.setPaintFlags(binding.prdOrgPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        // 이미지 슬라이더
        initPagerView();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 탭 메뉴 이동
        initTabPage();
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
                    navController.navigate(R.id.action_dest_main_to_dest_search);
                    return true;
                } else if(menuItem.getItemId() == R.id.dest_cart) {
                    navController.navigate(R.id.action_dest_main_to_dest_cart);
                    return true;
                }else if(menuItem.getItemId() == R.id.dest_category){
                    return true;
                }
                return false;
            }
        };
        getActivity().addMenuProvider(menuProvider, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    private void initTabPage() {
        binding.tabProductReviews.setOnClickListener(v -> {
            //navController.navigate(R.id.action_dest_prod_detail_to_dest_review);
        });

        binding.tabProductInquiries.setOnClickListener(v -> {
            navController.navigate(R.id.action_dest_prod_detail_to_dest_inquiry);
        });
    }

    private void initPagerView() {
        ProductDetailAdapter productDetailAdapter = new ProductDetailAdapter(getContext());
        binding.productImageSlider.setAdapter(productDetailAdapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                binding.tabLayout, binding.productImageSlider, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
            }
        });

        tabLayoutMediator.attach();
    }

}