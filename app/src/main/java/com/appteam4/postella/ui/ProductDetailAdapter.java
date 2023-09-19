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
    // 상품그룹 식별번호
    private int pg_no;
    // 상품상세 이미지목록 개수
    private int imgCnt;

    public ProductDetailAdapter(Context context, int pg_no, int imgCnt) {
        super((FragmentActivity) context);
        this.context = context;
        this.pg_no = pg_no;
        this.imgCnt = imgCnt;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // getItemCount()의 리턴값만큼 실행
        ProductDetailImageFragment productDetailImageFragment = new ProductDetailImageFragment();
        Bundle bundle = new Bundle();
        // bundle 객체에 position 개수 넣기
        bundle.putInt("pageNo", position);
        // bundle 객체에 상품그룹 식별번호 넣기
        bundle.putInt("pg_no", pg_no);
        productDetailImageFragment.setArguments(bundle);

        return productDetailImageFragment;
    }

    @Override
    public int getItemCount() {
        return imgCnt;
    }
}
