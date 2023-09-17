package com.appteam4.postella.dto;

public class WishResult {
    private String result;
    private int pg_no;

    @Override
    public String toString() {
        return "AddWishResult{" +
                "result='" + result + '\'' +
                ", pg_no=" + pg_no +
                ", message='" + message + '\'' +
                '}';
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getPg_no() {
        return pg_no;
    }

    public void setPg_no(int pg_no) {
        this.pg_no = pg_no;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
}
