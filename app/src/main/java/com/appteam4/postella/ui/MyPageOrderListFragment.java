package com.appteam4.postella.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentMyOrderListBinding;
import com.appteam4.postella.dto.MyPageOrderList;
import com.appteam4.postella.dto.Product;
import com.appteam4.postella.service.MyPageOrderListService;
import com.appteam4.postella.service.ServiceProvider;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPageOrderListFragment extends Fragment {
    private static final String TAG = "MyPageOrderListFragment";
    private FragmentMyOrderListBinding binding;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = FragmentMyOrderListBinding.inflate(inflater);
       navController = NavHostFragment.findNavController(this);

       initRecyclerViewMyOrderList();

       return binding.getRoot();
    }

    private void initRecyclerViewMyOrderList() {
        // RecycleView 에서 항목을 수직으로 배치
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false
        );
        binding.recyclerViewMyOrderList.setLayoutManager(linearLayoutManager);

        // adpter 생성
        MyPageOrderListAdapter myPageOrderListAdapter = new MyPageOrderListAdapter();

        // api 서버에서JSON 목록 받기
        MyPageOrderListService myPageOrderListService = ServiceProvider.getMyPageOrderList(getContext());
        Call<List<MyPageOrderList>> call = myPageOrderListService.getMyPageOrderList();
        call.enqueue(new Callback<List<MyPageOrderList>>() {
            @Override
            public void onResponse(Call<List<MyPageOrderList>> call, Response<List<MyPageOrderList>> response) {
                // json -> List<Product>
                List<MyPageOrderList> orderList = response.body();
                if (orderList != null) {
                    // 어댑터 데이터 생성하기
                    myPageOrderListAdapter.setList(orderList);
                    // RecyclerView에 어댑터 세팅
                    binding.recyclerViewMyOrderList.setAdapter(myPageOrderListAdapter);
                    // RecyclerView를 보이도록 설정
                    binding.recyclerViewMyOrderList.setVisibility(View.VISIBLE);
                } else {
                    Log.i(TAG, "onResponse: 리스트가 널이여~");
                }
                // RecyclerView에 어댑터 세팅
                // 어댑터에 데이터가 설정된 후 notifyDataSetChanged() 호출
                binding.recyclerViewMyOrderList.setAdapter(myPageOrderListAdapter);
            }

            @Override
            public void onFailure(Call<List<MyPageOrderList>> call, Throwable t) {
                Log.i(TAG, "MyPageOrderList API 호출 실패");
            }
        });

        myPageOrderListAdapter.setOnItemClickListener(new MyPageOrderListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                MyPageOrderList myPageOrderList = myPageOrderListAdapter.getItem(position);
                Log.i(TAG, myPageOrderList.toString());

                Bundle args = new Bundle();
                args.putSerializable("myPageOrderList", myPageOrderList);
                navController.navigate(R.id.action_dest_mypage_order_list_to_dest_prod_detail);
            }
        });
    }
}