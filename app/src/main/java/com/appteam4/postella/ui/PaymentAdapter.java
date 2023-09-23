package com.appteam4.postella.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appteam4.postella.R;
import com.appteam4.postella.dto.MyPageOrderList;

import java.util.ArrayList;
import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentViewHolder> {
    private List<MyPageOrderList> orderList = new ArrayList<>();

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.order_complete_list_item, parent, false);
        PaymentViewHolder paymentViewHolder = new PaymentViewHolder(itemView);
        return paymentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        MyPageOrderList order = orderList.get(position);
        holder.setData(order);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void setOrderList(List<MyPageOrderList> orderList) {
        this.orderList = orderList;
    }
}
