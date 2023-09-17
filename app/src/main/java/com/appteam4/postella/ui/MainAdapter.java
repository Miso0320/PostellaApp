package com.appteam4.postella.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appteam4.postella.R;
import com.appteam4.postella.dto.Product;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {
    private static final String TAG = "MainAdapter";
    List<Product> list = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private MainFragment mainFragment;

    // MainFragment의 참조 설정
    public void setMainFragment(MainFragment mainFragment) {
        this.mainFragment = mainFragment;
    }

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

    /**
     * RecyclerView에 의해 호출되며, 지정된 위치에 있는 데이터를 표시
     *
     * ViewHolder의 내용을 업데이트하여 어댑터의 데이터 집합 내에서 지정된 위치에 있는 항목을 나타냄
     *
     * @param holder 지정된 위치의 항목 내용을 나타낼 ViewHolder
     * @param position 어댑터의 데이터 집합 내에서 항목의 위치
     */
    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        // 해당 위치(position)의 Product 객체를 가져옴
        Product product = list.get(position);
        // ViewHolder에 데이터를 설정하여 화면에 표시
        holder.setData(product);
        // ViewHolder에서 CheckBox 참조 가져오기
        CheckBox checkBoxFavorite = holder.itemView.findViewById(R.id.checkbox_favorite);

        // CheckBox에 대한 OnCheckedChangeListener 설정
        checkBoxFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mainFragment != null) {
                    if (isChecked) {
                        Log.i(TAG, "onCheckedChanged: 실행한다 체쿠");
                        Log.i(TAG, "onCheckedChanged: " + product);
                        // 체크되었을 때, addWish 함수 호출
                        mainFragment.addWish(product);
                    } else {
                        Log.i(TAG, "onCheckedChanged: 실행한다 체쿠");
                        // 체크 해제되었을 때, removeWish 함수 호출
                        mainFragment.removeWish(product);
                    }
                }
            }
        });
    }

    /**
     * 어댑터의 데이터 집합에 포함된 항목의 총 개수를 반환
     *
     * @return 데이터 집합에 포함된 항목의 총 개수
     */
    @Override
    public int getItemCount() { return list.size(); }

    /**
     * 어댑터의 데이터 리스트를 설정
     *
     * @param list 어댑터에 표시할 데이터 리스트
     */
    public void setList(List<Product> list){this.list = list;}

    /**
     * RecyclerView의 아이템 클릭 이벤트를 처리하기 위한 인터페이스입니다.
     */
    public interface OnItemClickListener {
        /**
         * 아이템이 클릭될 때 호출
         *
         * @param itemView 클릭된 아이템의 뷰
         * @param position 클릭된 아이템의 위치
         */
        void onItemClick(View itemView, int position);
    }

    /**
     * 아이템 클릭 이벤트를 처리할 리스너 설정
     *
     * @param onItemClickListener 아이템 클릭 이벤트를 처리할 리스너
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 지정된 위치에 있는 아이템을 반환
     *
     * @param position 가져올 아이템의 위치
     * @return 지정된 위치에 있는 아이템
     */
    public Product getItem(int position) {return list.get(position);}

}
