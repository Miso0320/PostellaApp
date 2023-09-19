package com.appteam4.postella.ui;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.appteam4.postella.R;

public class SearchKeywordViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "SearchKeywordViewHolder";
    private Button keyword;
    public SearchKeywordViewHolder(@NonNull View itemView, SearchKeywordAdapter.OnItemClickListener onItemClickListener) {
        super(itemView);
        //아이템 UI 얻기
        keyword = (Button) itemView.findViewById(R.id.btn_search_log);
        //클릭 이벤트 처리
        itemView.setOnClickListener(v -> {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        });
    }
    public void setData(String keywordText){
        // 버튼의 텍스트 설정
        keyword.setText(keywordText);
    }
}
