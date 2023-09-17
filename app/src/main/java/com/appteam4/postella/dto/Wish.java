package com.appteam4.postella.dto;

import java.io.Serializable;

public class Wish implements Serializable {
    private int us_no; //회원 식별번호

    public int getUs_no() {
        return us_no;
    }

    public void setUs_no(int us_no) {
        this.us_no = us_no;
    }

    @Override
    public String toString() {
        return "Wish{" +
                "us_no=" + us_no +
                ", img_type='" + img_type + '\'' +
                ", encodedFile='" + encodedFile + '\'' +
                ", pg_no=" + pg_no +
                ", pg_name='" + pg_name + '\'' +
                ", prd_org_price=" + prd_org_price +
                ", prd_price=" + prd_price +
                '}';
    }

    // 이미지 파일 불러오기
    private String img_type;            // img 타입
    private String encodedFile;            // encoded된 이미지파일
    // 상품 대분류
    private int pg_no;                    // 상품 대분류 식별번호
    private String pg_name;                // 상품 대분류 이름
    private int prd_org_price;            // 정상가
    private int prd_price;                // 판매가

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

    public int getPg_no() {
        return pg_no;
    }

    public void setPg_no(int pg_no) {
        this.pg_no = pg_no;
    }

    public String getPg_name() {
        return pg_name;
    }

    public void setPg_name(String pg_name) {
        this.pg_name = pg_name;
    }

    public int getPrd_org_price() {
        return prd_org_price;
    }

    public void setPrd_org_price(int prd_org_price) {
        this.prd_org_price = prd_org_price;
    }

    public int getPrd_price() {
        return prd_price;
    }

    public void setPrd_price(int prd_price) {
        this.prd_price = prd_price;
    }

}
