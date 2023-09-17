package com.appteam4.postella.ui;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appteam4.postella.R;
import com.appteam4.postella.dto.Product;
import com.appteam4.postella.service.ProductGroupService;

import java.text.DecimalFormat;

public class MainViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "MainViewHolder";

    private int pg_no;
    private ImageView imgProdAttach;
    private TextView prodPrice;
    private TextView prodDiscount;
    private TextView prodName;
    public CheckBox checkboxItem; // 하트 체크박스 체크 여부를 위함

    private MainAdapter.OnItemClickListener listener; // 인터페이스 객체 추가

    public MainViewHolder (@NonNull View itemView, MainAdapter.OnItemClickListener onItemClickListener) {
        super(itemView);
        //아이템 UI 얻기
        imgProdAttach = (ImageView) itemView.findViewById(R.id.img_prod_attach);
        prodPrice = (TextView) itemView.findViewById(R.id.txt_reco_prod_price);
        prodDiscount = (TextView) itemView.findViewById(R.id.txt_reco_prod_discount);
        prodName = (TextView) itemView.findViewById(R.id.txt_prod_name);
        checkboxItem = itemView.findViewById(R.id.checkbox_favorite); // 레이아웃 XML에서 CheckBox 뷰 찾아서 초기화
        // 인터페이스 리스너 설정
        this.listener = listener;
        //클릭 이벤트 처리
        itemView.setOnClickListener(v -> {
            Log.i(TAG, "prodNo : " + pg_no);
            onItemClickListener.onItemClick(v, getAdapterPosition());
        });
    }

    public void setData(Product product){
        pg_no = product.getPg_no();
        ProductGroupService.loadImage(product.getPg_no(), imgProdAttach);
        //상품 가격
        DecimalFormat df = new DecimalFormat("#,###");
        prodPrice.setText(df.format(product.getPrd_price()));
        //할인율 계산
        int topPrdPrice = product.getPrd_org_price();
        int topPrdSaleprice = product.getPrd_price();
        double salePercent = (double)(topPrdPrice - topPrdSaleprice) / topPrdPrice * 100 ;
        int intSalePercent = (int)salePercent;
        //할인 하고 있는 상품만 할인율 표시
        if(intSalePercent != 0){
            prodDiscount.setText(String.valueOf(intSalePercent)+ "%");
        }
        prodName.setText(product.getPg_name());
    }
}
