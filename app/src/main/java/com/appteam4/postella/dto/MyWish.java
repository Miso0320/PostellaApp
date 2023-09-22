package com.appteam4.postella.dto;

public class MyWish {
    private int pg_no;                // 상품그룹 식별번호
    private int prd_no;                // 상품옵션 식별번호
    private String pg_name;           // 상품그룹명
    private int prd_org_price;        // 원가
    private int prd_price;            // 판매가
    private int sale_percent;         // 할인율
    private int us_no;                // 유저식별번호

    public int getPg_no() {
        return pg_no;
    }

    public void setPg_no(int pg_no) {
        this.pg_no = pg_no;
    }

    public int getPrd_no() {
        return prd_no;
    }

    public void setPrd_no(int prd_no) {
        this.prd_no = prd_no;
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

    public int getSale_percent() {
        return sale_percent;
    }

    public void setSale_percent(int sale_percent) {
        this.sale_percent = sale_percent;
    }

    public int getUs_no() {
        return us_no;
    }

    public void setUs_no(int us_no) {
        this.us_no = us_no;
    }

    @Override
    public String toString() {
        return "MyWish{" +
                "pg_no=" + pg_no +
                ", prd_no=" + prd_no +
                ", pg_name='" + pg_name + '\'' +
                ", prd_org_price=" + prd_org_price +
                ", prd_price=" + prd_price +
                ", sale_percent=" + sale_percent +
                ", us_no=" + us_no +
                '}';
    }
}
