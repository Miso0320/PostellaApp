package com.appteam4.postella.ui;

import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;


import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentCartBinding;

import com.appteam4.postella.datastore.AppKeyValueStore;
import com.appteam4.postella.dto.Cart;

import com.appteam4.postella.service.CartService;
import com.appteam4.postella.service.ServiceProvider;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment {

    private static final String TAG = "CartFragment";
    private FragmentCartBinding binding;
    private NavController navController;
    private List<Boolean> checkBoxList = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);

        CartAdapter cartAdapter = new CartAdapter();

        initRecyclerViewCart(cartAdapter);
        initBtnCheckAll(cartAdapter);

        return binding.getRoot();
    }

    private void initRecyclerViewCart(CartAdapter cartAdapter) {
        // RecyclerView 에서 항목을 수직으로 배치
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerViewCart.setLayoutManager(linearLayoutManager);

        if (AppKeyValueStore.getValue(getContext(), "us_no") != null) {
            // 공유데이터에서 us_no가져오기
            int us_no = Integer.parseInt(AppKeyValueStore.getValue(getContext(), "us_no"));

            // API 서버에서 JSON 목록 받기
            CartService cartService = ServiceProvider.getCartService(getContext());
            Call<List<Cart>> call = cartService.getCartList(us_no);
            call.enqueue(new Callback<List<Cart>>() {
                @Override
                public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                    // json -> List<Product>
                    List<Cart> cartList = response.body();
                    if (cartList != null) {
                        // 어댑터 데이터 생성하기
                        cartAdapter.setList(cartList);
                        // RecyclerView에 어댑터 세팅
                        binding.recyclerViewCart.setAdapter(cartAdapter);
                    } else {
                        Log.i(TAG, "onResponse: 리스트가 널이여~");
                    }
                }

                @Override
                public void onFailure(Call<List<Cart>> call, Throwable t) {
                    Log.i(TAG, "Cart API 호출 실패");
                }
            });
        }

        // 항목을 클릭 했을 때 콜백 객체를 등록
        cartAdapter.setOnItemClickListener(new CartAdapter.CartOnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Cart cart = cartAdapter.getItem(position);
                Log.i(TAG, cart.toString());

                Bundle args = new Bundle();
                args.putSerializable("cart", cart);
                args.putSerializable("pg_no", cart.getPg_no());

                navController.navigate(R.id.action_dest_cart_to_dest_prod_detail);
            }

            @Override
            public void btnMinusClick(View itemView, int position) {
                // 해당 위치의 아이템을 가져옴
                Cart cart = cartAdapter.getItem(position);
                CartService cartService = ServiceProvider.getCartService(getContext());

                int us_no = Integer.parseInt(AppKeyValueStore.getValue(getContext(), "us_no"));
                int prd_no = cart.getPrd_no();

                int currentCnt = cart.getCrt_qty();
                currentCnt--;
                cart.setCrt_qty(currentCnt);

                // DB업데이트
                Call<Void> call = cartService.updateCart(currentCnt, us_no, prd_no);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                    }
                });

                // RecyclerView를 업데이트하여 변경된 데이터를 반영
                cartAdapter.notifyItemChanged(position);
            }

            @Override
            public void btnPlusClick(View itemView, int position) {
                // 해당 위치의 아이템을 가져옴
                Cart cart = cartAdapter.getItem(position);
                CartService cartService = ServiceProvider.getCartService(getContext());

                int us_no = Integer.parseInt(AppKeyValueStore.getValue(getContext(), "us_no"));
                int prd_no = cart.getPrd_no();

                int currentCnt = cart.getCrt_qty();
                currentCnt++;
                cart.setCrt_qty(currentCnt);

                // DB업데이트
                Call<Void> call = cartService.updateCart(currentCnt, us_no, prd_no);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                    }
                });

                // RecyclerView를 업데이트하여 변경된 데이터를 반영
                cartAdapter.notifyItemChanged(position);
            }

            @Override
            public void btnCheckBoxClick(CheckBox checkBox, int position) {

                // 최초 실행 시 초기화
                if (checkBoxList == null) {
                    checkBoxList = new ArrayList<>();
                    for (int i=0; i<cartAdapter.getItemCount(); i++) {
                        checkBoxList.add(i, false);
                    }
                }

                // 리스트에 포지션과 체크여부를 저장
                checkBoxList.set(position, checkBox.isChecked());
                Log.i(TAG, "리스트: " + checkBoxList);

                // 모두 체크 되었으면 전체선택 체크
                if (!checkBoxList.contains(false)) {
                    binding.btnCheckAll.setChecked(true);
                } else {
                    binding.btnCheckAll.setChecked(false);
                }
            }
        });
    }
    private void initBtnCheckAll(CartAdapter cartAdapter) {
        binding.btnCheckAll.setOnClickListener(v -> {
            for (int i=0; i<binding.recyclerViewCart.getChildCount(); i++) {
                View view = binding.recyclerViewCart.getChildAt(i);
                CheckBox checkBox = view.findViewById(R.id.btn_prod_checkbox);
                checkBox.setChecked(binding.btnCheckAll.isChecked());
            }
            cartAdapter.setAllChecked(binding.btnCheckAll.isChecked());
        });
    }
}