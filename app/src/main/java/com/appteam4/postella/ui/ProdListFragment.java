package com.appteam4.postella.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentProdListBinding;
import com.appteam4.postella.dto.Product;
import com.appteam4.postella.service.NetworkInfo;
import com.appteam4.postella.service.ProductGroupService;
import com.appteam4.postella.service.ServiceProvider;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProdListFragment extends Fragment {

    private static final String TAG = "ProdListFragment";
    private FragmentProdListBinding binding;
    private NavController navController;
    private int selectedFilterBtn = R.id.navigation_view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProdListBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);
        // AppBar 다시 보이게 하기
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        //스피너 초기화
        initSpinner();
        //필터 버튼 초기화
        initBtnFilter();
        initRecyclerView();

        return binding.getRoot();
    }

    private void initSpinner(){
        //상품 정렬 스피너
        Spinner orderKindSpinner = binding.spinnerOrderKind;
        ArrayAdapter<CharSequence> orderKindAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.spinner_order_kind, android.R.layout.simple_spinner_item);
        orderKindAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderKindSpinner.setAdapter(orderKindAdapter);

        // Spinner 선택 이벤트 처리
        orderKindSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 선택된 정렬 방식에 따라 작업 수행
                String selectedOrderKind = (String) parent.getItemAtPosition(position);
                // 예: 선택된 정렬 방식을 로그에 출력
                Log.d("Selected Order Kind", selectedOrderKind);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 아무것도 선택되지 않았을 때 수행할 작업
            }
        });
    }

    //카테고리, 브랜드, 메시지 필터링 버튼
    private void initBtnFilter(){
        //검색어 가져오기
        String searchKeyword = getArguments().getString("keyword");
        //검색어를 번들에 넣어 필터프레그먼트에 전달
        Bundle bundle = new Bundle();
        bundle.putString("keyword", searchKeyword);
        binding.btnOrderCategory.setOnClickListener(v->{
            navController.navigate(R.id.action_dest_prod_list_to_dest_filter, bundle);
        });

        binding.btnOrderBrand.setOnClickListener(v->{
            Log.i(TAG, "initBtnFilter: " + selectedFilterBtn);
            navController.navigate(R.id.action_dest_prod_list_to_dest_filter, bundle);
        });

        binding.btnOrderMessage.setOnClickListener(v->{
            navController.navigate(R.id.action_dest_prod_list_to_dest_filter, bundle);
        });
    }

    private void initRecyclerView(){
        // RecyclerView에서 항목을 배치하도록 설정
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        binding.recyclerProdList.setLayoutManager(layoutManager);
        // 어댑터 생성
        MainAdapter mainAdapter = new MainAdapter();

        // 번들에서 검색어를 가져옴
        String searchKeyword = getArguments().getString("keyword");
        // 필터에서 선택한 항목을 가져옴
        String prd_category = getArguments().getString("prd_category");
        String brand = getArguments().getString("brand");
        String message = getArguments().getString("message");
        // API 서버에서 목록 받기
        ProductGroupService productGroupService = ServiceProvider.getFilteringProducts(getContext());
        Call<List<Product>> call = productGroupService.getFilteringProducts(searchKeyword, prd_category, brand, message);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                // json -> List<Product>
                List<Product> list = response.body();
                if (list != null) {
                    // 어댑터 데이터 생성하기
                    mainAdapter.setList(list);
                    // RecyclerView에 어댑터 세팅
                    binding.recyclerProdList.setAdapter(mainAdapter);
                    // RecyclerView를 보이도록 설정
                    binding.recyclerProdList.setVisibility(View.VISIBLE);
                } else {
                    Log.i(TAG, "onResponse: 리스트가 널이여~");
                }
                // RecyclerView에 어댑터 세팅
                // 어댑터에 데이터가 설정된 후 notifyDataSetChanged() 호출
                binding.recyclerProdList.setAdapter(mainAdapter);
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
                //클릭한 항목의 Product를 상품상세프레그먼트에 전달하고 이동
                navController.navigate(R.id.action_dest_prod_list_to_dest_prod_detail, args);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}