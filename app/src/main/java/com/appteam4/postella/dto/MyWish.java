package com.appteam4.postella.dto;

public class MyWish {
    private int pg_no; // 상품(옵션) 식별
    private int us_no; // 유저 식별

    @Override
    public String toString() {
        return "MyWish{" +
                "pg_no=" + pg_no +
                ", us_no=" + us_no +
                '}';
    }

    public int getPg_no() {
        return pg_no;
    }

    public void setPg_no(int pg_no) {
        this.pg_no = pg_no;
    }

    public int getUs_no() {
        return us_no;
    }

    public void setUs_no(int us_no) {
        this.us_no = us_no;
    }
}
