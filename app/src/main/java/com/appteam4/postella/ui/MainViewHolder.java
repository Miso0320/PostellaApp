package com.appteam4.postella.ui;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import com.appteam4.postella.R;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class MainViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "MainViewHolder";

    private int prodNo;
    private ImageView imgProdAttach;
    private TextView prodPrice;
    private TextView prodDiscount;
    private TextView prodName;

    public MainViewHolder(@NonNull View itemView, AdapterView.OnItemClickListener onItemClickListener) {
        super(itemView);
        //아이템 UI 얻기
        imgProdAttach = (ImageView) itemView.findViewById(R.id.img_prod_attach);
        prodPrice = (TextView) itemView.findViewById(R.id.txt_prod_price);
        prodDiscount = (TextView) itemView.findViewById(R.id.txt_prod_discount);
        prodName = (TextView) itemView.findViewById(R.id.txt_prod_name);

        //클릭 이벤트 처리
        itemView.setOnClickListener(v -> {
            Log.i(TAG, "prodNo : " + prodNo);
            //pg_no가 애매한 열유형이라고 검색이 안돼서 같이 상의 해봐야할거 같앙..
            //전체 상품을 불러 올건데 pg_no가 같은 상품은 가장 위 하나만 가져오도록 sql문을 짰어
            /*SELECT pg_name, pg_imgfile, prd_price, prd_org_price
            FROM (
                    SELECT p.*, pg.*,
                    ROW_NUMBER() OVER (PARTITION BY pg.pg_no ORDER BY ROWNUM) AS rn
                    FROM product p
                    JOIN product_group pg ON p.pg_no = pg.pg_no
            )
            WHERE rn = 1*/
            //아마 외래키가 아니어서 컬럼 이름이 같아서 그런거 같은데 후움~
        });
    }
}
