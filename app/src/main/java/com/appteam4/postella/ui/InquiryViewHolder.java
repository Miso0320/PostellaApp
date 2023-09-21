package com.appteam4.postella.ui;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appteam4.postella.R;
import com.appteam4.postella.dto.Inquiry;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InquiryViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "InquiryViewHolder";
    private int qnaNo = 1;
    private TextView qContent;
    private TextView aContent;
    private TextView qnaStatus;
    private TextView qDate;
    private TextView usName;
    private LinearLayout questionLayout;
    private LinearLayout answerLayout;
    private InquiryAdapter.OnItemClickListener listener;

    public InquiryViewHolder(@NonNull View itemView) {
        super(itemView);
        this.qContent = itemView.findViewById(R.id.q_content);
        this.qnaStatus = itemView.findViewById(R.id.qna_status);
        this.usName = itemView.findViewById(R.id.us_name);
        this.qDate = itemView.findViewById(R.id.q_date);
        this.aContent = itemView.findViewById(R.id.a_content);

        // 뷰 홀더 생성 시 아이템 뷰 내의 특정 뷰(예: question_layout)에 클릭 리스너 설정
        questionLayout = itemView.findViewById(R.id.question_layout);
        answerLayout = itemView.findViewById(R.id.answer_layout);

        questionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answerLayout.getVisibility() == View.VISIBLE) {
                    answerLayout.setVisibility(View.GONE);
                } else {
                    answerLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void setData(Inquiry inquiry) {
        this.qnaNo = inquiry.getQna_no();
        this.qContent.setText(inquiry.getQ_content());
        this.qnaStatus.setText(inquiry.getStatus());
        Date date = new Date(inquiry.getQ_date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String strDate = sdf.format(date);
        this.qDate.setText(strDate);
        this.usName.setText(inquiry.getUs_name());
        this.aContent.setText(inquiry.getA_content());
    }

    public TextView getQnaStatus() {
        return qnaStatus;
    }
}
