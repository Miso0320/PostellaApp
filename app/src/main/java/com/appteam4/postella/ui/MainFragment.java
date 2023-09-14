package com.appteam4.postella.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentMainBinding;
import com.appteam4.postella.dto.Product;
import com.appteam4.postella.service.ProductGroupService;
import com.appteam4.postella.service.ServiceProvider;
import com.google.android.material.navigation.NavigationView;
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
                if(menuItem.getItemId() == R.id.dest_search) {
                    navController.navigate(R.id.action_dest_main_to_dest_search);
                    return true;
                } else if(menuItem.getItemId() == R.id.dest_cart) {
                    navController.navigate(R.id.action_dest_main_to_dest_cart);
                    return true;
                }else if(menuItem.getItemId() == R.id.dest_category){
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
                navController.navigate(R.id.action_dest_main_to_dest_prod_detail);
            }
        });
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
                navController.navigate(R.id.action_dest_main_to_dest_prod_detail);
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

}