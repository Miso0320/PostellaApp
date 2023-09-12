package com.appteam4.postella.dto;

import java.io.Serializable;
import java.util.Date;

public class Cart implements Serializable {
    private int cartNo;
    private int CartImage;
    private String cartTitle;
    private int cartPrice;
    private long cartArrivalDate;
    private int cartProdPrice;

    public int getCartNo() {
        return cartNo;
    }

    public void setCartNo(int cartNo) {
        this.cartNo = cartNo;
    }

    public int getCartImage() {
        return CartImage;
    }

    public void setCartImage(int cartImage) {
        CartImage = cartImage;
    }

    public String getCartTitle() {
        return cartTitle;
    }

    public void setCartTitle(String cartTitle) {
        this.cartTitle = cartTitle;
    }

    public int getCartPrice() {
        return cartPrice;
    }

    public void setCartPrice(int cartPrice) {
        this.cartPrice = cartPrice;
    }

    public long getCartArrivalDate() {
        return cartArrivalDate;
    }

    public void setCartArrivalDate(long cartArrivalDate) {
        this.cartArrivalDate = cartArrivalDate;
    }

    public int getCartProdPrice() {
        return cartProdPrice;
    }

    public void setCartProdPrice(int cartProdPrice) {
        this.cartProdPrice = cartProdPrice;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartNo=" + cartNo +
                ", CartImage=" + CartImage +
                ", cartTitle='" + cartTitle + '\'' +
                ", cartPrice=" + cartPrice +
                ", cartArrivalDate=" + cartArrivalDate +
                ", cartProdPrice=" + cartProdPrice +
                '}';
    }
}
