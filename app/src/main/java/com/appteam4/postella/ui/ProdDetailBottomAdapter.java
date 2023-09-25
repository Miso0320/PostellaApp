package com.appteam4.postella.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appteam4.postella.R;
import com.appteam4.postella.dto.MyWish;
import com.appteam4.postella.dto.Product;

import java.util.ArrayList;
import java.util.List;

public class ProdDetailBottomAdapter extends RecyclerView.Adapter<ProdDetailBottomViewHolder> {
    private List<Product> productList = new ArrayList<>();

    @NonNull
    @Override
    public ProdDetailBottomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.prod_detail_bottom_list, parent, false);
        ProdDetailBottomViewHolder prodDetailBottomViewHolder = new ProdDetailBottomViewHolder(itemView);
        return prodDetailBottomViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProdDetailBottomViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.setData(product);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
        // 데이터가 변경된 것을 알리기
        notifyDataSetChanged();
    }

    public Product getItem(int position) {
        return productList.get(position);
    }

}
