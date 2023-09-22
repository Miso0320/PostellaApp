package com.appteam4.postella.ui;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appteam4.postella.R;
import com.appteam4.postella.dto.MyPageOrderList;

public class OrderViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "OrderViewHolder";
    private TextView pg_name;
    private TextView prd_name;
    private TextView prd_price;
    private TextView prd_qty;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        // 아이템 UI 얻기
        this.pg_name = itemView.findViewById(R.id.pg_name);
        this.prd_name = itemView.findViewById(R.id.prd_name);
        this.prd_price = itemView.findViewById(R.id.prd_price);
        this.prd_qty = itemView.findViewById(R.id.prd_qty);
    }

    public void setData(MyPageOrderList order) {
        this.pg_name.setText(order.getPg_name());
        this.prd_name.setText(order.getPrd_name());
        this.prd_qty.setText(order.getOd_detail_qty() + "개");
        
        // 상품금액 받아오기(개당 가격*개수)
        int totalPrice = order.getOd_detail_price() * order.getOd_detail_qty();
        this.prd_price.setText(totalPrice + "원");
    }
}
