package com.appteam4.postella.ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CategoryTabPagerAdapter extends FragmentStateAdapter {
    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();
    public CategoryTabPagerAdapter(@NonNull CategoryFragment fragmentActivity) {super(fragmentActivity);}

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // 해당 위치의 프래그먼트를 반환
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        // 프래그먼트 개수 반환
        return  fragmentList.size();
    }

    // 프래그먼트와 탭 이름을 추가하는 메서드
    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    }

    // 탭 이름을 가져오는 메서드
    public String getTabTitle(int position) {
        return fragmentTitleList.get(position);
    }

}
