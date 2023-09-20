package com.appteam4.postella.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appteam4.postella.MainActivity;
import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentCartBinding;
import com.appteam4.postella.databinding.ProdCartItemBinding;
import com.appteam4.postella.datastore.AppKeyValueStore;
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

        // 스피너 초기화
        initSpinner();

        initRecyclerViewCart();

        return binding.getRoot();
    }

    private void initRecyclerViewCart() {
        // RecyclerView 에서 항목을 수직으로 배치
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerViewCart.setLayoutManager(linearLayoutManager);

        // Adapter 생성
        CartAdapter cartAdapter = new CartAdapter();

        // 공유데이터에서 us_no가져오기
        int us_no = Integer.parseInt(AppKeyValueStore.getValue(getContext(), "us_no"));

        // API 서버에서 JSON 목록 받기
        CartService cartService = ServiceProvider.getCart(getContext());
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
                // RecyclerView에 어댑터 세팅
                // 어댑터에 데이터가 설정된 후 notifyDataSetChanged() 호출
                //binding.recyclerViewCart.setAdapter(cartAdapter);
            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {
                Log.i(TAG, "Cart API 호출 실패");
            }
        });

        // 항목을 클릭 했을 때 콜백 객체를 등록
        cartAdapter.setOnItemClickListener(new CartAdapter.CartOnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Cart cart = cartAdapter.getItem(position);
                Log.i(TAG, cart.toString());

                Bundle args = new Bundle();
                args.putSerializable("cart", cart);
                navController.navigate(R.id.action_dest_cart_to_dest_order);
            }
        });

        /*// 더미데이터
        for (int i=1; i<=10; i++) {
            Cart cart = new Cart();
            cart.setCartNo(i);
            cart.setCartTitle("장바구니" + i);
            cart.setCartPrice(2000 + i);
            //cart.setCartArrivalDate(20230914);

            cart.setCartProdPrice(2000 + i);

            cartAdapter.addCart(cart);
        }

        binding.recyclerViewCart.setAdapter(cartAdapter);*/
    }

    private void initSpinner() {
        ProdCartItemBinding prodCartItemBinding = ProdCartItemBinding.inflate(getLayoutInflater());
        LinearLayout linearLayout = prodCartItemBinding.spinnerLayout;
        Spinner spinner = linearLayout.findViewById(R.id.cart_spinner_count);

        //Spinner spinner = binding.recyclerViewCart.findViewById(R.id.cart_spinner_count);
        Log.i(TAG, "스피터: " + spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.cart_prod_count, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item
        );

        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}