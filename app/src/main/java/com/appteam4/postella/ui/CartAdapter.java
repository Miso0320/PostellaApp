package com.appteam4.postella.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appteam4.postella.R;
import com.appteam4.postella.dto.Cart;
import com.appteam4.postella.service.CartService;
import com.appteam4.postella.service.ServiceProvider;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartHolder> {

    private static final String TAG = "CartAdapter";
    List<Cart> list = new ArrayList<>();
    private CartOnItemClickListener cartOnItemClickListener;
    private CartService cartService;
    private Boolean isAllChecked = false;

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 사용하여 XML 레이아웃 파일을 View 객체로 인플레이트
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.prod_cart_item, parent, false);

        // CartService
        cartService = ServiceProvider.getCartService(itemView.getContext());

        // 새로운 CartHolder 객체를 생성하고 반환, RecyclerView 항목의 뷰와 이벤트 처리를 관리
        CartHolder cartHolder = new CartHolder(itemView, cartOnItemClickListener);
        return cartHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        // 해당 위치(position)의 List<Cart> 객체를 가져옴
        Cart cart = list.get(position);

        if (isAllChecked) {
            CheckBox checkBox = holder.itemView.findViewById(R.id.btn_prod_checkbox);
            checkBox.setChecked(true);
        }

        // ViewHolder에 데이터를 설정하여 화면에 표시
        holder.setCartData(cart);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Cart> list) {
        this.list = list;
    }

    public interface CartOnItemClickListener {
        void onItemClick(View itemView, int position);
        void btnMinusClick(View itemView, int position);
        void btnPlusClick(View itemView, int position);
        void btnCheckBoxClick(CheckBox checkBox, int position);
    }

    public void setOnItemClickListener(CartOnItemClickListener cartOnItemClickListener) {
        this.cartOnItemClickListener = cartOnItemClickListener;
    }

    public Cart getItem(int position) {
        return list.get(position);
    }

    public void setAllChecked(Boolean allChecked) {
        isAllChecked = allChecked;
    }
}
