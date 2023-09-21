package com.appteam4.postella.ui;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appteam4.postella.R;
import com.appteam4.postella.datastore.AppKeyValueStore;
import com.appteam4.postella.dto.Product;
import com.appteam4.postella.service.ProductGroupService;

import java.text.DecimalFormat;

public class RecomendViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "RecomendViewHolder";
    private int pg_no;
    private ImageView imgProdAttach;
    private TextView prodPrice;
    private TextView prodDiscount;
    private TextView prodName;
    private RatingBar starAvg;
    private TextView ratingScore;
    private TextView numOfReviews;

    public RecomendViewHolder(@NonNull View itemView, SearchAdapter.OnItemClickListener onItemClickListener) {
        super(itemView);
        //아이템 UI 얻기
        imgProdAttach = (ImageView) itemView.findViewById(R.id.img_reco_prod);
        prodPrice = (TextView) itemView.findViewById(R.id.txt_reco_prod_price);
        prodDiscount = (TextView) itemView.findViewById(R.id.txt_reco_prod_discount);
        prodName = (TextView) itemView.findViewById(R.id.txt_reco_prod_name);
        starAvg = (RatingBar)itemView.findViewById(R.id.reco_prod_rating);
        ratingScore = (TextView)itemView.findViewById(R.id.reco_prod_ratingScore);
        numOfReviews = (TextView)itemView.findViewById(R.id.reco_prod_ratingCountByProduct);

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
        starAvg.setRating(product.getPrd_star_avg());
        Log.i(TAG, "setData: " +  product.getPrd_star_avg());
        DecimalFormat dfScore = new DecimalFormat("#.#");
        ratingScore.setText(String.valueOf(product.getPrd_star_avg()));
        numOfReviews.setText("(" + String.valueOf(product.getPrd_review_num()) + ")");
    }
}
