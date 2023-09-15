package com.appteam4.postella.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentProdListBinding;

public class ProdListFragment extends Fragment {

    private static final String TAG = "ProdListFragment";
    private FragmentProdListBinding binding;
    private NavController navController;
    private int selectedFilterBtn = R.id.navigation_view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProdListBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);
        // AppBar 다시 보이게 하기
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        //스피너 초기화
        initSpinner();
        //필터 버튼 초기화
        initBtnFilter();

        return binding.getRoot();
    }

    private void initSpinner(){
        //상품 정렬 스피너
        Spinner orderKindSpinner = binding.spinnerOrderKind;
        ArrayAdapter<CharSequence> orderKindAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.spinner_order_kind, android.R.layout.simple_spinner_item);
        orderKindAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderKindSpinner.setAdapter(orderKindAdapter);

        // Spinner 선택 이벤트 처리
        orderKindSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 선택된 정렬 방식에 따라 작업 수행
                String selectedOrderKind = (String) parent.getItemAtPosition(position);
                // 예: 선택된 정렬 방식을 로그에 출력
                Log.d("Selected Order Kind", selectedOrderKind);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 아무것도 선택되지 않았을 때 수행할 작업
            }
        });
    }

    private void initBtnFilter(){
        binding.btnOrderCategory.setOnClickListener(v->{
            selectedFilterBtn = binding.btnOrderCategory.getId();
            navController.navigate(R.id.action_dest_prod_list_to_dest_filter);
        });

        binding.btnOrderBrand.setOnClickListener(v->{
            selectedFilterBtn = binding.btnOrderBrand.getId();
            Log.i(TAG, "initBtnFilter: " + selectedFilterBtn);
            navController.navigate(R.id.action_dest_prod_list_to_dest_filter);
        });

        binding.btnOrderMessage.setOnClickListener(v->{
            selectedFilterBtn = binding.btnOrderMessage.getId();
            navController.navigate(R.id.action_dest_prod_list_to_dest_filter);
        });
    }

    public int getSelectedFilterBtn(){return selectedFilterBtn;}
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}