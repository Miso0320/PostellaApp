package com.appteam4.postella.dto;

import java.io.Serializable;

public class Payment implements Serializable {
    private int od_no;            // 주문번호
    private int po_no;            // 포인트 식별번호
    private String pay_method;    // 결제수단, 코드테이블 사용
    private long pay_date;        // 결제날짜
    private String pay_detail;    // 결제방법 상세
    private int total_price;      // 전체가격

    public int getOd_no() {
        return od_no;
    }

    public void setOd_no(int od_no) {
        this.od_no = od_no;
    }

    public int getPo_no() {
        return po_no;
    }

    public void setPo_no(int po_no) {
        this.po_no = po_no;
    }

    public String getPay_method() {
        return pay_method;
    }

    public void setPay_method(String pay_method) {
        this.pay_method = pay_method;
    }

    public long getPay_date() {
        return pay_date;
    }

    public void setPay_date(long pay_date) {
        this.pay_date = pay_date;
    }

    public String getPay_detail() {
        return pay_detail;
    }

    public void setPay_detail(String pay_detail) {
        this.pay_detail = pay_detail;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "od_no=" + od_no +
                ", po_no=" + po_no +
                ", pay_method='" + pay_method + '\'' +
                ", pay_date=" + pay_date +
                ", pay_detail='" + pay_detail + '\'' +
                ", total_price=" + total_price +
                '}';
    }
}
