package com.appteam4.postella.ui;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentOrderBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OrderFragment extends Fragment {
    private static final String TAG = "OrderFragment";
    private FragmentOrderBinding binding;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);

        // 스피너 설정
        initSpinner();

        // 결제수단 선택
        initPayMethod();

        // 하단 네비게이션바 숨기기
        hideBottomNavigation(true);

        return binding.getRoot();
    }

    private void initSpinner() {
        // 무통장입금 입금은행 스피너
        Spinner depositSpinner = binding.spinnerDeposit;
        ArrayAdapter depositAdapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.deposit_kind, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item
        );

        depositAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        depositSpinner.setAdapter(depositAdapter);

        // 계좌이체 은행선택 스피너
        Spinner bankSpinner = binding.spinnerBank;
        ArrayAdapter bankAdapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.bank_kind, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item
        );

        bankAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bankSpinner.setAdapter(bankAdapter);

        // 카드결제 카드선택 스피너
        Spinner cardSpinner = binding.spinnerCard;
        ArrayAdapter cardAdapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.card_kind, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item
        );

        cardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cardSpinner.setAdapter(cardAdapter);

        // 카드결제 할부선택 스피너
        Spinner installmentSpinner = binding.spinnerInstallment;
        ArrayAdapter installmentAdapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.installment_kind, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item
        );

        installmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        installmentSpinner.setAdapter(installmentAdapter);

        // 휴대폰결제 통신사선택 스피너
        Spinner phoneSpinner = binding.spinnerPhone;
        ArrayAdapter phoneAdapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.phone_kind, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item
        );

        phoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        phoneSpinner.setAdapter(phoneAdapter);
    }

    private void initPayMethod() {
        LinearLayout depositLayout = binding.depositLayout;
        LinearLayout bankLayout = binding.bankSelectLayout;
        LinearLayout cardLayout = binding.cardLayout;
        LinearLayout phoneLayout = binding.phoneLayout;

        binding.makeDeposit.setOnClickListener(v -> {
            if (bankLayout.getVisibility() == View.VISIBLE) {
                bankLayout.setVisibility(View.GONE);
            }
            if (cardLayout.getVisibility() == View.VISIBLE) {
                cardLayout.setVisibility(View.GONE);
            }
            if (phoneLayout.getVisibility() == View.VISIBLE) {
                phoneLayout.setVisibility(View.GONE);
            }
            depositLayout.setVisibility(View.VISIBLE);
            //makeDeposit.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.your_desired_button_background_color));
            //makeDeposit.setTextColor(ContextCompat.getColor(getContext(), R.color.your_desired_button_text_color));
        });

        binding.accountTransfer.setOnClickListener(v -> {
            if (depositLayout.getVisibility() == View.VISIBLE) {
                depositLayout.setVisibility(View.GONE);
            }
            if (cardLayout.getVisibility() == View.VISIBLE) {
                cardLayout.setVisibility(View.GONE);
            }
            if (phoneLayout.getVisibility() == View.VISIBLE) {
                phoneLayout.setVisibility(View.GONE);
            }
            bankLayout.setVisibility(View.VISIBLE);
        });

        binding.cardPay.setOnClickListener(v -> {
            if (depositLayout.getVisibility() == View.VISIBLE) {
                depositLayout.setVisibility(View.GONE);
            }
            if (bankLayout.getVisibility() == View.VISIBLE) {
                bankLayout.setVisibility(View.GONE);
            }
            if (phoneLayout.getVisibility() == View.VISIBLE) {
                phoneLayout.setVisibility(View.GONE);
            }
            cardLayout.setVisibility(View.VISIBLE);
        });

        binding.phonePay.setOnClickListener(v -> {
            if (depositLayout.getVisibility() == View.VISIBLE) {
                depositLayout.setVisibility(View.GONE);
            }
            if (bankLayout.getVisibility() == View.VISIBLE) {
                bankLayout.setVisibility(View.GONE);
            }
            if (cardLayout.getVisibility() == View.VISIBLE) {
                cardLayout.setVisibility(View.GONE);
            }
            phoneLayout.setVisibility(View.VISIBLE);
        });
    }

    private void hideBottomNavigation(boolean bool) {
        BottomNavigationView bottomNavigation = getActivity().findViewById(R.id.bottom_navigation_view);
        if (bool == true) {
            // 하단 네비게이션바 지우기
            bottomNavigation.setVisibility(View.GONE);
        } else {
            // 하단 네이게이션바 나타내기
            bottomNavigation.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        hideBottomNavigation(false);
    }
}