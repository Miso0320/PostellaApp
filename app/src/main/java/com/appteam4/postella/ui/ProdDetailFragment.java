package com.appteam4.postella.ui;

import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentProdDetailBinding;
import com.appteam4.postella.dto.Product;
import com.appteam4.postella.service.ProductDetailService;
import com.appteam4.postella.service.ServiceProvider;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdDetailFragment extends Fragment {
    private static final String TAG = "ProdDetailFragment";
    private FragmentProdDetailBinding binding;
    NavController navController;

    // 취소선 관련 필드
    private TextView textView;
    private boolean isStrikeThrough = false;

    // 이미지 슬라이더 관련 필드
    private ViewPager2 viewPager2;
    private LinearLayout linearLayout;
    private TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProdDetailBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);

        // 앱바 설정
        initMenu();

        // Bundle에서 객체 받아오기
        Bundle args = getArguments();
        if (args != null) {
            Product product = (Product) args.getSerializable("product");

            if (product != null) {
                int pg_no = product.getPg_no();

                // 상품상세정보 불러오기
                initLoadInfo(pg_no);
            }
        }

        // 취소선 추가
        binding.prdOrgPrice.setPaintFlags(binding.prdOrgPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        // 하단 네비게이션바 숨기기
        hideBottomNavigation(true);

        showBottomSheet();

        // 탭 메뉴 이동
        initTabPage();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

    private void initLoadInfo(int pg_no) {
        // 어댑터 생성
        ProductDetailAdapter productDetailAdapter = new ProductDetailAdapter(getContext(), pg_no);
        // API 서버에서 목록 받기
        ProductDetailService productDetailService = ServiceProvider.getProductDetailService(getContext());
        Call<Product> call = productDetailService.getDetailView(pg_no);

        // 이미지 슬라이더 내용 추가
        binding.productImageSlider.setAdapter(productDetailAdapter);

        // 탭 레이아웃과 이미지 슬라이더를 연결
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                binding.tabLayout, binding.productImageSlider, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
            }
        });

        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                Product product = response.body();
                Log.i(TAG, "onResponse:" + product);
                binding.pgName.setText(product.getPg_name());
                binding.discountRate.setText((String.valueOf(product.getSale_percent())) + "%");
                binding.prdPrice.setText((String.valueOf(product.getPrd_price())) + "원");
                binding.prdOrgPrice.setText((String.valueOf(product.getPrd_org_price())) + "원");
                binding.productRating.setRating(product.getPrd_star_avg());
                binding.reviewCount.setText("(" + String.valueOf((product.getRevCnt())) + ")");
                binding.shippingFee.setText(String.valueOf(product.getPrd_ship_fee()));
                binding.sellerName.setText(product.getSel_name());

                ProductDetailService.loadDetailImage(product.getPg_no(), binding.prdDetailImage);
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                t.printStackTrace();
            }
        });

        // 항목을 클릭했을 때 콜백 겍체를 등록
/*        boardAdapter.setOnItemClickListener(new BoardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Log.i(TAG, position +  " 항목 클릭됨");
                Board board = boardAdapter.getItem(position);

                Bundle args = new Bundle();
                args.putSerializable("board", board);
                navController.navigate(R.id.action_dest_list_to_dest_detail, args);
            }
        });*/
        // TabLayoutMediator 활성화하기
        tabLayoutMediator.attach();
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

    private void showBottomSheet() {
    }

    private void initTabPage() {
        binding.tabProductReviews.setOnClickListener(v -> {
            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.dest_review, false)
                    .setLaunchSingleTop(true)
                    .build();

            navController.navigate(R.id.dest_review, null, navOptions);
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
    public void onPause() {
        super.onPause();
        hideBottomNavigation(false);
    }


}