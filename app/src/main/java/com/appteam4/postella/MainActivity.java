package com.appteam4.postella;

import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.appteam4.postella.databinding.ActivityMainBinding;
import com.appteam4.postella.ui.MainFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

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
    private boolean isVisible = false;
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

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.dest_review, false)
                    .setLaunchSingleTop(true)
                    .build();

            if (item.getItemId() == R.id.dest_main) {
                navController.navigate(R.id.dest_main, null, navOptions);
                return true;
            } else if (item.getItemId() == R.id.dest_ad_list) {
                navController.navigate(R.id.dest_ad_list, null, navOptions);
                return true;
            } else if (item.getItemId() == R.id.dest_wish_list) {
                navController.navigate(R.id.dest_wish_list, null, navOptions);
                return true;
            } else if (item.getItemId() == R.id.dest_mypage_order_list) {
                navController.navigate(R.id.dest_mypage_order_list, null, navOptions);
                return true;
            }
            return false;
        });

        // ActionBar와 NavController 연결
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // ActionBar의 Up 버튼을 사용하여 네비게이션 드로어 열기/닫기
        NavigationUI.setupWithNavController(navigationView, navController);

        // 메뉴 아이템 참조 가져오기
        MenuItem menuItem = navigationView.getMenu().findItem(R.id.c_main_item);

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
            // 현재 액티비티에서 FragmentManager를 가져옵니다.
            FragmentManager fragmentManager = getSupportFragmentManager();
            // 현재 액티비티에 표시되는 현재 프래그먼트를 가져옴
            Fragment currentFragment = fragmentManager.findFragmentById(R.id.nav_main).getChildFragmentManager().getFragments().get(0);
            // 네비게이션 드로어가 열린 상태에서만 드로어 메뉴로 이동
            if (currentFragment instanceof MainFragment) {
                Log.i(TAG, "onClick: 실행");
                drawerLayout.openDrawer(GravityCompat.START);
            }else {
                navController.popBackStack();
            }
        });

        // 네비게이션 메뉴 아이템 클릭 처리
        navigationView.setNavigationItemSelectedListener(item -> {
            drawerLayout = findViewById(R.id.drawer_layout);
            Menu menu = navigationView.getMenu();
            int itemId = item.getItemId();
            // 카테고리 항목 선택 이벤트 리스너
            if(itemId == R.id.c_main_item){
                // 카테고리 항목을 클릭하면 드롭다운 메뉴 그룹의 가시성을 토글
                isVisible = !isVisible;
                menu.setGroupVisible(R.id.group_category, isVisible);
                Log.i(TAG, "initDrawerLayout: " + isVisible);
            }else if(itemId == R.id.b_main_item){
                // 브랜드 항목을 클릭하면 드롭다운 메뉴 그룹의 가시성을 토글
                boolean isVisible = menu.findItem(R.id.b_sub_item1).isVisible();
                menu.setGroupVisible(R.id.group_brand, !isVisible);
            }else if(itemId == R.id.m_main_item) {
                // 메시지 항목을 클릭하면 드롭다운 메뉴 그룹의 가시성을 토글
                boolean isVisible = menu.findItem(R.id.m_sub_item1).isVisible();
                menu.setGroupVisible(R.id.group_message, !isVisible);
            }else if(itemId == R.id.c_sub_item1){
                navController.navigate(R.id.dest_category);
                drawerLayout.closeDrawer(GravityCompat.START);
            }else if(itemId == R.id.c_sub_item2){
                navController.navigate(R.id.dest_category);
                drawerLayout.closeDrawer(GravityCompat.START);
            }else if(itemId == R.id.c_sub_item3){
                navController.navigate(R.id.dest_category);
                drawerLayout.closeDrawer(GravityCompat.START);
            }else if(itemId == R.id.c_sub_item4){
                navController.navigate(R.id.dest_category);
                drawerLayout.closeDrawer(GravityCompat.START);
            }else if(itemId == R.id.b_sub_item1){
                navController.navigate(R.id.dest_category);
                drawerLayout.closeDrawer(GravityCompat.START);
            }else if(itemId == R.id.b_sub_item2){
                navController.navigate(R.id.dest_category);
                drawerLayout.closeDrawer(GravityCompat.START);
            }else if(itemId == R.id.b_sub_item3){
                navController.navigate(R.id.dest_category);
                drawerLayout.closeDrawer(GravityCompat.START);
            }else if(itemId == R.id.b_sub_item4){
                navController.navigate(R.id.dest_category);
                drawerLayout.closeDrawer(GravityCompat.START);
            }else if(itemId == R.id.b_sub_item5){
                navController.navigate(R.id.dest_category);
                drawerLayout.closeDrawer(GravityCompat.START);
            }else if(itemId == R.id.m_sub_item1){
                navController.navigate(R.id.dest_category);
                drawerLayout.closeDrawer(GravityCompat.START);
            }else if(itemId == R.id.m_sub_item2){
                navController.navigate(R.id.dest_category);
                drawerLayout.closeDrawer(GravityCompat.START);
            }else if(itemId == R.id.m_sub_item3){
                navController.navigate(R.id.dest_category);
                drawerLayout.closeDrawer(GravityCompat.START);
            }else if(itemId == R.id.m_sub_item4){
                navController.navigate(R.id.dest_category);
                drawerLayout.closeDrawer(GravityCompat.START);
            }else if(itemId == R.id.m_sub_item5){
                navController.navigate(R.id.dest_category);
                drawerLayout.closeDrawer(GravityCompat.START);
            }else{
                // 다른 항목 클릭 시 카테고리 메뉴 닫기
                menu.setGroupVisible(R.id.group_category, false);
                // 네비게이션 드로어 닫기
                drawerLayout.closeDrawer(GravityCompat.START);
            }
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