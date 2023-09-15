package com.appteam4.postella.dto;

public class User {
    private int us_no;
    private String us_email;
    private String us_password;
    private String us_name;
    private String us_tel;
    private long us_insertdate;

    public User() {
    }

    public User(int us_no, String us_email, String us_password, String us_name, String us_tel, long us_insertdate) {
        this.us_no = us_no;
        this.us_email = us_email;
        this.us_password = us_password;
        this.us_name = us_name;
        this.us_tel = us_tel;
        this.us_insertdate = us_insertdate;
    }

    public int getUs_no() {
        return us_no;
    }

    public void setUs_no(int us_no) {
        this.us_no = us_no;
    }

    public String getUs_email() {
        return us_email;
    }

    public void setUs_email(String us_email) {
        this.us_email = us_email;
    }

    public String getUs_password() {
        return us_password;
    }

    public void setUs_password(String us_password) {
        this.us_password = us_password;
    }

    public String getUs_name() {
        return us_name;
    }

    public void setUs_name(String us_name) {
        this.us_name = us_name;
    }

    public String getUs_tel() {
        return us_tel;
    }

    public void setUs_tel(String us_tel) {
        this.us_tel = us_tel;
    }

    public long getUs_insertdate() {
        return us_insertdate;
    }

    public void setUs_insertdate(long us_insertdate) {
        this.us_insertdate = us_insertdate;
    }

    @Override
    public String toString() {
        return "User{" +
                "us_no=" + us_no +
                ", us_email='" + us_email + '\'' +
                ", us_password='" + us_password + '\'' +
                ", us_name='" + us_name + '\'' +
                ", us_tel='" + us_tel + '\'' +
                ", us_insertdate=" + us_insertdate +
                '}';
    }
}
