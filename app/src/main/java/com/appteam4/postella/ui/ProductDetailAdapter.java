package com.appteam4.postella.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ProductDetailAdapter extends FragmentStateAdapter {
    private Context context;

    public ProductDetailAdapter(Context context) {
        super((FragmentActivity) context);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // getItemCount()의 리턴값만큼 실행
        ProductDetailImageFragment productDetailImageFragment = new ProductDetailImageFragment();
        Bundle bundle = new Bundle();
        // position은 0부터 시작하기 때문에 position+1 필요
        bundle.putInt("pageNo", (position+1));
        productDetailImageFragment.setArguments(bundle);

        return productDetailImageFragment;
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}