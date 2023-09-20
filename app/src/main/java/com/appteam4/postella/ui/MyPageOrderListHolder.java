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

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

public class MyPageOrderListHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "MyPageOrderListHolder";

    private int orderListNo;
    private ImageView orderListImage;
    private TextView orderListTitle;
    private TextView orderListPrice;
    private TextView orderListStatus;
    private TextView orderPrdName;
    private TextView orderDetailQty;
    private TextView orderArrivalDate;

    public MyPageOrderListHolder (@NonNull View itemView, MyPageOrderListAdapter.OnItemClickListener onItemClickListener) {
        super(itemView);
        //아이템 UI 얻기
        orderListImage = (ImageView) itemView.findViewById(R.id.order_attach);
        orderListTitle = (TextView) itemView.findViewById(R.id.order_title);
        orderListPrice = (TextView) itemView.findViewById(R.id.order_price);
        orderListStatus = (TextView) itemView.findViewById(R.id.order_status);
        orderPrdName = (TextView) itemView.findViewById(R.id.order_prd_name);
        orderDetailQty = (TextView) itemView.findViewById(R.id.order_detail_qty);
        orderArrivalDate = (TextView) itemView.findViewById(R.id.order_arrival_date);


        //클릭 이벤트 처리
        itemView.setOnClickListener(v -> {
            Log.i(TAG, "orderListNo : " + orderListNo);
            onItemClickListener.onItemClick(v, getAdapterPosition());
        });
    }

    public void setMyPageOrderListData(MyPageOrderList myPageOrderList) {
        orderListNo = myPageOrderList.getOd_no();
        MyPageOrderListService.loadImage(myPageOrderList.getPrd_no(), orderListImage);
        orderListTitle.setText(myPageOrderList.getPg_name());
        DecimalFormat df = new DecimalFormat("#,###");
        orderListPrice.setText(df.format(myPageOrderList.getOd_detail_price()) + "원");
        orderListStatus.setText(myPageOrderList.getOd_status());
        orderPrdName.setText(myPageOrderList.getPrd_name());
        orderDetailQty.setText(myPageOrderList.getOd_detail_qty() + "개");

        Date date = new Date(myPageOrderList.getOd_arrived_date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String strDate = sdf.format(date);
        orderArrivalDate.setText(strDate);
    }
}
