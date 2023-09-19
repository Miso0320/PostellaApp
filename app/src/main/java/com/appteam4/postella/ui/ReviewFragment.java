package com.appteam4.postella.ui;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
import com.appteam4.postella.service.ReviewService;
import com.appteam4.postella.service.ServiceProvider;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        // 스피너 설정
        initSpinner();

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

    private void initSpinner() {
        // 정렬 스피너
        Spinner sortSpinner = binding.spinnerReviewSort;
        ArrayAdapter sortAdapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.order_kind, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item
        );

        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(sortAdapter);

        // 별점 스피너
        Spinner starSpinner = binding.spinnerStarSort;
        ArrayAdapter starAdapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.star_rate_group, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item
        );

        starAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        starSpinner.setAdapter(starAdapter);
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

        ReviewService reviewService = ServiceProvider.getReviewService(getContext());

        // 스피너 선택항목 값 가져오기
        Spinner spinner = (Spinner) binding.spinnerReviewSort;
        String selectedSort = spinner.getSelectedItem().toString();
        Log.i(TAG, "selectedSort***************************************** " + selectedSort);
        int pg_no = 3;
        int starRate = 1;
        int kind = 1;

        // 리뷰목록 가져오기
        Call<List<Review>> reviewCall = reviewService.getReviewsForApp(pg_no, starRate, kind);

        reviewCall.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                List<Review> list = response.body();

                // 어댑터 데이터 세팅
                reviewAdapter.setReviewList(list);

                // RecyclerView에 어댑터 세팅
                binding.reviewListView.setAdapter(reviewAdapter);
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        // 선택된 리뷰개수 불러오기
        Call<Integer> reviewCntCall = reviewService.getReviewCntForApp(pg_no, starRate);

        reviewCntCall.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                int reviewCnt = response.body();
                binding.reviewCount.setText("총 " + reviewCnt + "개");
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                t.printStackTrace();
            }
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