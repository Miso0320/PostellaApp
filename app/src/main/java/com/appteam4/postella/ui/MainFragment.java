package com.appteam4.postella.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.appteam4.postella.MainActivity;
import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Timer;
import java.util.TimerTask;

public class MainFragment extends Fragment {

    private static final String TAG = "MainFragment";
    private FragmentMainBinding binding;
    private NavController navController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);

        // 메뉴 초기화
        initMenu();
        //상단 광고 초기화
        initPagerView();

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
        }, 3000, 3000); // 이미지 전환 간격 3초(작업을 실행하기 전의 지연 시간,업을 반복할 간격)
    }
}