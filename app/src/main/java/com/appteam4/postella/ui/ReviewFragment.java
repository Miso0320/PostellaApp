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
import android.widget.AdapterView;
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
import com.appteam4.postella.dto.Product;
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

    // 정렬순 구분값
    int kind = 1;
    int starRate = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReviewBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);

        // 앱바 설정
        initMenu();

        // Bundle에서 객체 받아오기
        Bundle args = getArguments();
        if (args != null) {
            Product product = (Product) args.getSerializable("product");
            if (product != null) {
                int pg_no = product.getPg_no();

                // 상품평 목록 RecyclerView 초기화
                initRecyclerView(pg_no, kind, starRate);
            }
        }

        // 스피너 설정
        initSpinner(args);

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
     * 상품평 스피너 초기화
     *
     * @param args
     * 			Bundle 객체 전달
     *
     */
    private void initSpinner(Bundle args) {
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

        // 정렬 스피너 변경 이벤트 처리
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 스피너 선택항목 값 가져오기
                String selectedSort = sortSpinner.getSelectedItem().toString();
                if (selectedSort.equals("최신순")) {
                    kind = 1;
                } else if (selectedSort.equals("별점순")) {
                    kind = 2;
                }

                // 리뷰목록 갱신하기
                if (args != null) {
                    Product product = (Product) args.getSerializable("product");
                    if (product != null) {
                        int pg_no = product.getPg_no();

                        // RecyclerView 초기화
                        initRecyclerView(pg_no, kind, starRate);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // 정렬 스피너 변경 이벤트 처리
        starSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 스피너 선택항목 값 가져오기
                String selectedStar = starSpinner.getSelectedItem().toString();
                if (selectedStar.equals("전체별점")) {
                    starRate = 0;
                } else if (selectedStar.equals("1개")) {
                    starRate = 1;
                } else if (selectedStar.equals("2개")) {
                    starRate = 2;
                } else if (selectedStar.equals("3개")) {
                    starRate = 3;
                } else if (selectedStar.equals("4개")) {
                    starRate = 4;
                } else if (selectedStar.equals("5개")) {
                    starRate = 5;
                }

                // 리뷰목록 갱신하기
                if (args != null) {
                    Product product = (Product) args.getSerializable("product");
                    if (product != null) {
                        int pg_no = product.getPg_no();

                        // RecyclerView 초기화
                        initRecyclerView(pg_no, kind, starRate);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    /**
     *
     * 상품평 목록 RecyclerView 초기화
     *
     * @param pg_no
     * 			상품 그룹 번호
     * @param kind
     * 			정렬순 결정값
     * @param starRate
     * 			별점별 필터링 구분값
     *
     */
    private void initRecyclerView(int pg_no, int kind, int starRate) {
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

        binding.tabProductInquiries.setOnClickListener(v -> {
            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.dest_inquiry, false)
                    .setLaunchSingleTop(true)
                    .build();

            navController.navigate(R.id.dest_inquiry, args, navOptions);
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        // 하단 네비게이션바 보이기
        hideBottomNavigation(false);
    }

}