package com.appteam4.postella.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appteam4.postella.R;
import com.appteam4.postella.dto.Cart;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartHolder> {

    private static final String TAG = "CartAdapter";
    List<Cart> list = new ArrayList<>();
    private CartOnItemClickListener cartOnItemClickListener;

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 사용하여 XML 레이아웃 파일을 View 객체로 인플레이트
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.prod_cart_item, parent, false);

        // 새로운 CartHolder 객체를 생성하고 반환, RecyclerView 항목의 뷰와 이벤트 처리를 관리
        CartHolder cartHolder = new CartHolder(itemView, cartOnItemClickListener);
        return cartHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        // 해당 위치(position)의 List<Cart> 객체를 가져옴
        Cart cart = list.get(position);
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
    }

    public void setOnItemClickListener(CartOnItemClickListener cartOnItemClickListener) {
        this.cartOnItemClickListener = cartOnItemClickListener;
    }

    public Cart getItem(int position) {
        return list.get(position);
    }

}
