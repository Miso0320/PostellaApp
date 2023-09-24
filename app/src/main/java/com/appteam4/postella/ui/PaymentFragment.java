package com.appteam4.postella.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentPaymentBinding;
import com.appteam4.postella.datastore.AppKeyValueStore;
import com.appteam4.postella.dto.Cart;
import com.appteam4.postella.dto.DeliveryAddress;
import com.appteam4.postella.dto.MyPageOrderList;
import com.appteam4.postella.dto.Payment;
import com.appteam4.postella.ui.PaymentAdapter;

import java.util.List;

public class PaymentFragment extends Fragment {
    private static final String TAG = "PaymentFragment";
    private FragmentPaymentBinding binding;
    NavController navController;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaymentBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);

        // Bundle 객체 생성
        Bundle bundle = getArguments();

        // RecyclerView 초기화
        initRecyclerView(bundle);
        
        // 주문완료 페이지 정보 불러오기
        initPaymentInfo(bundle);
        
        // 쇼핑 계속하기 이동 버튼
        initBtnShopping();

        return binding.getRoot();
    }

    private void initRecyclerView(Bundle bundle) {
        // RecyclerView에서 항목을 수직으로 배치하도록 설정
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false
        );
        binding.orderListRecylerView.setLayoutManager(linearLayoutManager);

        // 어댑터 생성
        PaymentAdapter paymentAdapter = new PaymentAdapter();

        // 로그인 여부 확인
        String exist = AppKeyValueStore.getValue(getContext(), "us_no");

        // 로그인 되어 있는 경우
        if (exist != null) {
            // 유저식별번호 받아오기
            int us_no = Integer.parseInt(AppKeyValueStore.getValue(getContext(), "us_no"));

            // 주문정보 받아오기
            List<MyPageOrderList> orderList = (List<MyPageOrderList>) bundle.getSerializable("orderList");

            // 총 주문금액
            int totalPirce = 0;
            for(MyPageOrderList order : orderList) {
                totalPirce += order.getOd_detail_price();
            }

            int deliverFee = 3000;
            if (totalPirce >= 50000) {
                deliverFee = 0;
            }
            
            // 결제금액
            binding.finalTotalPrice.setText(String.valueOf(totalPirce + deliverFee));
            
            paymentAdapter.setOrderList(orderList);

            // RecyclerView에 어댑터 세팅
            binding.orderListRecylerView.setAdapter(paymentAdapter);
        }
    }

    private void initPaymentInfo(Bundle bundle) {
        // 배송지정보 받아오기
        DeliveryAddress deliveryAddress = (DeliveryAddress) bundle.getSerializable("deliveryAddress");
        Payment payment = (Payment) bundle.getSerializable("payment");

        binding.daName.setText(deliveryAddress.getDa_name());
        binding.daAdr.setText(deliveryAddress.getDa_adr());
        binding.daDetailAdr.setText(deliveryAddress.getDa_detail_adr());
        binding.payMethod.setText(payment.getPay_method());
       //binding.payDetail.setText(payment.getPay_detail());
    }

    private void initBtnShopping() {
        // 쇼핑 계속하기 버튼 설정
        binding.btnShopping.setOnClickListener(v -> {
            navController.popBackStack(R.id.dest_main, false);
        });
    }
    
}