package com.appteam4.postella.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentSearchBinding;
import com.appteam4.postella.dto.Product;
import com.appteam4.postella.service.ProductGroupService;
import com.appteam4.postella.service.ServiceProvider;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private static final String TAG = "SearchFragment";
    private FragmentSearchBinding binding;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);

        // AppBar 숨기기
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();


        // 검색어 입력 부분을 가장 위에 배치
        View searchView = binding.searchGoList.getRootView();
        ViewGroup parent = (ViewGroup) searchView.getParent();
        if (parent != null) {
            parent.removeView(searchView);
            parent.addView(searchView, 0);
        }

        //RecyclerView 초기화
        initRecyclerView();
        //검색버튼 클릭
        initSearchClick();
        //하단 네비게이션바 지우기
        hideBottomNavigation(true);

        return binding.getRoot();
    }

    private void initSearchClick(){
        binding.searchGoList.setStartIconOnClickListener(v->{
            // 검색어 입력값 가져오기
            String searchKeyword = binding.editText.getText().toString();

            // 검색어를 다음 프래그먼트로 전달 (Bundle을 사용하여 전달)
            Bundle args = new Bundle();
            args.putString("keyword", searchKeyword);
            Log.i(TAG, "initSearchClick: 키워두" + searchKeyword);

            // 상품목록프레그먼트로 이동
            navController.navigate(R.id.action_dest_search_to_dest_prod_list, args);
        });
    }

    private void initRecyclerView() {
        // RecyclerView에서 항목을 수평으로 배치하도록 설정
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1 , GridLayoutManager.HORIZONTAL, false);
        binding.recyclerSearchView.setLayoutManager(layoutManager);

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
                    binding.recyclerSearchView.setAdapter(searchAdapter);
                    // RecyclerView를 보이도록 설정
                    binding.recyclerSearchView.setVisibility(View.VISIBLE);
                } else {
                    Log.i(TAG, "onResponse: 리스트가 없엉!!");
                }
                // RecyclerView에 어댑터 세팅
                // 어댑터에 데이터가 설정된 후 notifyDataSetChanged() 호출
                binding.recyclerSearchView.setAdapter(searchAdapter);
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
                navController.navigate(R.id.action_dest_search_to_dest_prod_detail2);
            }
        });
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

    @Override
    public void onPause() {
        super.onPause();
        //하단 네비게이션 바 표시
        hideBottomNavigation(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // AppBar 표시
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }
}