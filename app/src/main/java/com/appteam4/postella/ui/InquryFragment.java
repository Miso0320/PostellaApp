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
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class InquryFragment extends Fragment {
    private static final String TAG = "InquryFragment";
    private FragmentInquryBinding binding;
    NavController navController;

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
                int pg_no = product.getPg_no();

                // RecyclerView 초기화
                initRecyclerView();
            }
        }

        // 하단 네비게이션바 숨기기
        hideBottomNavigation(true);

        // 답변 내용 클릭 시 표출
        //viewAnswer();

        // 탭 메뉴 이동
        initTabPage(args);


        return binding.getRoot();
    }

    private void initMenu() {
        MenuProvider menuProvider = new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.nav_menu_top, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.dest_search) {
                    navController.navigate(R.id.dest_search, null);
                    return true;
                } else if(menuItem.getItemId() == R.id.dest_cart) {
                    navController.navigate(R.id.dest_cart, null);
                    return true;
                }
                return false;
            }
        };
        getActivity().addMenuProvider(menuProvider, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    private void initRecyclerView() {
        // RecyclerView에서 항목을 수직으로 배치하도록 설정
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false
        );
        binding.inquiryListView.setLayoutManager(linearLayoutManager);

        // 구분선 추가하기
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                binding.inquiryListView.getContext(),
                linearLayoutManager.getOrientation()
        );

        binding.inquiryListView.addItemDecoration(dividerItemDecoration);

        InquiryAdapter inquiryAdapter = new InquiryAdapter();

        for (int i = 1; i <= 5; i++) {
            Inquiry inquiry = new Inquiry();
            inquiry.setQnaNo(i);
            inquiry.setPrdNo(i);
            inquiry.setPgNo(i);
            inquiry.setUsNo(i);
            inquiry.setqContent("꼬몽이 엽서는 대체 언제 새 시리즈가 나오나요?");
            inquiry.setqDate("2023. 09. 11");
            inquiry.setaContent("곧 추가될 예정입니다.");
            inquiry.setaDate("2023. 09. 13");

            inquiryAdapter.addInquiry(inquiry);
        }

        binding.inquiryListView.setAdapter(inquiryAdapter);
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

    /*private void viewAnswer() {
        LinearLayout linearLayout6 = binding.getRoot().findViewById(R.id.linearLayout6);

        LinearLayout linearLayout = binding.getRoot().findViewById(R.id.linearLayout6);

        linearLayout.setOnClickListener(v -> {
            Log.i(TAG, "클릭클릭클릭!!");
        });

        linearLayout6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: 선택됨!!!!");
                // 다른 레이아웃을 동적으로 추가
                TextView dynamicTextView = new TextView(requireContext());
                dynamicTextView.setText("꼬몽이 엽서 곧 출시 예정입니다.");
                //dynamicTextView.setTextAppearance(getContext(), R.);
                dynamicTextView.setBackgroundColor(Color.GRAY);

            }
        });
    }*/

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
        hideBottomNavigation(false);
    }
}