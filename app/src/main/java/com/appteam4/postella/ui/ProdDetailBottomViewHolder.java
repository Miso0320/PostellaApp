package com.appteam4.postella.ui;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appteam4.postella.R;
import com.appteam4.postella.dto.MyWish;
import com.appteam4.postella.dto.Product;
import com.appteam4.postella.service.WishService;

import java.text.DecimalFormat;

public class ProdDetailBottomViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "ProdDetailBottomViewHolder";
    private int pgNo;                   // 상품그룹 식별번호
    private int usNo;                   // 유저식별번호
    private int prdNo;                  // 상품옵션 번호
    private TextView optionName;        // 상품옵션명
    private TextView optionPrice;       // 상품옵션가격
    private TextView optionQty;         // 선택수량
    private Button plusBtn;             // 수량 +1
    private Button minusBtn;            // 수량 -1

    public ProdDetailBottomViewHolder(@NonNull View itemView) {
        super(itemView);
        // 아이템 UI 얻기
        this.optionName = itemView.findViewById(R.id.option_name);
        this.optionPrice = itemView.findViewById(R.id.option_price);
        this.optionQty = itemView.findViewById(R.id.option_qty);
        this.plusBtn = itemView.findViewById(R.id.btn_plus);
        this.minusBtn = itemView.findViewById(R.id.btn_minus);
    }

    public void setData(Product product) {
        // 한화 통화 형식을 사용
        DecimalFormat df = new DecimalFormat("#,###");

        this.pgNo = product.getPg_no();
        this.prdNo = product.getPrd_no();
        this.optionName.setText(product.getPrd_name());
        this.optionPrice.setText(df.format(product.getPrd_price()) + "원");
    }

}
