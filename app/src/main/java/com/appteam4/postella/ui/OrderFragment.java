package com.appteam4.postella.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentOrderBinding;
import com.appteam4.postella.datastore.AppKeyValueStore;
import com.appteam4.postella.dto.DeliveryAddress;
import com.appteam4.postella.dto.MyPageOrderList;
import com.appteam4.postella.service.OrderService;
import com.appteam4.postella.service.ServiceProvider;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderFragment extends Fragment {
    private static final String TAG = "OrderFragment";
    private FragmentOrderBinding binding;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);

        // Bundle 객체 생성
        Bundle bundle = new Bundle();

        // 스피너 설정
        initSpinner(bundle);

        // RecyclerView 초기화
        initRecyclerView(bundle);

        // 결제수단 선택
        initPayMethod(bundle);

        // 하단 네비게이션바 숨기기
        hideBottomNavigation(true);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        // 하단 네비게이션바 숨기기
        hideBottomNavigation(true);
    }

    private void initSpinner(Bundle bundle) {
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

    private void initPayMethod(Bundle bundle) {
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

    private void initRecyclerView(Bundle bundle) {
        // RecyclerView에서 항목을 수직으로 배치하도록 설정
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false
        );
        binding.orderRecyclerView.setLayoutManager(linearLayoutManager);

        // 어댑터 생성
        OrderAdapter orderAdapter = new OrderAdapter();

        // 로그인 여부 확인
        String exist = AppKeyValueStore.getValue(getContext(), "us_no");

        // 로그인 되어 있는 경우
        if (exist != null) {
            // 유저식별번호 받아오기
            int us_no = Integer.parseInt(AppKeyValueStore.getValue(getContext(), "us_no"));

            //bundle.putSerializable("cart", (Serializable) dtoList);
            // 장바구니 체크항목 받아오기
            //List<Cart> cartList = (List<Cart>) bundle.getSerializable("cart");

            // MyPageOrderList에 필요한 정보 저장
            List<MyPageOrderList> orderList = new ArrayList<>();

            /*if (cartList != null) {
                // Cart DTO -> Order DTO 데이터 전달
                for (int i = 0; i < cartList.size(); i++) {
                    orderList.get(i).setPrd_no(cartList.get(i).getPrd_no());
                    orderList.get(i).setPg_name(cartList.get(i).getPg_name());
                    orderList.get(i).setPrd_name(cartList.get(i).getPrd_name());
                    orderList.get(i).setOd_detail_price(cartList.get(i).getPrd_price());
                    orderList.get(i).setOd_detail_qty(cartList.get(i).getCrt_qty());
                }
            }*/

            for (int i = 0; i < 10; i++) {
                MyPageOrderList item = new MyPageOrderList();
                item.setPrd_no(i);
                item.setPg_name("상품명" + i);
                item.setPrd_name("옵션명" + i);
                item.setOd_detail_price(i * 1000);
                item.setOd_detail_qty(i);

                orderList.add(item);
            }

            bundle.putSerializable("orderList", (Serializable) orderList);

            // 어댑터 데이터 세팅
            orderAdapter.setOrderList(orderList);

            // RecyclerView에 어댑터 세팅
            binding.orderRecyclerView.setAdapter(orderAdapter);

            // 주문결제 정보 불러오기
            initLoadInfo(orderList, us_no, bundle);

            // 주문완료 페이지로 이동
            ExtendedFloatingActionButton btnPayment = binding.btnPayment;

            btnPayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavOptions navOptions = new NavOptions.Builder()
                            .setPopUpTo(R.id.dest_inquiry, false)
                            .setLaunchSingleTop(true)
                            .build();

                    navController.navigate(R.id.dest_payment, bundle, navOptions);
                }
            });
        }
    }

    private void initLoadInfo(List<MyPageOrderList> orderList, int us_no, Bundle bundle) {
        OrderService orderService = ServiceProvider.getOrderService(getContext());

        // 주문상품 펼치기/접기 선택 레이아웃
        LinearLayout linearLayout = binding.orderList;
        // 주문상품 펼치기/접기로 숨겨지는 레이아웃
        RecyclerView orderListLayout = binding.orderRecyclerView;

        // 주문상품 접기/펼치기 이벤트 처리
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderListLayout.getVisibility() == View.GONE) {
                    orderListLayout.setVisibility(View.VISIBLE);
                } else {
                    orderListLayout.setVisibility(View.GONE);
                }
            }
        });
        
        // 받는사람 정보
        Call<DeliveryAddress> addressCall = orderService.selectDeliveryForApp(us_no);
        addressCall.enqueue(new Callback<DeliveryAddress>() {
            @Override
            public void onResponse(Call<DeliveryAddress> call, Response<DeliveryAddress> response) {
                DeliveryAddress deliveryAddress = response.body();

                // 받는 사람 이름
                binding.daName.setText(deliveryAddress.getDa_name());
                // 받는 사람 전화번호
                binding.daTel.setText(deliveryAddress.getDa_tel());
                // 주소
                binding.daAdr.setText(deliveryAddress.getDa_adr());
                // 상세주소
                binding.daDetailAdr.setText(deliveryAddress.getDa_detail_adr());

                bundle.putSerializable("deliveryAddress", (Serializable) deliveryAddress);
            }

            @Override
            public void onFailure(Call<DeliveryAddress> call, Throwable t) {
                t.printStackTrace();
            }
        });

        // 주문상품 총 개수
        binding.productQty.setText(String.valueOf(orderList.size()));

        // 총 상품금액
        int total = 0;
        for (MyPageOrderList order : orderList) {
            total += order.getOd_detail_price();
        }
        binding.totalPriceAmount.setText(String.valueOf(total) + "원");

        // 배송비
        int deliverFee = 3000;
        if (total >= 50000) {
            deliverFee = 0;
        }
        binding.deliveryFee.setText(String.valueOf(deliverFee) + "원");

        // 총 결제금액
        int finalTotal = 0;
        finalTotal = total + deliverFee;
        binding.finalTotalPrice.setText(String.valueOf(finalTotal) + "원");

        // 입금기한
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH) + 1;
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        String limitDate = year + "년 " + month + "월 " + day + "일 " + hour + "시 " + minute + "분";

        binding.depositLimit.setText(limitDate);
    }


    /**
     * 하단 네비게이션바 보이기/숨기기
     *
     * @param bool 보이기/숨기기 결정값
     */
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