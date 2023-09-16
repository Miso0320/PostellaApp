package com.appteam4.postella.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import com.appteam4.postella.MainActivity;
import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentCategoryBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class CategoryFragment extends Fragment {

    private static final String TAG = "CategoryFragment";
    private FragmentCategoryBinding binding;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);

        //Tab 초기화
        initTabLayout();
        return binding.getRoot();
    }

    private void initTabLayout(){
        Bundle bundle = new Bundle();
        CategoryTabPagerAdapter pagerAdapter = new CategoryTabPagerAdapter(this);
        // MainActivity에서 선택한 메뉴 아이템 가져오기
        int selectedMenuItem = ((MainActivity) requireActivity()).getSelectedMenuItem();

        // 프래그먼트와 탭 이름 추가 또는 변경------------------------------------
        pagerAdapter.addFragment(new CategoryTabPageFragment(), "CATEGORY");
        pagerAdapter.addFragment(new BrandTabPageFragment(), "BRAND");
        pagerAdapter.addFragment(new MessageTabPageFragment(), "MESSAGE");

        TabLayout tabLayout = binding.tabLayoutCategory;
        ViewPager2 viewPager2 = binding.viewPagerCategory;

        viewPager2.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            tab.setText(pagerAdapter.getTabTitle(position));
        }).attach();

        // 선택한 메뉴 아이템에 따라 탭 선택
        if(selectedMenuItem == R.id.c_sub_item1 || selectedMenuItem == R.id.c_sub_item2 ||
                selectedMenuItem == R.id.c_sub_item3 || selectedMenuItem == R.id.c_sub_item4){
            // 첫 번째 탭 선택
            viewPager2.setCurrentItem(0);
        }else if( selectedMenuItem == R.id.b_sub_item1 || selectedMenuItem == R.id.b_sub_item2 ||
                selectedMenuItem == R.id.b_sub_item3 || selectedMenuItem == R.id.b_sub_item4 ||
                selectedMenuItem == R.id.b_sub_item5 ){
            // 두 번째 탭 선택
            viewPager2.setCurrentItem(1);
        }else{
            // 세 번째 탭 선택
            viewPager2.setCurrentItem(2);
        }
    }

}