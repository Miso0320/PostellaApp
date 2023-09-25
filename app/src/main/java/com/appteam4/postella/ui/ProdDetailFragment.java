package com.appteam4.postella.ui;

import android.app.FragmentTransaction;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentProdDetailBinding;
import com.appteam4.postella.datastore.AppKeyValueStore;
import com.appteam4.postella.dto.MyWish;
import com.appteam4.postella.dto.Product;
import com.appteam4.postella.dto.ProductDetailViewModel;
import com.appteam4.postella.dto.WishResult;
import com.appteam4.postella.service.ProductDetailService;
import com.appteam4.postella.service.ServiceProvider;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

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

    // 상품상세 이미지목록 개수
    private int imgCnt;

    // ViewModel에 데이터 저장
    private ProductDetailViewModel viewModel;

    // bottom sheet
    private View bottomSheetView;
    private BottomSheetBehavior bottomSheetBehavior;

    // 상품그룹식별번호
    private int pg_no;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ViewModel 생성하기
        viewModel = new ViewModelProvider(this).get(ProductDetailViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProdDetailBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);

        // 앱바 설정
        initMenu();

        // Bundle에서 객체 받아오기
        Bundle args = getArguments();
        Log.i(TAG, "번들이 받아지나용? **************" + args);
        if (args != null) {
            if (args.getSerializable("product") != null) {
                Product product = (Product) args.getSerializable("product");
                if (product != null) {
                    pg_no = product.getPg_no();
                    viewModel.setPgNo(pg_no);
                    // 상품상세 이미지 개수 받아오기
                    initImgCnt(pg_no);
                }
            } else {
                pg_no = args.getInt("pg_no");
                Log.i(TAG, "상품번호를 받기**************" + pg_no);
                viewModel.setPgNo(pg_no);
                // 상품상세 이미지 개수 받아오기
                initImgCnt(pg_no);
            }
        }

        // bottom sheet
        ProdDetailBottomSheetFragment prodDetailBottomSheetFragment = new ProdDetailBottomSheetFragment(pg_no);

        // 버튼 클릭 시 bottom sheet를 보이도록 설정
        binding.btnBuy.setOnClickListener(v -> {
            prodDetailBottomSheetFragment.show(getChildFragmentManager(), prodDetailBottomSheetFragment.getTag());
        });

        // 취소선 추가
        binding.prdOrgPrice.setPaintFlags(binding.prdOrgPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

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
     * 앱바 설정
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
     * 상품상세 이미지 개수 받아오기
     *
     * @param pg_no 상품 그룹 번호
     */
    private void initImgCnt(int pg_no) {
        // API 서버에서 목록 받기
        ProductDetailService productDetailService = ServiceProvider.getProductDetailService(getContext());
        Call<Integer> callCnt = productDetailService.loadProductImageCnt(pg_no);

        callCnt.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                imgCnt = response.body();
                // 데이터를 ViewModel에 저장
                viewModel.setImgCnt(imgCnt);
                // 상품상세정보 불러오기
                initLoadInfo();
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /**
     * 상품상세정보 불러오기
     */
    private void initLoadInfo() {
        // ViewModel에서 데이터 가져오기
        int imgCnt = viewModel.getImgCnt();
        int pg_no = viewModel.getPgNo();

        // API 서버에서 목록 받기
        ProductDetailService productDetailService = ServiceProvider.getProductDetailService(getContext());
        Call<Product> callProdDetail = productDetailService.getDetailView(pg_no);

        // 어댑터 생성
        ProductDetailAdapter productDetailAdapter = new ProductDetailAdapter(getContext(), pg_no, imgCnt);

        // 이미지 슬라이더 내용 추가
        binding.productImageSlider.setAdapter(productDetailAdapter);

        // 탭 레이아웃과 이미지 슬라이더를 연결
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                binding.tabLayout, binding.productImageSlider, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
            }
        });

        callProdDetail.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                Product product = response.body();

                // 한화 통화 형식을 사용
                DecimalFormat df = new DecimalFormat("#,###");

                binding.pgName.setText(product.getPg_name());
                binding.discountRate.setText((String.valueOf(product.getSale_percent())) + "%");
                binding.prdPrice.setText(df.format(product.getPrd_price()) + "원");
                binding.prdOrgPrice.setText(df.format(product.getPrd_org_price()) + "원");
                binding.productRating.setRating(product.getPrd_star_avg());
                binding.reviewCount.setText("(" + String.valueOf((product.getRevCnt())) + ")");
                binding.shippingFee.setText(df.format(product.getPrd_ship_fee()));
                binding.sellerName.setText(product.getSel_name());

                // 이미지 받아오기
                ProductDetailService.loadDetailImage(product.getPg_no(), binding.prdDetailImage);
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                t.printStackTrace();
            }
        });

        // TabLayoutMediator 활성화하기
        tabLayoutMediator.attach();

        // 체크박스 이벤트 처리
        binding.checkboxFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    Snackbar snackbar = Snackbar.make(getView(), "상품을 나의 찜목록에 담았어요!", Snackbar.LENGTH_SHORT);
                    snackbar.setAction("바로가기", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // 바로가기 클릭 시 찜목록 프래그먼트로 이동
                            NavController navController = NavHostFragment.findNavController(ProdDetailFragment.this);
                            navController.navigate(R.id.dest_wish_list, null);
                        }
                    });
                    snackbar.show();
                }
            }
        });
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

    /**
     * 상단 탭메뉴 이동
     *
     * @param args Bundle 객체 전달
     */
    private void initTabPage(Bundle args) {
        binding.tabProductReviews.setOnClickListener(v -> {
            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.dest_review, false)
                    .setLaunchSingleTop(true)
                    .build();

            // 이미 받아온 데이터를 사용
            if (viewModel.getImgCnt() > 0) {
                // 데이터가 이미 있다면 데이터를 사용하고 프래그먼트 이동
                navController.navigate(R.id.dest_review, args, navOptions);
            } else {
                // 데이터가 없다면 데이터를 받아오고 이후에 프래그먼트 이동
                initImgCnt(viewModel.getPgNo());
            }
        });

        binding.tabProductInquiries.setOnClickListener(v -> {
            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.dest_inquiry, false)
                    .setLaunchSingleTop(true)
                    .build();

            // 이미 받아온 데이터를 사용
            if (viewModel.getImgCnt() > 0) {
                // 데이터가 이미 있다면 데이터를 사용하고 프래그먼트 이동
                navController.navigate(R.id.dest_inquiry, args, navOptions);
            } else {
                // 데이터가 없다면 데이터를 받아오고 이후에 프래그먼트 이동
                initImgCnt(viewModel.getPgNo());
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        // 하단 네비게이션바 보이기
        hideBottomNavigation(false);
    }
}