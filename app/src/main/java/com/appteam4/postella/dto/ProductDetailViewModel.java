package com.appteam4.postella.dto;

import androidx.lifecycle.ViewModel;

public class ProductDetailViewModel extends ViewModel {
    private int imgCnt;

    public int getImgCnt() {
        return imgCnt;
    }

    public void setImgCnt(int imgCnt) {
        this.imgCnt = imgCnt;
    }
}
