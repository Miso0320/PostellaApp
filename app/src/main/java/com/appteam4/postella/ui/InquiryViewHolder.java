package com.appteam4.postella.ui;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appteam4.postella.R;
import com.appteam4.postella.dto.Inquiry;

public class InquiryViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "InquiryViewHolder";
    private int qnaNo = 1;
    private TextView qContent;
    private TextView qnaStatus;
    private TextView usNo;
    private TextView qDate;


    public InquiryViewHolder(@NonNull View itemView) {
        super(itemView);
        this.qContent = itemView.findViewById(R.id.q_content);
        this.qnaStatus = itemView.findViewById(R.id.qna_status);
        this.usNo = itemView.findViewById(R.id.us_no);
        this.qDate = itemView.findViewById(R.id.q_date);
    }

    public void setData(Inquiry inquiry) {
        this.qnaNo = inquiry.getQnaNo();
        this.qContent.setText(inquiry.getqContent());
        this.qnaStatus.setText("답변대기");
        this.qDate.setText(inquiry.getqDate());
        /*this.usNo.setText(inquiry.getUsNo());
        */
    }

}
