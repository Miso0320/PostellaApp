package com.appteam4.postella.ui;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appteam4.postella.R;
import com.appteam4.postella.dto.Inquiry;

import java.util.ArrayList;
import java.util.List;


public class InquiryAdapter extends RecyclerView.Adapter<InquiryViewHolder> {
    private static final String TAG = "InquiryAdapter";
    private List<Inquiry> inquiryList = new ArrayList<>();
    // 아이템 클릭 리스너
    private OnItemClickListener onItemClickListener;
    View itemView;

    @NonNull
    @Override
    public InquiryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        itemView = layoutInflater.inflate(R.layout.inquiry_list_item, parent, false);
        InquiryViewHolder inquiryViewHolder = new InquiryViewHolder(itemView);
        return inquiryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InquiryViewHolder holder, int position) {
        Inquiry inquiry = inquiryList.get(position);

        // qnaStatus의 값 받아오기
        TextView qnaStatus = holder.getQnaStatus();

        // 답변완료의 경우 텍스트 색상 변경
        if (inquiry.getStatus().equals("답변완료")) {
            qnaStatus.setTextColor(Color.parseColor("#7E57C2"));
        }

        holder.setData(inquiry);
    }

    @Override
    public int getItemCount() {
        return inquiryList.size();
    }

    public void setInquiryList(List<Inquiry> inquiryList) {
        this.inquiryList = inquiryList;
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
