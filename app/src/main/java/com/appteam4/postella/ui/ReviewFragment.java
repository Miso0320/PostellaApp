package com.appteam4.postella.ui;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentReviewBinding;
import com.appteam4.postella.dto.Review;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ReviewFragment extends Fragment {
    private static final String TAG = "ReviewFragment";
    private FragmentReviewBinding binding;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReviewBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);

        // 앱바 설정
        initMenu();

        // RecyclerView 초기화
        initRecyclerView();

        // 하단 네비게이션바 숨기기
        hideBottomNavigation(true);

        // 탭 메뉴 이동
        initTabPage();

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
        binding.reviewListView.setLayoutManager(linearLayoutManager);

        // 구분선 추가하기
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                binding.reviewListView.getContext(),
                linearLayoutManager.getOrientation()
        );

        binding.reviewListView.addItemDecoration(dividerItemDecoration);

        // 어댑터 생성
        ReviewAdapter reviewAdapter = new ReviewAdapter();

        for (int i = 1; i <= 5; i++) {
            Review review = new Review();
            review.setRevNo(i);
            review.setUsName("유저" + i);
            review.setPrdName("상품" + i);
            review.setRevContent("엽서가 넘 귀엽고 예뻐서 다음에 또 사고싶어요!");
            review.setRevDate("2023. 09. 10");
            review.setPrdNo(i);
            review.setOdNo(i);
            review.setOdDetailNo(i);
            review.setRating(i);

            reviewAdapter.addReview(review);
        }

        binding.reviewListView.setAdapter(reviewAdapter);
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

    private void initTabPage() {
        binding.tabProductDetail.setOnClickListener(v -> {
            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.dest_prod_detail, false)
                    .setLaunchSingleTop(true)
                    .build();

            navController.navigate(R.id.dest_prod_detail, null, navOptions);
        });

        binding.tabProductInquiries.setOnClickListener(v -> {
            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.dest_inquiry, false)
                    .setLaunchSingleTop(true)
                    .build();

            navController.navigate(R.id.dest_inquiry, null, navOptions);
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        hideBottomNavigation(false);
    }

}