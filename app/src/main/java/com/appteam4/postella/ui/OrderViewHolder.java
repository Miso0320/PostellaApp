package com.appteam4.postella.ui;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appteam4.postella.R;
import com.appteam4.postella.dto.MyPageOrderList;
import com.appteam4.postella.service.OrderService;
import com.appteam4.postella.service.WishService;

import java.text.DecimalFormat;

public class OrderViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "OrderViewHolder";
    private TextView pg_name;
    private TextView prd_name;
    private TextView prd_price;
    private TextView prd_qty;
    private ImageView pgImg;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        // 아이템 UI 얻기
        this.pg_name = itemView.findViewById(R.id.pg_name);
        this.prd_name = itemView.findViewById(R.id.prd_name);
        this.prd_price = itemView.findViewById(R.id.prd_price);
        this.prd_qty = itemView.findViewById(R.id.prd_qty);
        this.pgImg = itemView.findViewById(R.id.pg_img);
    }

    public void setData(MyPageOrderList order) {
        // 한화 통화 형식을 사용
        DecimalFormat df = new DecimalFormat("#,###");

        this.pg_name.setText(order.getPg_name());
        this.prd_name.setText(order.getPrd_name());
        this.prd_qty.setText(order.getOd_detail_qty() + "개");

        // 상품금액 받아오기(개당 가격*개수)
        int totalPrice = order.getOd_detail_price() * order.getOd_detail_qty();
        this.prd_price.setText(df.format(totalPrice) + "원");

        Log.i(TAG, "setData:************************* " + order.getPrd_no());

        // 상품 썸네일 이미지 받아오기
        OrderService.loadImage(order.getPrd_no(), pgImg);
    }
}
