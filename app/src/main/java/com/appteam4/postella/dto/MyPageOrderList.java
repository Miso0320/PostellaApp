package com.appteam4.postella.dto;

import java.io.Serializable;

public class MyPageOrderList implements Serializable {
    private int orderListNo;
    private int orderListImage;
    private String orderListTitle;
    private int orderListPrice;
    private String orderListStatus;

    public int getOrderListNo() {
        return orderListNo;
    }

    public void setOrderListNo(int orderListNo) {
        this.orderListNo = orderListNo;
    }

    public int getOrderListImage() {
        return orderListImage;
    }

    public void setOrderListImage(int orderListImage) {
        this.orderListImage = orderListImage;
    }

    public String getOrderListTitle() {
        return orderListTitle;
    }

    public void setOrderListTitle(String orderListTitle) {
        this.orderListTitle = orderListTitle;
    }

    public int getOrderListPrice() {
        return orderListPrice;
    }

    public void setOrderListPrice(int orderListPrice) {
        this.orderListPrice = orderListPrice;
    }

    public String getOrderListStatus() {
        return orderListStatus;
    }

    public void setOrderListStatus(String orderListStatus) {
        this.orderListStatus = orderListStatus;
    }

    @Override
    public String toString() {
        return "MyPageOrderList{" +
                "orderListNo=" + orderListNo +
                ", orderListImage=" + orderListImage +
                ", orderListTitle='" + orderListTitle + '\'' +
                ", orderListPrice=" + orderListPrice +
                ", orderListStatus='" + orderListStatus + '\'' +
                '}';
    }
}
