package com.appteam4.postella.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appteam4.postella.R;
import com.appteam4.postella.dto.Product;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<RecomendViewHolder> {
    private static final String TAG = "SearchAdapter";
    List<Product> list = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public RecomendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // LayoutInflater를 사용하여 XML 레이아웃 파일을 View 객체로 인플레이트
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.reco_prod_item, parent, false);

        // 새로운 MainViewHolder 객체를 생성하고 반환, RecyclerView 항목의 뷰와 이벤트 처리를 관리
        RecomendViewHolder recomendViewHolder = new RecomendViewHolder(itemView, onItemClickListener);
        return recomendViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecomendViewHolder holder, int position) {
        // 해당 위치(position)의 Product 객체를 가져옴
        Product product = list.get(position);
        // ViewHolder에 데이터를 설정하여 화면에 표시
        holder.setData(product);
    }

    @Override
    public int getItemCount() {return list.size();}
    public void setList(List<Product> list){this.list = list;}

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(SearchAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public Product getItem(int position) {return list.get(position);}
}
