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
import com.appteam4.postella.databinding.FragmentCartBinding;
import com.appteam4.postella.dto.Cart;
import com.appteam4.postella.dto.MyPageOrderList;
import com.appteam4.postella.service.CartService;
import com.appteam4.postella.service.ServiceProvider;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment {

    private static final String TAG = "CartFragment";
    private FragmentCartBinding binding;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);

        initBtnOrder();

        initRecyclerViewCart();

        return binding.getRoot();
    }

    private void initBtnOrder(){
        binding.btnOrder.setOnClickListener(v->{
            navController.navigate(R.id.action_dest_cart_to_dest_order);
        });
    }

    private void initRecyclerViewCart() {
        // RecyclerView 에서 항목을 수직으로 배치
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerViewCart.setLayoutManager(linearLayoutManager);

        // Adapter 생성
        CartAdapter cartAdapter = new CartAdapter();

        // API 서버에서 JSON 목록 받기
        CartService cartService = ServiceProvider.getCart(getContext());
        Call<List<Cart>> call = cartService.getCartList();
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
                    // RecyclerView를 보이도록 설정
                    binding.recyclerViewCart.setVisibility(View.VISIBLE);
                } else {
                    Log.i(TAG, "onResponse: 리스트가 널이여~");
                }
                // RecyclerView에 어댑터 세팅
                // 어댑터에 데이터가 설정된 후 notifyDataSetChanged() 호출
                binding.recyclerViewCart.setAdapter(cartAdapter);
            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {
                Log.i(TAG, "Cart API 호출 실패");
            }
        });

        // 항목을 클릭 했을 때 콜백 객체를 등록
        cartAdapter.setOnItemClickListener(new CartAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Cart cart = cartAdapter.getItem(position);
                Log.i(TAG, cart.toString());

                Bundle args = new Bundle();
                args.putSerializable("cart", cart);
                navController.navigate(R.id.action_dest_cart_to_dest_order);
            }
        });
    }
}