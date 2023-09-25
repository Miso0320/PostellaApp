package com.appteam4.postella.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
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
import com.google.android.material.snackbar.Snackbar;

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

        // Bundle 객체 셍성
        Bundle bundle = new Bundle();

        // 로그인 여부 확인
        String exist = AppKeyValueStore.getValue(getContext(), "us_no");

        // 로그인 되어 있는 경우
        if (exist != null) {
            Call<List<Product>> optionCall = productDetailService.detailOptionsForApp(pg_no);

            // 상품 옵션정보 받아오기
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

            // 수량조절 버튼 클릭 이벤트 설정
            prodDetailBottomAdapter.setOnItemClickListener(new ProdDetailBottomAdapter.OnItemClickListener() {
                @Override
                public void onPlusBtnClick(View itemView, int position) {
                    Product product = prodDetailBottomAdapter.getItem(position);
                    
                    // 현재 수량 가져오기
                    int quantity = product.getQuantity();

                    if (quantity < 100) {
                        quantity++;
                    }

                    // 수량 업데이트
                    product.setQuantity(quantity);

                    bundle.putInt("quantity", quantity);
                    bundle.putInt("prd_no", product.getPrd_no());
                    bundle.putString("prd_name", product.getPrd_name());
                    bundle.putInt("prd_price", product.getPrd_price());

                    // 변화 알리기
                    prodDetailBottomAdapter.notifyDataSetChanged();
                }

                @Override
                public void onMinusBtnClick(View itemView, int position) {
                    Product product = prodDetailBottomAdapter.getItem(position);

                    // 현재 수량 가져오기
                    int quantity = product.getQuantity();

                    if (quantity > 0) {
                        quantity--;
                    }
                    // 수량 업데이트
                    product.setQuantity(quantity);


                    bundle.putInt("quantity", quantity);
                    bundle.putInt("prd_no", product.getPrd_no());
                    bundle.putString("prd_name", product.getPrd_name());
                    bundle.putInt("prd_price", product.getPrd_price());

                    // 변화 알리기
                    prodDetailBottomAdapter.notifyDataSetChanged();
                }
            });

        } else {
            Log.i(TAG, "로그인 안된 경우 처리 필요");
        }

        // 주문결제 페이지로 이동
        initBtnSet(bundle);

    }

    private void initBtnSet(Bundle bundle) {
        binding.btnCart.setOnClickListener(v -> {
            Snackbar snackbar = Snackbar.make(getView(), "상품을 장바구니에 담았어요!", Snackbar.LENGTH_SHORT);
            snackbar.setAction("바로가기", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 바로가기 클릭 시 장바구니 프래그먼트로 이동
                    NavController navController = NavHostFragment.findNavController(ProdDetailBottomSheetFragment.this);
                    navController.navigate(R.id.dest_cart);
                }
            });
            snackbar.show();
        });

        /*binding.btnBuyNow.setOnClickListener(v -> {
            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.dest_inquiry, false)
                    .setLaunchSingleTop(true)
                    .build();

            navController.navigate(R.id.dest_order, bundle, navOptions);
        });*/
    }
}