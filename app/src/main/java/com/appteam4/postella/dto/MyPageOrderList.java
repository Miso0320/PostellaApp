package com.appteam4.postella.dto;

import java.io.Serializable;

public class MyPageOrderList implements Serializable {
    private int od_no;					// 주문번호
    private int us_no;					// 회원고유번호
    private long od_date;				// 주문날짜
    private String od_status;			// 주문상태
    private int od_item_cnt;			// 주문상품 종류의 개수
    private long od_arrived_date;		// 구매자가 배송받은 날짜
    private int pg_no;

    private String prd_name;			// 상품명
    private int prd_no;					// 상품고유번호
    private int od_detail_qty;			// 개별 상품 개수
    private int od_detail_price;		// 개별 상품 금액
    private int od_detail_no;			// 개별 상품 식별번호
    private String img_type;			// 이미지 MIME 타입
    private String encodedFile;			// 인코딩된 이미지

    public int getOd_no() {
        return od_no;
    }

    public void setOd_no(int od_no) {
        this.od_no = od_no;
    }

    public int getUs_no() {
        return us_no;
    }

    public void setUs_no(int us_no) {
        this.us_no = us_no;
    }

    public long getOd_date() {
        return od_date;
    }

    public void setOd_date(long od_date) {
        this.od_date = od_date;
    }

    public String getOd_status() {
        return od_status;
    }

    public void setOd_status(String od_status) {
        this.od_status = od_status;
    }

    public int getOd_item_cnt() {
        return od_item_cnt;
    }

    public void setOd_item_cnt(int od_item_cnt) {
        this.od_item_cnt = od_item_cnt;
    }

    public long getOd_arrived_date() {
        return od_arrived_date;
    }

    public void setOd_arrived_date(long od_arrived_date) {
        this.od_arrived_date = od_arrived_date;
    }

    public int getPg_no() {
        return pg_no;
    }

    public void setPg_no(int pg_no) {
        this.pg_no = pg_no;
    }

    public String getPrd_name() {
        return prd_name;
    }

    public void setPrd_name(String prd_name) {
        this.prd_name = prd_name;
    }

    public int getPrd_no() {
        return prd_no;
    }

    public void setPrd_no(int prd_no) {
        this.prd_no = prd_no;
    }

    public int getOd_detail_qty() {
        return od_detail_qty;
    }

    public void setOd_detail_qty(int od_detail_qty) {
        this.od_detail_qty = od_detail_qty;
    }

    public int getOd_detail_price() {
        return od_detail_price;
    }

    public void setOd_detail_price(int od_detail_price) {
        this.od_detail_price = od_detail_price;
    }

    public int getOd_detail_no() {
        return od_detail_no;
    }

    public void setOd_detail_no(int od_detail_no) {
        this.od_detail_no = od_detail_no;
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

    @Override
    public String toString() {
        return "MyPageOrderList{" +
                "od_no=" + od_no +
                ", us_no=" + us_no +
                ", od_date=" + od_date +
                ", od_status='" + od_status + '\'' +
                ", od_item_cnt=" + od_item_cnt +
                ", od_arrived_date=" + od_arrived_date +
                ", pg_no=" + pg_no +
                ", prd_name='" + prd_name + '\'' +
                ", prd_no=" + prd_no +
                ", od_detail_qty=" + od_detail_qty +
                ", od_detail_price=" + od_detail_price +
                ", od_detail_no=" + od_detail_no +
                ", img_type='" + img_type + '\'' +
                ", encodedFile='" + encodedFile + '\'' +
                '}';
    }
}
