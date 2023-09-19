package com.appteam4.postella.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentMyOrderListBinding;
import com.appteam4.postella.datastore.AppKeyValueStore;
import com.appteam4.postella.dto.MyPageOrderList;
import com.appteam4.postella.service.MyPageOrderListService;
import com.appteam4.postella.service.ServiceProvider;

import java.util.List;

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

        initMenu();
        initRecyclerViewMyOrderList();
        //initClickProfile();
        initBtnLogout();
        initBtnImageSelect();

       return binding.getRoot();
    }

    private void initMenu() {
        MenuProvider menuProvider = new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.mypage_settings_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.dest_settings) {
                    return true;
                }
                return false;
            }
        };
        getActivity().addMenuProvider(menuProvider, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    private void initRecyclerViewMyOrderList() {
        /*// RecycleView 에서 항목을 수직으로 배치
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false
        );
        binding.recyclerViewMyOrderList.setLayoutManager(linearLayoutManager);

        // adpter 생성
        MyPageOrderListAdapter myPageOrderListAdapter = new MyPageOrderListAdapter();

        for (int i = 1; i <= 10; i++) {
            MyPageOrderList myPageOrderList = new MyPageOrderList();
            myPageOrderList.setOrderListNo(i);
            myPageOrderList.setOrderListTitle("상품" + i);
            myPageOrderList.setOrderListPrice(1000 + i);
            myPageOrderList.setOrderListStatus("배송중");

            myPageOrderListAdapter.addMyPageOrderList(myPageOrderList);
        }

        binding.recyclerViewMyOrderList.setAdapter(myPageOrderListAdapter);*/

        // RecyclerView에서 항목을 배치하도록 설정
        //GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        //binding.recyclerMainView.setLayoutManager(layoutManager);

        MyPageOrderListAdapter orderListAdapter = new MyPageOrderListAdapter();

        // API 서버에서 목록 받기
        MyPageOrderListService myPageOrderListService = ServiceProvider.getMyPageOrderList(getContext());

        // 공유데이터에서 us_no가져오기
        int us_no = Integer.parseInt(AppKeyValueStore.getValue(getContext(), "us_no"));

        Call<List<MyPageOrderList>> call = myPageOrderListService.getMyPageOrderList(us_no);
        call.enqueue(new Callback<List<MyPageOrderList>>() {
            @Override
            public void onResponse(Call<List<MyPageOrderList>> call, Response<List<MyPageOrderList>> response) {
                // json -> List<Product>
                List<MyPageOrderList> list = response.body();
                Log.i(TAG, "list: " + list);
                if (list != null) {
                    // 어댑터 데이터 생성하기
                    orderListAdapter.setList(list);
                    // RecyclerView에 어댑터 세팅
                    binding.recyclerViewMyOrderList.setAdapter(orderListAdapter);
                    /*// RecyclerView를 보이도록 설정
                    binding.recyclerViewMyOrderList.setVisibility(View.VISIBLE);*/
                    Log.i(TAG, "onResponse: 리스트가 있긴함");
                } else {
                    Log.i(TAG, "onResponse: 리스트가 널이여~");
                }
                // RecyclerView에 어댑터 세팅
                // 어댑터에 데이터가 설정된 후 notifyDataSetChanged() 호출
                //binding.recyclerViewMyOrderList.setAdapter(orderListAdapter);
            }

            @Override
            public void onFailure(Call<List<MyPageOrderList>> call, Throwable t) {
                Log.e(TAG, "API 호출 실패", t);
            }
        });
        // 항목 클릭시 콜백 메소드 등록
        orderListAdapter.setOnItemClickListener(new MyPageOrderListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                MyPageOrderList orderList = orderListAdapter.getItem(position);
                Log.i(TAG, orderList.toString());

                Bundle args = new Bundle();
                args.putSerializable("orderList", orderList);
                navController.navigate(R.id.action_dest_mypage_order_list_to_dest_prod_detail);
            }
        });

        // RecyclerView에서 항목을 배치하도록 설정
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.recyclerViewMyOrderList.setLayoutManager(linearLayoutManager);
    }

    private void initBtnLogout() {
        binding.btnLogout.setOnClickListener(v -> {
            AppKeyValueStore.remove(getContext(), "us_email");
            AppKeyValueStore.remove(getContext(), "us_password");
            navController.navigate(R.id.dest_main);
        });
    }

    private void initBtnImageSelect() {
        ActivityResultLauncher<PickVisualMediaRequest> activityResultLauncher =
                registerForActivityResult(
                        new ActivityResultContracts.PickVisualMedia(),
                        uri -> {
                            if (uri != null) {
                                binding.profileImg.setImageURI(uri);
                                binding.profileImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            }
                        }
                );

        binding.profileImg.setOnClickListener(v -> {
            PickVisualMediaRequest request = new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build();
            activityResultLauncher.launch(request);
        });
    }
}