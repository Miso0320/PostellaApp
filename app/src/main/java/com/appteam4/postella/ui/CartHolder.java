package com.appteam4.postella.ui;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
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
    private TextView cartPrdName;
    private TextView cartProdPrice;
    private TextView cartQty;
    private AppCompatButton btnMinus;

    public CartHolder(@NonNull View itemView, CartAdapter.CartOnItemClickListener onItemClickListener) {
        super(itemView);
        //아이템 UI 얻기
        this.cartImage = (ImageView) itemView.findViewById(R.id.img_cart_attach);
        this.cartTitle = (TextView) itemView.findViewById(R.id.cart_title);
        this.cartPrice = (TextView) itemView.findViewById(R.id.cart_price);
        this.cartPrdName = (TextView) itemView.findViewById(R.id.cart_prd_name);
        this.cartProdPrice = (TextView) itemView.findViewById(R.id.cart_total_price);
        this.cartQty = (TextView) itemView.findViewById(R.id.cart_qty);
        this.btnMinus = (AppCompatButton) itemView.findViewById(R.id.btn_minus);

        //클릭 이벤트 처리
        itemView.setOnClickListener(v -> {
            Log.i(TAG, "cartNo : " + cartNo);
            onItemClickListener.onItemClick(v, getAdapterPosition());
        });

        btnMinus.setOnClickListener(v -> {
            onItemClickListener.btnMinusClick(v, getAdapterPosition());
        });
    }

    public void setCartData(Cart cart) {
        cartNo = cart.getCartNo();
        CartService.loadImage(cart.getPrd_no(), cartImage);
        cartTitle.setText(cart.getPg_name());
        cartPrdName.setText(cart.getPrd_name());

        DecimalFormat df = new DecimalFormat("#,###");
        cartPrice.setText(df.format(cart.getPrd_price()) + "원");

        int totalPrice = cart.getPrd_price() * cart.getCrt_qty();
        cartProdPrice.setText("총 " + df.format(totalPrice) + "원");
        cartQty.setText(df.format(cart.getCrt_qty()));
    }
}
