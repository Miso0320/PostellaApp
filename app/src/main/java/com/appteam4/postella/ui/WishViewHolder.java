package com.appteam4.postella.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appteam4.postella.R;
import com.appteam4.postella.dto.Wish;

public class WishViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "WishViewHolder";
    private int pgNo; // 상품(옵션) 식별
    private int usNo; // 유저 식별
    private ImageView pgImg;    // 상품이미지
    private TextView pgName;    // 상품명
    private TextView prdDiscount;   // 할인율
    private TextView prdPrice;  // 판매가

    public WishViewHolder(@NonNull View itemView) {
        super(itemView);
        // 아이템 UI 얻기
        this.pgName = itemView.findViewById(R.id.pg_name);
        this.prdDiscount = itemView.findViewById(R.id.prd_discount);
        this.prdPrice = itemView.findViewById(R.id.prd_price);
    }

    public void setData(Wish wish) {
        this.pgNo = wish.getPg_no();
        this.pgName.setText(wish.getPg_name());

        //할인율 계산
        int topPrdPrice = wish.getPrd_org_price();
        int topPrdSaleprice = wish.getPrd_price();
        double salePercent = (double) (topPrdPrice - topPrdSaleprice) / topPrdPrice * 100;
        int intSalePercent = (int) salePercent;
        //할인 하고 있는 상품만 할인율 표시
        if (intSalePercent != 0) {
            //this.prdDiscount.setText(String.valueOf(intSalePercent) + "%");
            this.prdDiscount.setText("10%");
        }

        this.prdPrice.setText(String.valueOf(wish.getPrd_price()));
    }

}
