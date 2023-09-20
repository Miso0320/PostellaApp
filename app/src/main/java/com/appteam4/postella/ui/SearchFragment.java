package com.appteam4.postella.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentSearchBinding;
import com.appteam4.postella.datastore.AppKeyValueStore;
import com.appteam4.postella.dto.Product;
import com.appteam4.postella.service.ProductGroupService;
import com.appteam4.postella.service.ServiceProvider;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.nio.file.ClosedFileSystemException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private static final String TAG = "SearchFragment";
    private FragmentSearchBinding binding;
    private NavController navController;

    private SearchAdapter.OnItemClickListener onItemClickListener;

    private SearchKeywordViewHolder searchKeywordViewHolder;

    //SearchKeywordViewHolder 참조 설정
    public void setSearchKeywordViewHolder(SearchKeywordViewHolder searchKeywordViewHolder){
        this.searchKeywordViewHolder = searchKeywordViewHolder;
    }

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
        //최근 검색어 편집 및 추천 검색어 기능 초기화
        initKeyword();
        //최근 검색어 recyclerView 초기화
        initKeywordRecyclerView();
        setSearchKeywordViewHolder(this.searchKeywordViewHolder);


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
            if(searchKeyword.isEmpty()){
                Snackbar snackbar = Snackbar.make(binding.getRoot(),"검색어를 입력해 주세요.", Snackbar.LENGTH_LONG);
                snackbar.show();
            }else{
                // 상품목록프레그먼트로 이동
                navController.navigate(R.id.action_dest_search_to_dest_prod_list, args);
                // 검색어를 저장
                AppKeyValueStore.addRecentSearchKeyword(requireContext(), searchKeyword);
            }
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
                navController.navigate(R.id.action_dest_search_to_dest_prod_detail2, args);
            }
        });
    }
    
    //하단 네비게이션 바 지우기 여부
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

    //최근검색어 클릭이벤트 및 편집 클릭이벤트 처리
    private void initKeyword(){
        NavOptions navOptions = new NavOptions.Builder()
                .setPopUpTo(R.id.dest_review, false)
                .setLaunchSingleTop(true)
                .build();
        //편집모드에서만 "전체삭제|닫기"보이기
        String isEdit = AppKeyValueStore.getValue(requireContext(),"editMode");
        if(isEdit.equals("off")){
            binding.txtLogDeleteAll.setVisibility(View.GONE);
            binding.line.setVisibility(View.GONE);
            binding.txtLogEditClose.setVisibility(View.GONE);
            //"전체삭제|닫기"가 보이지 않을 때 편집 글씨가 오른쪽 끝에 가도록 마진 변경
            // 현재 뷰의 레이아웃 파라미터 가져오기
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) binding.txtRecentKeyword.getLayoutParams();
            // 오른쪽 마진 값을 변경
            layoutParams.rightMargin = 700;
            // 변경된 레이아웃 파라미터 설정
            binding.txtRecentKeyword.setLayoutParams(layoutParams);
        }else if(isEdit.equals("on")){
            binding.txtLogDeleteAll.setVisibility(View.VISIBLE);
            binding.line.setVisibility(View.VISIBLE);
            binding.txtLogEditClose.setVisibility(View.VISIBLE);
            //"편집"글씨 클릭시 마진 변경 후 "전체삭제|닫기" 다시 보이게 하기
            // 현재 뷰의 레이아웃 파라미터 가져오기
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) binding.txtRecentKeyword.getLayoutParams();
            // 오른쪽 마진 값을 변경
            layoutParams.rightMargin =350;
            // 변경된 레이아웃 파라미터 설정
            binding.txtRecentKeyword.setLayoutParams(layoutParams);
        }
        //"전체삭제|닫기"가 보이지 않을 때 편집 글씨가 오른쪽 끝에 가도록 마진 변경
        if(binding.txtLogDeleteAll.getVisibility() == View.GONE){
            // 현재 뷰의 레이아웃 파라미터 가져오기
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) binding.txtRecentKeyword.getLayoutParams();
            // 오른쪽 마진 값을 변경
            layoutParams.rightMargin = 700;
            // 변경된 레이아웃 파라미터 설정
            binding.txtRecentKeyword.setLayoutParams(layoutParams);
        }
        //"편집"글씨 클릭시 마진 변경 후 "전체삭제|닫기" 다시 보이게 하기
        binding.txtLogEdit.setOnClickListener(v->{
            //편집 모드 ON
            AppKeyValueStore.remove(requireContext(),"editMode");
            AppKeyValueStore.put(requireContext(),"editMode","on");
            navController.navigate(R.id.action_dest_search_self, null, navOptions);
        });
        binding.txtLogDeleteAll.setOnClickListener(v->{
            AppKeyValueStore.clearRecentSearchKeywords(requireContext());
            navController.navigate(R.id.action_dest_search_self, null, navOptions);
        });
        //닫기 글씨 클릭시 마진 변경 후 다시 "전체삭제|닫기" 지우기
        binding.txtLogEditClose.setOnClickListener(v->{
            AppKeyValueStore.remove(requireContext(),"editMode");
            AppKeyValueStore.put(requireContext(),"editMode","off");
            navController.navigate(R.id.action_dest_search_self, null, navOptions);
        });
        //추천검색어 info버튼 클릭시 스낵바 보이게 하기
        binding.btnInfo.setOnClickListener(v->{
            Snackbar snackbar = Snackbar.make(binding.btnInfo, "최근 검색어와 연관된 검색어를 추천해드려요.", Snackbar.LENGTH_LONG);
            View snackbarView = snackbar.getView();

            // 스낵바 레이아웃 파라미터 설정
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) snackbarView.getLayoutParams();
            params.setAnchorId(R.id.btn_info); // 원하는 뷰를 설정하여 연결
            params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL; // 원하는 위치로 설정 (아래 중앙)
            snackbarView.setLayoutParams(params);

            snackbar.show();
        });
        binding.btnRecoSensitivity.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            bundle.putString("keyword", "감성엽서");
            AppKeyValueStore.addRecentSearchKeyword(requireContext(),"감성엽서");
            navController.navigate(R.id.action_dest_search_to_dest_prod_list, bundle);
        });
        binding.btnRecoInterior.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            bundle.putString("keyword", "인테리어 엽서");
            AppKeyValueStore.addRecentSearchKeyword(requireContext(),"인테리어 엽서");
            navController.navigate(R.id.action_dest_search_to_dest_prod_list, bundle);
        });
        binding.btnRecoMong.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            bundle.putString("keyword", "꼬몽이 엽서");
            AppKeyValueStore.addRecentSearchKeyword(requireContext(),"꼬몽이 엽서");
            navController.navigate(R.id.action_dest_search_to_dest_prod_list, bundle);
        });
        binding.btnRecoPhoto.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            bundle.putString("keyword", "포토 엽서");
            AppKeyValueStore.addRecentSearchKeyword(requireContext(),"포토 엽서");
            navController.navigate(R.id.action_dest_search_to_dest_prod_list, bundle);
        });
        binding.btnRecoTeanager.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            bundle.putString("keyword", "하이틴 엽서");
            navController.navigate(R.id.action_dest_search_to_dest_prod_list, bundle);
        });
    }
    public void initKeywordRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerSearchLog.setLayoutManager(layoutManager);

        // 어댑터 생성
        SearchKeywordAdapter searchKeywordAdapter = new SearchKeywordAdapter();

        // AppKeyValueStore에서 목록 받기
        List<String> recentSearchKeywords = AppKeyValueStore.getRecentSearchKeywords(requireContext());
        Log.i(TAG, "initKeywordRecyclerView: " + recentSearchKeywords.toString());
        if(recentSearchKeywords != null){
            //최근 검색어가 없을 때 UI 변경
            if(recentSearchKeywords.size() == 0){
                binding.txtNothing.setVisibility(View.VISIBLE);
                binding.txtLogEdit.setVisibility(View.GONE);
                binding.txtLogDeleteAll.setVisibility(View.GONE);
                binding.line.setVisibility(View.GONE);
                binding.txtLogEditClose.setVisibility(View.GONE);
            }else{
                binding.txtNothing.setVisibility(View.GONE);
            }
            // 어댑터 데이터 생성하기
            searchKeywordAdapter.setList(recentSearchKeywords);
            // RecyclerView에 어댑터 세팅
            binding.recyclerSearchLog.setAdapter(searchKeywordAdapter);
            // RecyclerView를 보이도록 설정
            binding.recyclerSearchLog.setVisibility(View.VISIBLE);
        }else{
            Log.i(TAG, "initKeywordRecyclerView: 실행 나 업셔");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // AppBar 숨기기
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
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