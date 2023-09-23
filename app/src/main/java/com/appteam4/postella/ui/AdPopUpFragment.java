package com.appteam4.postella.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentAdPopUpBinding;

import java.util.Calendar;

public class AdPopUpFragment extends DialogFragment {
    private static final String TAG = "AdPopUpFragment";
    private FragmentAdPopUpBinding binding;
    private NavController navController;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Dialog 객체를 생성
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // 팝업창의 레이아웃을 설정
        dialog.setContentView(R.layout.fragment_ad_pop_up);

        // Dialog의 Window 객체를 얻기.
        Window window = dialog.getWindow();
        if (window != null) {
            // 팝업창의 너비와 높이를 조절
            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            params.width = WindowManager.LayoutParams.MATCH_PARENT; // 너비를 원하는 크기로 설정
            params.height = WindowManager.LayoutParams.MATCH_PARENT; // 높이를 원하는 크기로 설정

            // Window에 설정을 적용
            window.setAttributes(params);
        }

        return dialog;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = FragmentAdPopUpBinding.inflate(inflater);
       navController = NavHostFragment.findNavController(this);

        initBtn();
        hideAdIfNeeded();

       return binding.getRoot();
    }

    private void initBtn(){
        //자세히 보기 클릭시 광고 상세 프레그먼트로 이동
        binding.btnGoDetailAd.setOnClickListener(v->{
            navController.navigate(R.id.action_dest_main_to_dest_ad_detail_ad1);
            dismiss();
        });
        binding.btnPopUpCloseTop.setOnClickListener(v->{
            // 팝업창을 닫는 동작을 구현
            dismiss();
        });
        binding.btnPopUpCloseBottom.setOnClickListener(v->{
            // 팝업창을 닫는 동작을 구현
            dismiss();
        });
    }

    //오늘 하루 동안 보지않기 체크박스 상태 저장
    private void hideAdIfNeeded() {
        // SharedPreferences에서 체크박스 상태 확인
        SharedPreferences preferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        boolean hideAdToday = preferences.getBoolean("hide_ad_today", false);

        binding.chkNoForADay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == false){
                    // 체크박스 상태를 SharedPreferences에 저장
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("hide_ad_today", isChecked);
                    editor.apply();
                }else {
                    // 체크박스 상태를 SharedPreferences에 저장
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("hide_ad_today", isChecked);
                    editor.apply();
                }

                if(hideAdToday){
                    // "오늘 하루 동안 광고를 보지 않도록 설정"이 체크된 경우
                    scheduleResetOfAdDisplayFlag();
                }
            }
        });
    }
    private void scheduleResetOfAdDisplayFlag() {
        // 현재 날짜를 기준으로 1일 뒤의 날짜를 계산
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        // SharedPreferences에 날짜를 저장하고, 이 날짜를 기준으로 광고 표시 여부를 초기화
        SharedPreferences preferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("ad_display_reset_date", calendar.getTimeInMillis());
        editor.apply();
    }

    private void checkAndResetAdDisplayFlag() {
        SharedPreferences preferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        long resetDate = preferences.getLong("ad_display_reset_date", 0);

        // 현재 날짜와 저장된 날짜를 비교하여 광고 표시 여부를 초기화
        long currentDate = System.currentTimeMillis();
        if (resetDate > 0 && currentDate >= resetDate) {
            // 저장된 날짜가 있고 현재 날짜가 저장된 날짜 이후인 경우
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("hide_ad_today", false); // 광고 표시 여부 초기화
            editor.putLong("ad_display_reset_date", 0); // 날짜 초기화
            editor.apply();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        checkAndResetAdDisplayFlag();
    }
}
