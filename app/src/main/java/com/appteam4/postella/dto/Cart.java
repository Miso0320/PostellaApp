package com.appteam4.postella.dto;

import java.io.Serializable;
import java.util.Date;

public class Cart implements Serializable {
    private int cartNo;
    private int CartImage;
    private long od_date;       //주문날짜
    private long cartArrivalDate;
    private int cartProdPrice;
    private int us_no; 			//사용자 식별번호
    private int prd_no; 		//상품 식별번호
    private int crt_qty; 		//장바구니에 담긴 상품에 수량
    private int prd_price;		//상품 가격
    private int prd_ship_fee;	//배송비
    private String prd_name;	//상품 이름
    private String img_type;	//img 타입
    private String encodedFile;	//encoded된 이미지파일
    private String pg_name;		//상품 대분류 이름

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

    public long getOd_date() {
        return od_date;
    }

    public void setOd_date(long od_date) {
        this.od_date = od_date;
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

    public int getUs_no() {
        return us_no;
    }

    public void setUs_no(int us_no) {
        this.us_no = us_no;
    }

    public int getPrd_no() {
        return prd_no;
    }

    public void setPrd_no(int prd_no) {
        this.prd_no = prd_no;
    }

    public int getCrt_qty() {
        return crt_qty;
    }

    public void setCrt_qty(int crt_qty) {
        this.crt_qty = crt_qty;
    }

    public int getPrd_price() {
        return prd_price;
    }

    public void setPrd_price(int prd_price) {
        this.prd_price = prd_price;
    }

    public int getPrd_ship_fee() {
        return prd_ship_fee;
    }

    public void setPrd_ship_fee(int prd_ship_fee) {
        this.prd_ship_fee = prd_ship_fee;
    }

    public String getPrd_name() {
        return prd_name;
    }

    public void setPrd_name(String prd_name) {
        this.prd_name = prd_name;
    }

    public String getImg_type() {
        return img_type;
    }

    public void setImg_type(String img_type) {
        this.img_type = img_type;
    }

    public String getEncodedFile() {
        return encodedFile;
    }

    public void setEncodedFile(String encodedFile) {
        this.encodedFile = encodedFile;
    }

    public String getPg_name() {
        return pg_name;
    }

    public void setPg_name(String pg_name) {
        this.pg_name = pg_name;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartNo=" + cartNo +
                ", CartImage=" + CartImage +
                ", od_date=" + od_date +
                ", cartArrivalDate=" + cartArrivalDate +
                ", cartProdPrice=" + cartProdPrice +
                ", us_no=" + us_no +
                ", prd_no=" + prd_no +
                ", crt_qty=" + crt_qty +
                ", prd_price=" + prd_price +
                ", prd_ship_fee=" + prd_ship_fee +
                ", prd_name='" + prd_name + '\'' +
                ", img_type='" + img_type + '\'' +
                ", encodedFile='" + encodedFile + '\'' +
                ", pg_name='" + pg_name + '\'' +
                '}';
    }
}
