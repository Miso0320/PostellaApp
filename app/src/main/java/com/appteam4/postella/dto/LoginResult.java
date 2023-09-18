package com.appteam4.postella.dto;

public class LoginResult {

    private String result;
    private String us_email;
    private String us_password;

    @Override
    public String toString() {
        return "LoginResult{" +
                "result='" + result + '\'' +
                ", us_email='" + us_email + '\'' +
                ", us_password='" + us_password + '\'' +
                '}';
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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
}
