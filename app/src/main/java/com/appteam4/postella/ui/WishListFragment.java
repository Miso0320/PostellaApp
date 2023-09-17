package com.appteam4.postella.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentWishListBinding;
import com.appteam4.postella.dto.Wish;

public class WishListFragment extends Fragment {

    private static final String TAG = "WishListFragment";
    private FragmentWishListBinding binding;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWishListBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);

        // RecyclerView 초기화
        initRecyclerView();

        // 상세가기(임시버튼)
        initBtnProdDetail();

        return binding.getRoot();
    }

    private void initRecyclerView() {
        // RecyclerView에서 항목을 수직으로 배치하도록 설정
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false
        );
        binding.wishListView.setLayoutManager(linearLayoutManager);

        // 구분선 추가하기
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                binding.wishListView.getContext(),
                linearLayoutManager.getOrientation()
        );
        binding.wishListView.addItemDecoration(dividerItemDecoration);
        
        // 어댑터 생성
        WishAdapter wishAdapter = new WishAdapter();

        for (int i = 1; i <= 5; i++) {
            Wish wish = new Wish();
            wish.setPg_no(i);
            wish.setPg_name("상품" + i);
            wish.setPrd_price(i*2000);

            wishAdapter.addWish(wish);
        }
        binding.wishListView.setAdapter(wishAdapter);
        
    }

    private void initBtnProdDetail() {
        binding.wishQty.setOnClickListener(v -> {
            navController.navigate(R.id.action_dest_wish_list_to_dest_prod_detail);
        });
    }
}