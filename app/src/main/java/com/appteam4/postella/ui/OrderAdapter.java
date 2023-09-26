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

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {
    private List<MyPageOrderList> orderList = new ArrayList<>();

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.order_list_item, parent, false);
        OrderViewHolder orderViewHolder = new OrderViewHolder(itemView);
        return orderViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        MyPageOrderList order = orderList.get(position);
        holder.setData(order);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void setOrderList(List<MyPageOrderList> orderList) {
        this.orderList = orderList;
        // 데이터가 변경된 것을 알리기
        notifyDataSetChanged();
    }
}
