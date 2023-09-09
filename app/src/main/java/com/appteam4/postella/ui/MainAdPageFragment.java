package com.appteam4.postella.ui;
/**
 * @auther 성유진
 */
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentPageBinding;

//메인페이지 상단 광고 패널을 위한 pageFragment
public class MainAdPageFragment extends Fragment {
    private static final String TAG = "PageFragment";
    private FragmentPageBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPageBinding.inflate(inflater);

        //페이지 번호 받기
        Bundle bundle = getArguments();
        int pageNo = bundle.getInt("pageNo");
        binding.txtContent.setText(pageNo +"/5");

        //페이지별로 UI 세팅
        initUIByPageNo(pageNo);

        return binding.getRoot();
    }

    /**
     * 메인페이지 상단 광고의 페이지별 UI를 초기화하기
     * @param pageNo :현재 페이지 번호
     */
    private void initUIByPageNo(int pageNo){
        //현재 페이지 번호 표시
        binding.txtContent.setText(pageNo + "/5");

        //배경 이미지의 리소스 id를 저장하기 위한 변수 초기화
        int backgroundImageResId = 0;
        //페이지에 따른 배경이미지 리소스 id를 backgroundImageResId에 할당
        switch (pageNo) {
            case 1:
                backgroundImageResId = R.drawable.main_ad_top1;
                break;
            case 2:
                backgroundImageResId = R.drawable.main_ad_top2;
                break;
            case 3:
                backgroundImageResId = R.drawable.main_ad_top3;
                break;
            case 4:
                backgroundImageResId = R.drawable.main_ad_top4;
                break;
            case 5:
                backgroundImageResId = R.drawable.main_ad_top5;

        }
        //선택된 배경이미지가 있을 때 해당 배경이미지를 설정
        if (backgroundImageResId != 0) {
            binding.getRoot().setBackgroundResource(backgroundImageResId);
        }
    }
}