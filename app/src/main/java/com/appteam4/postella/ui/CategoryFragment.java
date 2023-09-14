package com.appteam4.postella.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

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
        CategoryTabPagerAdapter pagerAdapter = new CategoryTabPagerAdapter(this);

        // 프래그먼트와 탭 이름 추가 또는 변경
        pagerAdapter.addFragment(new CategoryTabPageFragment(), "포토엽서");
        pagerAdapter.addFragment(new CategoryTabPageFragment(), "디자인 패턴 엽서");
        pagerAdapter.addFragment(new CategoryTabPageFragment(), "그림/일러스트 엽서");
        pagerAdapter.addFragment(new CategoryTabPageFragment(), "캘리그라피 엽서");
        pagerAdapter.addFragment(new CategoryTabPageFragment(), "홍홍엔데코");
        pagerAdapter.addFragment(new CategoryTabPageFragment(), "송미풋");
        pagerAdapter.addFragment(new CategoryTabPageFragment(), "우리집 토끼는 미소천사");
        pagerAdapter.addFragment(new CategoryTabPageFragment(), "유짱이네 쿼카");
        pagerAdapter.addFragment(new CategoryTabPageFragment(), "살쾡이는 너무 무서워");
        pagerAdapter.addFragment(new CategoryTabPageFragment(), "축하/기념일");
        pagerAdapter.addFragment(new CategoryTabPageFragment(), "사랑/고백");
        pagerAdapter.addFragment(new CategoryTabPageFragment(), "감사");
        pagerAdapter.addFragment(new CategoryTabPageFragment(), "힐링");
        pagerAdapter.addFragment(new CategoryTabPageFragment(), "위로 격려");

        TabLayout tabLayout = binding.tabLayoutCategory;
        ViewPager2 viewPager2 = binding.viewPagerCategory;

        viewPager2.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            tab.setText(pagerAdapter.getTabTitle(position));
        }).attach();
    }

}