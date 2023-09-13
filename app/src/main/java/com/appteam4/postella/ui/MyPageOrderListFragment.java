package com.appteam4.postella.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
       //initClickProfile();
       initBtnImageSelect();

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

    /*private void initClickProfile() {
        binding.profileImg.setOnClickListener(v -> {

            Log.i(TAG, "클릭됨");

            if (ContextCompat.checkSelfPermission(getContext(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "네비갤러리 실행");
                navigateGallery();
            } else {
                *//*ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setTitle("권한 없음")
                        .setMessage("갤러리 접근에 실패하였습니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();*//*
                if (ContextCompat.checkSelfPermission(
                        getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "true 접근");
                    requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
                }

                requestStoragePermission();
            }
        });
    }

    private void navigateGallery() {
        Log.i(TAG, "네비갤러리 함수 실행");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        //startActivityForResult(intent, 2000);
        startActivity(intent);
    }

    private void requestStoragePermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            // 사용자에게 권한 필요 이유 설명
            new AlertDialog.Builder(getContext())
                    .setTitle("권한 필요")
                    .setMessage("프로필 이미지를 바꾸기 위해 갤러리 접근 권한이 필요합니다.")
                    .setPositiveButton("동의하기", (dialog, which) -> {
                        // 권한 요청
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
                    })
                    .setNegativeButton("취소하기", (dialog, which) -> {
                        // 사용자가 권한 요청을 취소한 경우의 동작을 설정
                    })
                    .create()
                    .show();
        } else {
            // 권한 요청을 처음 시도하는 경우
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
        }
    }*/

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