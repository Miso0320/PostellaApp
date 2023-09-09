package com.appteam4.postella.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MainAdPagerAdapter extends FragmentStateAdapter {

    public MainAdPagerAdapter(@NonNull MainFragment fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        MainAdPageFragment mainAdPageFragment = new MainAdPageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("pageNo", (position+1));
        mainAdPageFragment.setArguments(bundle);

        return mainAdPageFragment;
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
