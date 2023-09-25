package com.appteam4.postella.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentInquryBinding;
import com.appteam4.postella.databinding.FragmentReviewBinding;
import com.appteam4.postella.dto.Inquiry;
import com.appteam4.postella.dto.Product;
import com.appteam4.postella.dto.Review;
import com.appteam4.postella.service.InquiryService;
import com.appteam4.postella.service.ServiceProvider;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquryFragment extends Fragment {
    private static final String TAG = "InquryFragment";
    private FragmentInquryBinding binding;
    NavController navController;

    // bottom sheet
    private View bottomSheetView;
    private BottomSheetBehavior bottomSheetBehavior;

    // 상품그룹식별번호
    private int pg_no;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInquryBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);

        // 앱바 설정
        initMenu();

        // Bundle에서 객체 받아오기
        Bundle args = getArguments();
        if (args != null) {
            Product product = (Product) args.getSerializable("product");
            if (product != null) {
                pg_no = product.getPg_no();

                // RecyclerView 초기화
                initRecyclerView(pg_no);
            }
        }

        // bottom sheet
        ProdDetailBottomSheetFragment prodDetailBottomSheetFragment = new ProdDetailBottomSheetFragment(pg_no);

        // 버튼 클릭 시 bottom sheet를 보이도록 설정
        binding.btnBuy.setOnClickListener(v -> {
            prodDetailBottomSheetFragment.show(getChildFragmentManager(), prodDetailBottomSheetFragment.getTag());
        });

        // 하단 네비게이션바 숨기기
        hideBottomNavigation(true);

        // 탭 메뉴 이동
        initTabPage(args);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        // 하단 네비게이션바 숨기기
        hideBottomNavigation(true);
    }

    /**
     *
     * 앱바 설정
     *
     */
    private void initMenu() {
        MenuProvider menuProvider = new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.nav_menu_top, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.dest_search) {
                    navController.navigate(R.id.dest_search, null);
                    return true;
                } else if (menuItem.getItemId() == R.id.dest_cart) {
                    navController.navigate(R.id.dest_cart, null);
                    return true;
                }
                return false;
            }
        };
        getActivity().addMenuProvider(menuProvider, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    /**
     *
     * 상품문의 목록 RecyclerView 초기화
     *
     * @param pg_no
     * 			상품 그룹 번호
     *
     */
    private void initRecyclerView(int pg_no) {
        // RecyclerView에서 항목을 수직으로 배치하도록 설정
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false
        );

        // RecyclerView 찾기
        RecyclerView recyclerView = binding.inquiryListView;
        recyclerView.setLayoutManager(linearLayoutManager);

        // 구분선 추가하기
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                linearLayoutManager.getOrientation()
        );

        recyclerView.addItemDecoration(dividerItemDecoration);

        InquiryAdapter inquiryAdapter = new InquiryAdapter();

        InquiryService inquiryService = ServiceProvider.getInquiryService(getContext());

        // 상품문의목록 가져오기
        Call<List<Inquiry>> inquiryCall = inquiryService.getQnaListForApp(pg_no);

        inquiryCall.enqueue(new Callback<List<Inquiry>>() {
            @Override
            public void onResponse(Call<List<Inquiry>> call, Response<List<Inquiry>> response) {
                List<Inquiry> list = response.body();

                // 어댑터 데이터 세팅
                inquiryAdapter.setInquiryList(list);

                // RecyclerView에 어댑터 세팅
                recyclerView.setAdapter(inquiryAdapter);
            }

            @Override
            public void onFailure(Call<List<Inquiry>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /**
     *
     * 하단 네비게이션바 보이기/숨기기
     *
     * @param bool
     * 			보이기/숨기기 결정값
     *
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

    /**
     *
     * 상단 탭메뉴 이동
     *
     * @param args
     * 			Bundle 객체 전달
     *
     */
    private void initTabPage(Bundle args) {
        binding.tabProductDetail.setOnClickListener(v -> {
            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.dest_prod_detail, false)
                    .setLaunchSingleTop(true)
                    .build();

            navController.navigate(R.id.dest_prod_detail, args, navOptions);
        });

        binding.tabProductReviews.setOnClickListener(v -> {
            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.dest_review, false)
                    .setLaunchSingleTop(true)
                    .build();

            navController.navigate(R.id.dest_review, args, navOptions);
        });

        binding.btnInquiry.setOnClickListener(v -> {
            navController.navigate(R.id.dest_order);
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        // 하단 네비게이션바 보이기
        hideBottomNavigation(false);
    }
}