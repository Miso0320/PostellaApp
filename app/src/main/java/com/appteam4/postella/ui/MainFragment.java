package com.appteam4.postella.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentMainBinding;
import com.appteam4.postella.datastore.AppKeyValueStore;
import com.appteam4.postella.dto.MyWish;
import com.appteam4.postella.dto.Product;
import com.appteam4.postella.dto.WishResult;
import com.appteam4.postella.service.ProductGroupService;
import com.appteam4.postella.service.ServiceProvider;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment {

    private static final String TAG = "MainFragment";
    private FragmentMainBinding binding;
    private NavController navController;
    private NestedScrollView nestedScrollView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);
        Log.i(TAG, "onStop: 실행");
        binding.btnScrollToTop.hide();
        // AppBar 나타나기
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        // 메뉴 초기화
        initMenu();
        //상단 광고 초기화
        initPagerView();
        //RecyclerView 초기화
        initRecyclerView();
        initRecyclerView2();
        //NestedScrollView 초기화
        initNestedSCrollView();

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Fragment가 파괴되면 타이머를 정지시킴
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }

    private void initMenu() {
        MenuProvider menuProvider = new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.nav_menu_top, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                // us_email 얻기
                String us_email = AppKeyValueStore.getValue(getContext(), "us_email");

                if(menuItem.getItemId() == R.id.dest_search) {
                    navController.navigate(R.id.action_dest_main_to_dest_search);
                    return true;
                } else if(menuItem.getItemId() == R.id.dest_cart) {
                    if (us_email == null) {
                        navController.navigate(R.id.dest_login);
                    } else {
                        navController.navigate(R.id.action_dest_main_to_dest_cart);
                    }
                    return true;
                } else if(menuItem.getItemId() == R.id.dest_category){
                    return true;
                }
                return false;
            }
        };
        getActivity().addMenuProvider(menuProvider, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }
    private void initPagerView() {
        MainAdPagerAdapter mainAdPagerAdapter = new MainAdPagerAdapter(this);
        binding.viewPager.setAdapter(mainAdPagerAdapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                binding.tabLayout, binding.viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
            }
        });
        tabLayoutMediator.attach();
        // Timer를 사용하여 이미지 자동 슬라이드 시작
        startAutoSlide();
    }

    //Timer: 일정 시간간격으로 작업을 실행하기 위함
    private Timer timer;
    //현재 페이지 번호를 받기위함
    private int currentPage = 0;
    private void startAutoSlide(){
        //Runnable update: 타이머가 실행될 때마다 호출되는 Runnable 객체
        //Runnable 내부에서 현재 페이지 번호를 확인하고, 페이지 번호를 증가시킴
        final Runnable update = () -> {
            //현재 페이지가 5에 도달하면 다시 0으로 초기화
            if(currentPage == 5){
                currentPage = 0;
            }
            binding.viewPager.setCurrentItem(currentPage++, true);
        };
        timer = new Timer();

        //Timer를 사용하여 작업을 예약
        timer.schedule(new TimerTask() {
            //실행될 작업을 지정하는 TimerTask 객체를 전달
            @Override
            public void run() {
                getActivity().runOnUiThread(update);
            }
        }, 2000, 2000); // 이미지 전환 간격 2초(작업을 실행하기 전의 지연 시간,업을 반복할 간격)
    }

    private void initRecyclerView() {
        // RecyclerView에서 항목을 배치하도록 설정
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        binding.recyclerMainView.setLayoutManager(layoutManager);

        // 어댑터 생성
        MainAdapter mainAdapter = new MainAdapter();

        // API 서버에서 목록 받기
        ProductGroupService productGroupService = ServiceProvider.getProductList(getContext());
        Call<List<Product>> call = productGroupService.getProductList();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                // json -> List<Product>
                List<Product> list = response.body();
                if (list != null) {
                    // 어댑터 데이터 생성하기
                    mainAdapter.setList(list);
                    // RecyclerView에 어댑터 세팅
                    binding.recyclerMainView.setAdapter(mainAdapter);
                    // RecyclerView를 보이도록 설정
                    binding.recyclerMainView.setVisibility(View.VISIBLE);
                } else {
                    Log.i(TAG, "onResponse: 리스트가 널이여~");
                }
                // RecyclerView에 어댑터 세팅
                // 어댑터에 데이터가 설정된 후 notifyDataSetChanged() 호출
                binding.recyclerMainView.setAdapter(mainAdapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, "API 호출 실패", t);
            }
        });
        // 항목 클릭시 콜백 메소드 등록
        mainAdapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Product product = mainAdapter.getItem(position);
                Log.i(TAG, product.toString());

                Bundle args = new Bundle();
                args.putSerializable("product", product);
                navController.navigate(R.id.action_dest_main_to_dest_prod_detail, args);
            }
        });
        // MainAdapter에 MainFragment의 참조 설정
        mainAdapter.setMainFragment(this); // MainFragment의 참조 설정
    }

    private void initRecyclerView2() {
        // RecyclerView에서 항목을 수평으로 배치하도록 설정
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1 , GridLayoutManager.HORIZONTAL, false);
        binding.recyclerRecoView.setLayoutManager(layoutManager);

        // 어댑터 생성
        SearchAdapter searchAdapter = new SearchAdapter();

        // API 서버에서 목록 받기
        ProductGroupService productGroupService = ServiceProvider.getProductList(getContext());
        Call<List<Product>> call = productGroupService.getProductList();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                // json -> List<Product>
                List<Product> list = response.body();
                if (list != null) {
                    // 어댑터 데이터 생성하기
                    searchAdapter.setList(list);
                    // RecyclerView에 어댑터 세팅
                    binding.recyclerRecoView.setAdapter(searchAdapter);
                    // RecyclerView를 보이도록 설정
                    binding.recyclerRecoView.setVisibility(View.VISIBLE);
                } else {
                    Log.i(TAG, "onResponse: 리스트가 없엉!!");
                }
                // RecyclerView에 어댑터 세팅
                // 어댑터에 데이터가 설정된 후 notifyDataSetChanged() 호출
                binding.recyclerRecoView.setAdapter(searchAdapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, "API 호출 실패", t);
            }
        });
        // 항목 클릭시 콜백 메소드 등록
        searchAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Product product = searchAdapter.getItem(position);
                Log.i(TAG, product.toString());

                Bundle args = new Bundle();
                args.putSerializable("product", product);
                navController.navigate(R.id.action_dest_main_to_dest_prod_detail, args);
            }
        });
    }

    private void initNestedSCrollView(){
        // NestedScrollView 초기화
        nestedScrollView = binding.getRoot().findViewById(R.id.nest_scroll_main_view);
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if((!v.canScrollVertically(-1))){
                binding.btnScrollToTop.hide();
            }else {
                binding.btnScrollToTop.show();
            }

        });
        binding.btnScrollToTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // NestedScrollView를 맨 위로 스크롤
                nestedScrollView.smoothScrollTo(0, 0);
            }
        });
    }

    //찜 추가 요청
    public void addWish(Product product) {
        if(AppKeyValueStore.getValue(getContext(), "us_no")!=null){
            int us_no = Integer.parseInt(AppKeyValueStore.getValue(getContext(), "us_no"));
            // 찜 추가 API 요청 보내기
            MyWish wish = new MyWish(); // 적절한 Wish 객체 생성 (상품 ID를 전달)
            wish.setPg_no(product.getPg_no());
            wish.setUs_no(us_no);
            AppKeyValueStore.put(getContext(),"wishPg_no", String.valueOf(product.getPg_no()));
            ProductGroupService productGroupService = ServiceProvider.getProductList(getContext());
            Call<WishResult> call = productGroupService.addWish(wish);
            call.enqueue(new Callback<WishResult>() {
                @Override
                public void onResponse(Call<WishResult> call, Response<WishResult> response) {
                    WishResult wishResult = response.body(); // WishResult 객체 가져오기
                    // API 요청이 성공했을 때의 처리
                    if (response.isSuccessful()) {
                        if(wishResult != null){
                            String result = wishResult.getResult();
                            // 찜 목록에서 상품을 추가한 상태로 업데이트
                            Snackbar snackbar = Snackbar.make(getView(), "상품을 나의 찜목록에 담았어요! 바로가기", Snackbar.LENGTH_SHORT);
                            if(result.equals("success")){
                                // 찜 목록에서 상품을 추가한 상태로 업데이트
                                snackbar.setAction("바로가기", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        // 바로가기 클릭 시 찜목록 프래그먼트로 이동
                                        NavController navController = NavHostFragment.findNavController(MainFragment.this);
                                        navController.navigate(R.id.action_dest_main_to_dest_wish_list);
                                    }
                                });
                                snackbar.show();
                            }else{
                                if (snackbar != null && snackbar.isShown()) {
                                    snackbar.dismiss();
                                }
                            }
                        }
                    }else {
                        Log.i(TAG, "onResponse: 왜안되는교" + response.isSuccessful());
                    }
                }
                @Override
                public void onFailure(Call<WishResult> call, Throwable t) {
                    // API 요청 실패 시 예외 처리
                    Log.e(TAG, "API 호출 실패", t);
                    // 오류 메시지를 얻어와서 로그로 출력
                    String errorMessage = t.getMessage(); // 예외 메시지 얻기
                    Log.e(TAG, "API 오류 메시지: " + errorMessage);
                }
            });
        }else {
            navController.navigate(R.id.action_dest_main_to_dest_login);
        }
    }
    //찜 삭제 요청
    public void removeWish(Product product) {
        if (AppKeyValueStore.getValue(getContext(), "us_no") != null) {
            int us_no = Integer.parseInt(AppKeyValueStore.getValue(getContext(), "us_no"));
            // 찜 추가 API 요청 보내기
            MyWish wish = new MyWish(); // 적절한 Wish 객체 생성 (상품 ID를 전달)
            wish.setPg_no(product.getPg_no());
            wish.setUs_no(us_no);
            ProductGroupService productGroupService = ServiceProvider.getProductList(getContext());
            Call<WishResult> call = productGroupService.removeWish(wish);
            call.enqueue(new Callback<WishResult>() {
                @Override
                public void onResponse(Call<WishResult> call, Response<WishResult> response) {
                    WishResult wishResult = response.body();
                    // API 요청이 성공했을 때의 처리
                    if (response.isSuccessful()) {
                        Log.i(TAG, "onResponse: 삭제 여부" + wishResult.getResult());
                        Snackbar snackbar = Snackbar.make(getView(), "찜목록에서 상품을 삭제하였습니다!", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    } else {
                        Log.i(TAG, "onResponse: 왜안되는교" + call.toString());
                    }
                }

                @Override
                public void onFailure(Call<WishResult> call, Throwable t) {
                    // API 요청 실패 시 예외 처리
                    Log.e(TAG, "API 호출 실패", t);
                    // 오류 메시지를 얻어와서 로그로 출력
                    String errorMessage = t.getMessage(); // 예외 메시지 얻기
                    Log.e(TAG, "API 오류 메시지: " + errorMessage);
                }
            });
        }else {
            navController.navigate(R.id.action_dest_main_to_dest_login);
        }
    }

}