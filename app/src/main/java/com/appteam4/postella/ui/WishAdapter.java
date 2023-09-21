package com.appteam4.postella.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appteam4.postella.R;
import com.appteam4.postella.dto.MyWish;
import com.appteam4.postella.dto.Review;

import java.util.ArrayList;
import java.util.List;

public class WishAdapter extends RecyclerView.Adapter<WishViewHolder> {
    private List<MyWish> wishList = new ArrayList<>();

    @NonNull
    @Override
    public WishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.wish_list_item, parent, false);
        WishViewHolder wishViewHolder = new WishViewHolder(itemView);
        return wishViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WishViewHolder holder, int position) {
        MyWish wish = wishList.get(position);
        holder.setData(wish);
    }

    @Override
    public int getItemCount() {
        return wishList.size();
    }

    public void setWishList(List<MyWish> wishList) {
        this.wishList = wishList;
        // 데이터가 변경된 것을 알리기
        notifyDataSetChanged();
    }

    public void addWish(MyWish wish) {
        wishList.add(wish);
    }
}
