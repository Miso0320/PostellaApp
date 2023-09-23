package com.appteam4.postella.dto;

import java.io.Serializable;

public class DeliveryAddress implements Serializable {
    private int da_no;                // 주소 식별번호
    private String da_type;           // 구매자인지 판매자인지 확인, 구매자:C, 판매자:S
    private Integer us_no;            // 유저식별번호
    private Integer sel_no;           // 판매자식별번호
    private String da_name;           // 받는 사람 이름
    private String da_tel;            // 받는 사람 전화번호
    private String da_postcode;       // 우편번호
    private String da_adr;            // 주소
    private String da_detail_adr;     // 상세주소
    private String da_main;           // 기본배송지 여부, Y/N
    private String da_req_type;       // 배송 요청사항, 코드테이블 사용

    public int getDa_no() {
        return da_no;
    }

    public void setDa_no(int da_no) {
        this.da_no = da_no;
    }

    public String getDa_type() {
        return da_type;
    }

    public void setDa_type(String da_type) {
        this.da_type = da_type;
    }

    public Integer getUs_no() {
        return us_no;
    }

    public void setUs_no(Integer us_no) {
        this.us_no = us_no;
    }

    public Integer getSel_no() {
        return sel_no;
    }

    public void setSel_no(Integer sel_no) {
        this.sel_no = sel_no;
    }

    public String getDa_name() {
        return da_name;
    }

    public void setDa_name(String da_name) {
        this.da_name = da_name;
    }

    public String getDa_tel() {
        return da_tel;
    }

    public void setDa_tel(String da_tel) {
        this.da_tel = da_tel;
    }

    public String getDa_postcode() {
        return da_postcode;
    }

    public void setDa_postcode(String da_postcode) {
        this.da_postcode = da_postcode;
    }

    public String getDa_adr() {
        return da_adr;
    }

    public void setDa_adr(String da_adr) {
        this.da_adr = da_adr;
    }

    public String getDa_detail_adr() {
        return da_detail_adr;
    }

    public void setDa_detail_adr(String da_detail_adr) {
        this.da_detail_adr = da_detail_adr;
    }

    public String getDa_main() {
        return da_main;
    }

    public void setDa_main(String da_main) {
        this.da_main = da_main;
    }

    public String getDa_req_type() {
        return da_req_type;
    }

    public void setDa_req_type(String da_req_type) {
        this.da_req_type = da_req_type;
    }

    @Override
    public String toString() {
        return "DeliveryAddress{" +
                "da_no=" + da_no +
                ", da_type='" + da_type + '\'' +
                ", us_no=" + us_no +
                ", sel_no=" + sel_no +
                ", da_name='" + da_name + '\'' +
                ", da_tel='" + da_tel + '\'' +
                ", da_postcode='" + da_postcode + '\'' +
                ", da_adr='" + da_adr + '\'' +
                ", da_detail_adr='" + da_detail_adr + '\'' +
                ", da_main='" + da_main + '\'' +
                ", da_req_type='" + da_req_type + '\'' +
                '}';
    }
}
