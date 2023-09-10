package com.appteam4.postella;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.appteam4.postella.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;

    private AppBarConfiguration appBarConfiguration;
    private DrawerLayout drawerLayout;

    private NavController navController;
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

        initDrawerLayout();
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
                // 추가적인 대상 ID를 여기에 추가
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

        // 네비게이션 메뉴 아이템 클릭 처리
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
           /* switch (id) {
                case R.id.nav_item1:
                    // Item 1을 클릭한 경우의 동작 처리
                    break;
                case R.id.nav_item2:
                    // Item 2를 클릭한 경우의 동작 처리
                    break;
                case R.id.nav_item3:
                    // Item 3을 클릭한 경우의 동작 처리
                    break;
                case R.id.nav_item4:
                    // Item 4를 클릭한 경우의 동작 처리
                    break;
            }*/
            // 네비게이션 드로어를 닫습니다.
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }
}