package com.appteam4.postella;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.appteam4.postella.databinding.ActivityMainBinding;
import com.appteam4.postella.ui.MainFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;

    private AppBarConfiguration appBarConfiguration;
    private DrawerLayout drawerLayout;

    private NavController navController;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // ActionBar 설정
        setSupportActionBar(binding.toolbar);

        //NavController 가져오기
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_main);
        navController = navHostFragment.getNavController();

        // 하단 탐색 뷰와 NavController 연동
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);
        // 앱바 추가하기
        initAppbar();
        //drawerlayout 메뉴 추가하기
        initDrawerLayout();
        
    }
    private void initAppbar() {
        // Toolbar를 앱바로 설정
        setSupportActionBar(binding.toolbar);

        // 그래프상에서 최상위 수준 대상을 지정
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();

        // Toolbar만 단독으로 사용
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration);
    }

    private void initDrawerLayout(){
        //네비게이션 드로어의 DrawerLayout 가져오기
        drawerLayout = findViewById(R.id.drawer_layout);

        //네비게이션 뷰 가져오기
        NavigationView navigationView = findViewById(R.id.navigation_view);
        // 네비게이션 그래프 설정
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.dest_main,
                R.id.dest_category,
                R.id.dest_search,
                R.id.dest_cart
        )
                .setOpenableLayout(drawerLayout)
                .build();

        //NavController 가져오기
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_main);
        navController = navHostFragment.getNavController();
        // ActionBar와 NavController 연결
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // ActionBar의 Up 버튼을 사용하여 네비게이션 드로어 열기/닫기
        NavigationUI.setupWithNavController(navigationView, navController);

        // 최상위 창 아이콘 (툴바의 네비게이션 아이콘) 클릭 이벤트 처리
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close

        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // 최상위 창 아이콘 클릭 이벤트 처리
        binding.toolbar.setNavigationOnClickListener(v -> {
            // 네비게이션 드로어가 열린 상태에서만 CategoryFragment로 이동
            Log.i(TAG, "onClick: 실행");
            drawerLayout.openDrawer(GravityCompat.START);
        });


        // 네비게이션 메뉴 아이템 클릭 처리
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            // 네비게이션 드로어 닫기
            drawerLayout.closeDrawer(GravityCompat.START);
            // MainFragment로 이동 (네비게이션 드로어가 닫힌 후)
            navController.popBackStack();

            return true;
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        FragmentManager fragmentManager = getSupportFragmentManager();
        int backStackEntryCount = fragmentManager.getBackStackEntryCount();
    }
}