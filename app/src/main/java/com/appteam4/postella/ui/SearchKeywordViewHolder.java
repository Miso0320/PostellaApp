package com.appteam4.postella.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.appteam4.postella.R;
import com.appteam4.postella.datastore.AppKeyValueStore;

public class SearchKeywordViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "SearchKeywordViewHolder";
    public SearchKeywordAdapter viewHolder;
    private SearchFragment searchFragment;
    private Button keyword;
    private String keywordText;
    String isEditMode = AppKeyValueStore.getValue(itemView.getContext(),"editMode");

    // SearchFragment의 참조 설정
    public SearchKeywordViewHolder(@NonNull View itemView, SearchKeywordAdapter.OnItemClickListener onItemClickListener) {
        super(itemView);
        //아이템 UI 얻기
        keyword = (Button) itemView.findViewById(R.id.btn_search_log);
        //클릭 이벤트 처리
        itemView.setOnClickListener(v -> {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        });
        //최근 검색어 클릭 시 키워드 삭제
        keyword.setOnClickListener(v->{
            //편집 버튼 클릭 여부
            Log.i(TAG, "SearchKeywordViewHolder:아이템 클릭함");
            //AppKeyValueStore에 편집모드 여부
            //SearchFragment에서 편집을 클릭하고 최근 검색어 버튼을  클릭했을 때 삭제
            if(isEditMode != null){
                if(isEditMode.equals("on")){
                    Log.i(TAG, "SearchKeywordViewHolder: 최근 검색어 지워졋");
                    AppKeyValueStore.removeRecentSearchKeyword(itemView.getContext(), (String) keyword.getText());
                    AppKeyValueStore.remove(itemView.getContext(),"recentKeyword");
                    keyword.setVisibility(View.GONE);
                }else if(isEditMode.equals("off")) {
                    Log.i(TAG, "SearchKeywordViewHolder: 추가추가");
                    Bundle bundle = new Bundle();
                    //SearchFragment로 클릭한 최근검색어 AppkeyValureStore에 저장해주기
                    AppKeyValueStore.put(itemView.getContext(),"recentKeyword", (String) keyword.getText());
                    Log.i(TAG, "SearchKeywordViewHolder: 저장된 키워듀 " + AppKeyValueStore.getValue(itemView.getContext(),"recentKeyword"));
                    bundle.putString("keyword", (String) keyword.getText());
                }
            }
        });
    }
    public void setData(String keywordText){
        Log.i(TAG, "SearchKeywordViewHolder: 실행햠1");
        // 버튼의 텍스트 설정
        keyword.setText(keywordText);
        if(isEditMode!= null){
            //편집 모드일때는 버튼오른쪽에 "X"아이콘 추가
            if(isEditMode.equals("on")){
                Log.i(TAG, "SearchKeywordViewHolder: 실행햠2");
                // 추가 하려는 "X" 아이콘 가져오기
                Drawable drawable = ContextCompat.getDrawable(itemView.getContext(),R.drawable.baseline_close_24);
                // 아이콘 크기 설정
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                //버튼 오른쪽에 "X" 아이콘 추가
                keyword.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            }
        }
    }


}
