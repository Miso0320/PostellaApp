package com.appteam4.postella.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.appteam4.postella.dto.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailAdapter extends FragmentStateAdapter {
    private Context context;
    private List<Product> productList = new ArrayList<>();
    private int pg_no;

    public ProductDetailAdapter(Context context, int pg_no) {
        super((FragmentActivity) context);
        this.context = context;
        this.pg_no = pg_no;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // getItemCount()의 리턴값만큼 실행
        ProductDetailImageFragment productDetailImageFragment = new ProductDetailImageFragment();
        Bundle bundle = new Bundle();
        // position은 0부터 시작하기 때문에 position+1 필요
        bundle.putInt("pageNo", (position + 1));
        bundle.putInt("pg_no", pg_no);
        productDetailImageFragment.setArguments(bundle);

        return productDetailImageFragment;
    }

    public void setList(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
