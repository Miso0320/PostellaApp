package com.appteam4.postella.ui;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appteam4.postella.R;
import com.appteam4.postella.dto.MyWish;
import com.appteam4.postella.service.WishService;

public class WishViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "WishViewHolder";
    private int pgNo;                   // 상품그룹 식별번호
    private int usNo;                   // 유저식별번호
    private int prdNo;                  // 상품옵션 번호
    private ImageView pgImg;            // 상품이미지
    private TextView wishQty;           // 찜목록 전체 개수
    private TextView pgName;            // 상품명
    private TextView prdDiscount;       // 할인율
    private TextView prdPrice;          // 판매가
    private Button deleteBtn;           // 삭제버튼

    private WishAdapter.OnItemClickListener listener;

    public WishViewHolder(@NonNull View itemView, WishAdapter.OnItemClickListener onItemClickListener) {
        super(itemView);
        // 아이템 UI 얻기
        this.pgName = itemView.findViewById(R.id.pg_name);
        this.prdDiscount = itemView.findViewById(R.id.prd_discount);
        this.prdPrice = itemView.findViewById(R.id.prd_price);

        this.listener = listener;
        
        // 상품 썸네일 이미지
        this.pgImg = itemView.findViewById(R.id.pg_img);
        
        // 클릭 이벤트 처리(이미지 선택 시 상품상세로 이동)
        pgImg.setOnClickListener(v -> {
            onItemClickListener.onPgImgClick(v, getAdapterPosition());
        });

        // 찜목록 삭제버튼
        this.deleteBtn = itemView.findViewById(R.id.btn_wish_delete);

        // 클릭 이벤트 처리(삭제 버튼)
        deleteBtn.setOnClickListener(v -> {
            onItemClickListener.onDeleteBtnClick(v, getAdapterPosition());
        });

    }

    public void setData(MyWish wish) {
        this.pgNo = wish.getPg_no();
        this.pgName.setText(wish.getPg_name());
        this.prdDiscount.setText(String.valueOf(wish.getSale_percent()) + "%");
        this.prdPrice.setText(String.valueOf(wish.getPrd_price()) + "원");

        // 상품 썸네일 이미지 받아오기
        WishService.loadImage(wish.getPrd_no(), pgImg);
    }

}
