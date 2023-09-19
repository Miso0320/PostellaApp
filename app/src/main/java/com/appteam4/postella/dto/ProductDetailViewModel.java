package com.appteam4.postella.dto;

import androidx.lifecycle.ViewModel;

public class ProductDetailViewModel extends ViewModel {
    private int imgCnt;
    private int pgNo;

    public int getPgNo() {
        return pgNo;
    }

    public void setPgNo(int pgNo) {
        this.pgNo = pgNo;
    }

    public int getImgCnt() {
        return imgCnt;
    }

    public void setImgCnt(int imgCnt) {
        this.imgCnt = imgCnt;
    }
}
