package com.appteam4.postella.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.appteam4.postella.R;

import java.util.ArrayList;
import java.util.List;

public class SearchKeywordAdapter extends RecyclerView.Adapter<SearchKeywordViewHolder> {
    private static final String TAG = "SearchKeywordAdapter";
    List<String> list = new ArrayList<>();
    private SearchFragment searchFragment;
    private OnItemClickListener onItemClickListener;
    private NavController navController;

    public void setSearchFragment(SearchFragment searchFragment){this.searchFragment = searchFragment;}
    @NonNull
    @Override
    public SearchKeywordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 사용하여 XML 레이아웃 파일을 View 객체로 인플레이트
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.search_log_item, parent, false);

        //새로운 SearchKewordViewHolder를 객체로 생성하고 반환, RecyclerView 항목의 뷰와 이벤트 처리를 관리
        SearchKeywordViewHolder searchKeywordViewHolder = new SearchKeywordViewHolder(itemView, onItemClickListener);
        return searchKeywordViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchKeywordViewHolder holder, int position) {
        // 해당 위치(position)의 Product 객체를 가져옴
        String keyword = list.get(position);
        // ViewHolder에 데이터를 설정하여 화면에 표시
        holder.setData(keyword);
    }

    @Override
    public int getItemCount() {return list.size();}
    public void setList(List<String> list){this.list = list;}

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public String getItem(int position) {return list.get(position);}

}
