package com.appteam4.postella.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appteam4.postella.R;
import com.appteam4.postella.dto.MyWish;

public class WishViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "WishViewHolder";
    private int pgNo;                   // 상품그룹 식별번호
    private int usNo;                   // 유저식별번호
    private ImageView pgImg;            // 상품이미지
    private TextView wishQty;           // 찜목록 전체 개수
    private TextView pgName;            // 상품명
    private TextView prdDiscount;       // 할인율
    private TextView prdPrice;          // 판매가

    public WishViewHolder(@NonNull View itemView) {
        super(itemView);
        // 아이템 UI 얻기
        this.pgName = itemView.findViewById(R.id.pg_name);
        this.prdDiscount = itemView.findViewById(R.id.prd_discount);
        this.prdPrice = itemView.findViewById(R.id.prd_price);
    }

    public void setData(MyWish wish) {
        this.pgNo = wish.getPg_no();
        this.pgName.setText(wish.getPg_name());
        this.prdDiscount.setText(String.valueOf(wish.getSale_percent()) + "%");
        this.prdPrice.setText(String.valueOf(wish.getPrd_price()) + "원");
    }

}
