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

public class MyPageOrderListAdapter extends RecyclerView.Adapter<MyPageOrderListHolder> {

    private static final String TAG = "MyPageOrderListAdapter";
    List<MyPageOrderList> orderList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public MyPageOrderListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 사용하여 XML 레이아웃 파일을 View 객체로 인플레이트
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.prod_my_order_list_item, parent, false);

        // 새로운 MyPageOrderListHolder 객체를 생성하고 반환, RecyclerView 항목의 뷰와 이벤트 처리를 관리
        MyPageOrderListHolder myPageOrderListHolder = new MyPageOrderListHolder(itemView, onItemClickListener);
        return myPageOrderListHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyPageOrderListHolder holder, int position) {
        // 해당 위치(position)의 Product 객체를 가져옴
        MyPageOrderList myPageOrderList = orderList.get(position);
        // ViewHolder에 데이터를 설정하여 화면에 표시
        holder.setMyPageOrderListData(myPageOrderList);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void setList(List<MyPageOrderList> orderList){
        this.orderList = orderList;
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MyPageOrderList getItem(int position) {
        return orderList.get(position);
    }

    public void addMyPageOrderList(MyPageOrderList myPageOrderList) {
        orderList.add(myPageOrderList);
    }
}
