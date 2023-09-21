package com.appteam4.postella.dto;

public class Inquiry {
    private int qna_no;            // 문의게시글 번호
    private String q_content;      // 상품문의내용
    private long q_date;           // 문의 작성 시간
    private String a_content;      // 답변 작성 내용
    private String status;         // 답변여부
    private String us_name;        // 작성자명

    public int getQna_no() {
        return qna_no;
    }

    public void setQna_no(int qna_no) {
        this.qna_no = qna_no;
    }

    public String getQ_content() {
        return q_content;
    }

    public void setQ_content(String q_content) {
        this.q_content = q_content;
    }

    public long getQ_date() {
        return q_date;
    }

    public void setQ_date(long q_date) {
        this.q_date = q_date;
    }

    public String getA_content() {
        return a_content;
    }

    public void setA_content(String a_content) {
        this.a_content = a_content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUs_name() {
        return us_name;
    }

    public void setUs_name(String us_name) {
        this.us_name = us_name;
    }

    @Override
    public String toString() {
        return "Inquiry{" +
                "qna_no=" + qna_no +
                ", q_content='" + q_content + '\'' +
                ", q_date=" + q_date +
                ", a_content='" + a_content + '\'' +
                ", status='" + status + '\'' +
                ", us_name='" + us_name + '\'' +
                '}';
    }
}
