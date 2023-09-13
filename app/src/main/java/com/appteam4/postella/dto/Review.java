package com.appteam4.postella.dto;

import android.widget.RatingBar;
import android.widget.TextView;

public class Review {
    private int revNo;
    private String usName;
    private String prdName;
    private String revContent;
    private String revDate;
    private int prdNo;
    private int odNo;
    private int odDetailNo;
    private int rating;

    public int getRevNo() {
        return revNo;
    }

    public void setRevNo(int revNo) {
        this.revNo = revNo;
    }

    public String getUsName() {
        return usName;
    }

    public void setUsName(String usName) {
        this.usName = usName;
    }

    public String getPrdName() {
        return prdName;
    }

    public void setPrdName(String prdName) {
        this.prdName = prdName;
    }

    public String getRevContent() {
        return revContent;
    }

    public void setRevContent(String revContent) {
        this.revContent = revContent;
    }

    public String getRevDate() {
        return revDate;
    }

    public void setRevDate(String revDate) {
        this.revDate = revDate;
    }

    public int getPrdNo() {
        return prdNo;
    }

    public void setPrdNo(int prdNo) {
        this.prdNo = prdNo;
    }

    public int getOdNo() {
        return odNo;
    }

    public void setOdNo(int odNo) {
        this.odNo = odNo;
    }

    public int getOdDetailNo() {
        return odDetailNo;
    }

    public void setOdDetailNo(int odDetailNo) {
        this.odDetailNo = odDetailNo;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}
