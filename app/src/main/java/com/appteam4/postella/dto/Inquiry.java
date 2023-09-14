package com.appteam4.postella.dto;

public class Inquiry {
    private int qnaNo;
    private int usNo;
    private int pgNo;
    private int prdNo;
    private String qContent;
    private String qDate;
    private String aContent;
    private String aDate;

    public int getQnaNo() {
        return qnaNo;
    }

    public void setQnaNo(int qnaNo) {
        this.qnaNo = qnaNo;
    }

    public int getUsNo() {
        return usNo;
    }

    public void setUsNo(int usNo) {
        this.usNo = usNo;
    }

    public int getPgNo() {
        return pgNo;
    }

    public void setPgNo(int pgNo) {
        this.pgNo = pgNo;
    }

    public int getPrdNo() {
        return prdNo;
    }

    public void setPrdNo(int prdNo) {
        this.prdNo = prdNo;
    }

    public String getqContent() {
        return qContent;
    }

    public void setqContent(String qContent) {
        this.qContent = qContent;
    }

    public String getqDate() {
        return qDate;
    }

    public void setqDate(String qDate) {
        this.qDate = qDate;
    }

    public String getaContent() {
        return aContent;
    }

    public void setaContent(String aContent) {
        this.aContent = aContent;
    }

    public String getaDate() {
        return aDate;
    }

    public void setaDate(String aDate) {
        this.aDate = aDate;
    }
}
