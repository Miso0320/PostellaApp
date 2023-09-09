package com.appteam4.postella.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appteam4.postella.R;
import com.appteam4.postella.dto.Product;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {
    List<Product> list = new ArrayList<>();
    private AdapterView.OnItemClickListener onItemClickListener;

    /**
     * RecyclerView에서 새로운 뷰 홀더 객체를 생성
     * @param parent 새로운 뷰가 추가될 부모 ViewGroup
     * @param viewType 새로운 View의 타입
     *
     * @return 생성된 뷰 홀더 객체를 반환
     */
    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 사용하여 XML 레이아웃 파일을 View 객체로 인플레이트
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.prod_list_item, parent, false);

        // 새로운 MainViewHolder 객체를 생성하고 반환, RecyclerView 항목의 뷰와 이벤트 처리를 관리
        MainViewHolder mainViewHolder = new MainViewHolder(itemView, onItemClickListener);
        return mainViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        Product product = list.get(position);
        holder.setData(product);
    }

    @Override
    public int getItemCount() { return list.size(); }
    public void setList(List<Product> list){this.list = list;}
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
 
}
