package com.appteam4.postella.ui;

import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.appteam4.postella.MainActivity;
import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentFilterBinding;

public class FilterFragment extends Fragment {
    private static final String TAG = "FilterFragment";
    private FragmentFilterBinding binding;
    private NavController navController;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFilterBinding.inflate(inflater);
        // NavController 초기화
        navController = NavHostFragment.findNavController(this);
        //필터 결과보기 버튼 초기화
        initBtnListResult();
        return binding.getRoot();
    }

    private void initBtnListResult(){
        //검색어 가져오기
        String searchKeyword = getArguments().getString("keyword");
        // 결과 보기 버튼을 초기에 비활성화
        binding.btnProdOrderResult.setEnabled(false);
        binding.btnProdOrderResult.setOnClickListener(v->{
            // 선택한 라디오 버튼의 값을 가져와서 상품목록 프래그먼트로 전달
            String selectedFilterValue = getSelectedValue(binding.radioGroupCategory.getCheckedRadioButtonId());
            // 선택한 값을 Bundle에 담아서 상품 목록 프래그먼트로 전달
            Bundle bundle = new Bundle();
            //검색어를 번들에 담아서 상품 목록 프레그먼트로 전달
            bundle.putString("keyword", searchKeyword);
            if(selectedFilterValue == "PHO" || selectedFilterValue == "DES" ||
                selectedFilterValue == "ILU" ||selectedFilterValue == "CAL" ){
                bundle.putString("prd_category", selectedFilterValue);
                // 상품 목록 프래그먼트로 이동
                navController.navigate(R.id.action_dest_filter_to_dest_prod_list, bundle);
            } else if (selectedFilterValue == "HON" || selectedFilterValue == "FOO" ||
                    selectedFilterValue == "SMI" || selectedFilterValue == "UUU" || selectedFilterValue == "GYU") {
                bundle.putString("brand", selectedFilterValue);
                // 상품 목록 프래그먼트로 이동
                navController.navigate(R.id.action_dest_filter_to_dest_prod_list, bundle);
            }else if (selectedFilterValue == "CEL" || selectedFilterValue == "LOV" ||
                    selectedFilterValue == "THA" || selectedFilterValue == "HEA" || selectedFilterValue == "APO") {
                bundle.putString("message", selectedFilterValue);
                // 상품 목록 프래그먼트로 이동
                navController.navigate(R.id.action_dest_filter_to_dest_prod_list, bundle);
            }else {
                // 상품 목록 프래그먼트로 이동
                navController.navigate(R.id.action_dest_filter_to_dest_prod_list);
            }
        });
        // 라디오 버튼 클릭 이벤트 처리
        binding.radioGroupCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 라디오 버튼이 선택되었을 때만 결과 보기 버튼 활성화
                binding.btnProdOrderResult.setEnabled(checkedId != -1);
            }
        });
    }
    // 라디오 버튼에 대한 선택한 값을 정의
    private String getSelectedValue(int radioButtonId) {
        if(radioButtonId == binding.btnRadioPhoto.getId()){
            return "PHO";
        }else if(radioButtonId == binding.btnRadioDesign.getId()){
            return "DES";
        }else if(radioButtonId == binding.btnRadioIllust.getId()){
            return "ILU";
        }else if(radioButtonId == binding.btnRadioCalli.getId()){
            return "CAL";
        }else if(radioButtonId == binding.btnRadioHong.getId()){
            return "HON";
        }else if(radioButtonId == binding.btnRadioSong.getId()){
            return "FOO";
        }else if(radioButtonId == binding.btnRadioSmile.getId()){
            return "SMI";
        }else if(radioButtonId == binding.btnRadioYuyu.getId()){
            return "UUU";
        }else if(radioButtonId == binding.btnRadioGyu.getId()){
            return "GYU";
        }else if(radioButtonId == binding.btnRadioCele.getId()){
            return "CEL";
        }else if(radioButtonId == binding.btnRadioLove.getId()){
            return "LOV";
        }else if(radioButtonId == binding.btnRadioThank.getId()){
            return "THA";
        }else if(radioButtonId == binding.btnRadioHealing.getId()){
            return "HEA";
        }else if(radioButtonId == binding.btnRadioTake.getId()){
            return "APO";
        }else {
            return null;
        }
    }

}