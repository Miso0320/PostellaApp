package com.appteam4.postella.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentProdDetailBottomSheetBinding;
import com.appteam4.postella.datastore.AppKeyValueStore;
import com.appteam4.postella.dto.Product;
import com.appteam4.postella.service.ProductDetailService;
import com.appteam4.postella.service.ServiceProvider;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdDetailBottomSheetFragment extends BottomSheetDialogFragment {
    public static final String TAG = "ProdDetailBottomSheetFragment";

    private FragmentProdDetailBottomSheetBinding binding;
    private NavController navController;

    // 상품그룹 식별번호
    private int pg_no;

    public ProdDetailBottomSheetFragment(int pg_no) {
        this.pg_no = pg_no;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProdDetailBottomSheetBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);

        // RecyclerView 초기화
        initRecyclerView();

        return binding.getRoot();
    }

    private void initRecyclerView() {
        // RecyclerView에서 항목을 수직으로 배치하도록 설정
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false
        );
        binding.optionRecyclerView.setLayoutManager(linearLayoutManager);

        // 어댑터 생성
        ProdDetailBottomAdapter prodDetailBottomAdapter = new ProdDetailBottomAdapter();

        ProductDetailService productDetailService = ServiceProvider.getProductDetailService(getContext());

        // 로그인 여부 확인
        String exist = AppKeyValueStore.getValue(getContext(), "us_no");
        // 로그인 되어 있는 경우
        if (exist != null) {
            // 유저식별번호 받아오기
            //int us_no = Integer.parseInt(AppKeyValueStore.getValue(getContext(), "us_no"));

            Call<List<Product>> optionCall = productDetailService.detailOptionsForApp(pg_no);

            optionCall.enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    List<Product> list = response.body();

                    if (list != null) {
                        prodDetailBottomAdapter.setProductList(list);

                        binding.optionRecyclerView.setAdapter(prodDetailBottomAdapter);
                    }
                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {
                    t.printStackTrace();
                }
            });

        } else {
            Log.i(TAG, "로그인 안된 경우 처리 필요");
        }

    }
}