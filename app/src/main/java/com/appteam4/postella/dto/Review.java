package com.appteam4.postella.dto;

import android.widget.RatingBar;
import android.widget.TextView;

public class Review {
    private int rev_no;                     // 리뷰 번호
    private String us_name;                 // 주문자 이름
    private String prd_name;                // 상품 옵션 이름
    private String rev_content;             // 리뷰 내용
    private long rev_date;                  // 리뷰 등록일
    private int prd_no;                     // 옵션 식별번호
    private int od_no;                      // 주문 식별자
    private int od_detail_no;              // 주문상세 식별자
    private int rev_star_rate;             // 별점

    public int getRev_no() {
        return rev_no;
    }

    public void setRev_no(int rev_no) {
        this.rev_no = rev_no;
    }

    public String getUs_name() {
        return us_name;
    }

    public void setUs_name(String us_name) {
        this.us_name = us_name;
    }

    public String getPrd_name() {
        return prd_name;
    }

    public void setPrd_name(String prd_name) {
        this.prd_name = prd_name;
    }

    public String getRev_content() {
        return rev_content;
    }

    public void setRev_content(String rev_content) {
        this.rev_content = rev_content;
    }

    public long getRev_date() {
        return rev_date;
    }

    public void setRev_date(long rev_date) {
        this.rev_date = rev_date;
    }

    public int getPrd_no() {
        return prd_no;
    }

    public void setPrd_no(int prd_no) {
        this.prd_no = prd_no;
    }

    public int getOd_no() {
        return od_no;
    }

    public void setOd_no(int od_no) {
        this.od_no = od_no;
    }

    public int getOd_detail_no() {
        return od_detail_no;
    }

    public void setOd_detail_no(int od_detail_no) {
        this.od_detail_no = od_detail_no;
    }

    public int getRev_star_rate() {
        return rev_star_rate;
    }

    public void setRev_star_rate(int rev_star_rate) {
        this.rev_star_rate = rev_star_rate;
    }

    @Override
    public String toString() {
        return "Review{" +
                "rev_no=" + rev_no +
                ", us_name='" + us_name + '\'' +
                ", prd_name='" + prd_name + '\'' +
                ", rev_content='" + rev_content + '\'' +
                ", rev_date=" + rev_date +
                ", prd_no=" + prd_no +
                ", od_no=" + od_no +
                ", od_detail_no=" + od_detail_no +
                ", rev_star_rate=" + rev_star_rate +
                '}';
    }
}
