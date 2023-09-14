package com.appteam4.postella.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appteam4.postella.R;
import com.appteam4.postella.dto.Inquiry;

import java.util.ArrayList;
import java.util.List;

public class InquiryAdapter extends RecyclerView.Adapter<InquiryViewHolder> {
    private static final String TAG = "InquiryAdapter";
    private List<Inquiry> inquiryList = new ArrayList<>();


    @NonNull
    @Override
    public InquiryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.inquiry_list_item, parent, false);
        InquiryViewHolder inquiryViewHolder = new InquiryViewHolder(itemView);
        return inquiryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InquiryViewHolder holder, int position) {
        Inquiry inquiry = inquiryList.get(position);
        holder.setData(inquiry);
    }

    @Override
    public int getItemCount() {
        return inquiryList.size();
    }

    public void setInquiryList(List<Inquiry> inquiryList) {
        this.inquiryList = inquiryList;
    }

    public void addInquiry(Inquiry inquiry) {
        inquiryList.add(inquiry);
    }
}
