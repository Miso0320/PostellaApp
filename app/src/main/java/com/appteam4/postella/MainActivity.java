package com.appteam4.postella;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.appteam4.postella.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_main);
        navController = navHostFragment.getNavController();

        // 하단 탐색 뷰와 NavController 연동
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);

        // 앱바 추가하기
        initAppbar();
        
    }

    private void initAppbar() {
        // Toolbar를 앱바로 설정
        setSupportActionBar(binding.toolbar);

        // 그래프상에서 최상위 수준 대상을 지정
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();

        // Toolbar만 단독으로 사용
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration);
    }
}