package com.appteam4.postella.dto;

public class LoginResult {

    private String result;
    private String mid;
    private String mpassword;

    @Override
    public String toString() {
        return "LoginResult{" +
                "result='" + result + '\'' +
                ", mid='" + mid + '\'' +
                ", mpassword='" + mpassword + '\'' +
                '}';
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMpassword() {
        return mpassword;
    }

    public void setMpassword(String mpassword) {
        this.mpassword = mpassword;
    }
}
