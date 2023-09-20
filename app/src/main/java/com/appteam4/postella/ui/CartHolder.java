package com.appteam4.postella.ui;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appteam4.postella.R;
import com.appteam4.postella.dto.Cart;
import com.appteam4.postella.service.CartService;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CartHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "CartHolder";

    private int cartNo;
    private ImageView cartImage;
    private TextView cartTitle;
    private TextView cartPrice;
    private TextView cartArrivalDate;
    private TextView cartProdPrice;
    private TextView cartShipFee;

    public CartHolder(@NonNull View itemView, CartAdapter.CartOnItemClickListener onItemClickListener) {
        super(itemView);
        //아이템 UI 얻기
        cartImage = (ImageView) itemView.findViewById(R.id.img_cart_attach);
        cartTitle = (TextView) itemView.findViewById(R.id.cart_title);
        cartPrice = (TextView) itemView.findViewById(R.id.cart_price);
        cartArrivalDate = (TextView) itemView.findViewById(R.id.cart_arrival_date);
        cartProdPrice = (TextView) itemView.findViewById(R.id.cart_total_price);
        cartShipFee = (TextView) itemView.findViewById(R.id.cart_ship_fee);

        //클릭 이벤트 처리
        itemView.setOnClickListener(v -> {
            Log.i(TAG, "cartNo : " + cartNo);
            onItemClickListener.onItemClick(v, getAdapterPosition());
        });
    }

    public void setCartData(Cart cart) {
        cartNo = cart.getCartNo();
        CartService.loadImage(cart.getPrd_no(), cartImage);
        cartTitle.setText(cart.getPrd_name());

        DecimalFormat df = new DecimalFormat("#,###");
        cartPrice.setText(df.format(cart.getPrd_price()));

        Date date = new Date(cart.getCartArrivalDate());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String strDate = sdf.format(date);
        cartArrivalDate.setText(strDate);

        cartProdPrice.setText(df.format(cart.getPrd_price()));
        cartShipFee.setText(df.format(cart.getPrd_ship_fee()));
    }
}
