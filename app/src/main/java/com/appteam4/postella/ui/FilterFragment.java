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
        // 결과 보기 버튼을 초기에 비활성화
        binding.btnProdOrderResult.setEnabled(false);
        binding.btnProdOrderResult.setOnClickListener(v->{
            // 선택한 라디오 버튼의 값을 가져와서 상품목록 프래그먼트로 전달
            // 선택한 라디오 버튼의 값을 가져오기
            int selectedFilterValue = getSelectedValue(binding.radioGroupCategory.getCheckedRadioButtonId());
            Log.i(TAG, "initBtnListResult: " + selectedFilterValue);
            // 선택한 값을 Bundle에 담아서 상품 목록 프래그먼트로 전달
            Bundle bundle = new Bundle();
            bundle.putInt("selectedFilterValue", selectedFilterValue);

            // 상품 목록 프래그먼트로 이동
            navController.navigate(R.id.action_dest_filter_to_dest_prod_list, bundle);
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
    private int getSelectedValue(int radioButtonId) {
        if(radioButtonId == binding.btnRadioPhoto.getId()){
            return 1;
        }else{
            return 0;
        }
    }

}