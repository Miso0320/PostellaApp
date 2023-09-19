package com.appteam4.postella.ui;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appteam4.postella.R;
import com.appteam4.postella.dto.MyPageOrderList;
import com.appteam4.postella.dto.Product;
import com.appteam4.postella.service.MyPageOrderListService;
import com.appteam4.postella.service.ProductGroupService;

import java.text.DecimalFormat;
import java.util.Currency;

public class MyPageOrderListHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "MyPageOrderListHolder";

    private int orderListNo;
    private ImageView orderListImage;
    private TextView orderListTitle;
    private TextView orderListPrice;
    private TextView orderListStatus;

    public MyPageOrderListHolder (@NonNull View itemView, MyPageOrderListAdapter.OnItemClickListener onItemClickListener) {
        super(itemView);
        //아이템 UI 얻기
        orderListImage = (ImageView) itemView.findViewById(R.id.order_attach);
        orderListTitle = (TextView) itemView.findViewById(R.id.order_title);
        orderListPrice = (TextView) itemView.findViewById(R.id.order_price);
        orderListStatus = (TextView) itemView.findViewById(R.id.order_status);

        //클릭 이벤트 처리
        itemView.setOnClickListener(v -> {
            Log.i(TAG, "orderListNo : " + orderListNo);
            onItemClickListener.onItemClick(v, getAdapterPosition());
        });
    }

    public void setMyPageOrderListData(MyPageOrderList myPageOrderList) {
        orderListNo = myPageOrderList.getOd_no();
        MyPageOrderListService.loadImage(myPageOrderList.getPrd_no(), orderListImage);

        Log.i(TAG, "prd_no" + myPageOrderList.getPrd_no());

        orderListTitle.setText(myPageOrderList.getPrd_name());
        DecimalFormat df = new DecimalFormat("#,###");
        orderListPrice.setText(df.format(myPageOrderList.getOd_detail_price()));
        orderListStatus.setText(myPageOrderList.getOd_status());
    }
}
