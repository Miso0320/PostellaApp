package com.appteam4.postella.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentWishListBinding;
import com.appteam4.postella.datastore.AppKeyValueStore;
import com.appteam4.postella.dto.MyWish;
import com.appteam4.postella.dto.WishResult;
import com.appteam4.postella.service.ServiceProvider;
import com.appteam4.postella.service.WishService;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishListFragment extends Fragment {

    private static final String TAG = "WishListFragment";
    private FragmentWishListBinding binding;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWishListBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);

        // 앱바 설정
        initMenu();

        // RecyclerView 초기화
        initRecyclerView();

        // 상세가기(임시버튼)
        initBtnProdDetail();

        return binding.getRoot();
    }

    /**
     * 앱바 설정
     */
    private void initMenu() {
        MenuProvider menuProvider = new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.nav_menu_top, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.dest_search) {
                    navController.navigate(R.id.dest_search, null);
                    return true;
                } else if (menuItem.getItemId() == R.id.dest_cart) {
                    navController.navigate(R.id.dest_cart, null);
                    return true;
                }
                return false;
            }
        };
        getActivity().addMenuProvider(menuProvider, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    private void initRecyclerView() {
        // RecyclerView에서 항목을 수직으로 배치하도록 설정
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false
        );
        binding.wishListView.setLayoutManager(linearLayoutManager);

        // 구분선 추가하기
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                binding.wishListView.getContext(),
                linearLayoutManager.getOrientation()
        );

        binding.wishListView.addItemDecoration(dividerItemDecoration);

        // 어댑터 생성
        WishAdapter wishAdapter = new WishAdapter();

        WishService wishService = ServiceProvider.getWishService(getContext());

        // 로그인 여부 확인
        String exist = AppKeyValueStore.getValue(getContext(), "us_no");
        // 로그인 되어 있는 경우
        if (exist != null) {
            // 유저식별번호 받아오기
            int us_no = Integer.parseInt(AppKeyValueStore.getValue(getContext(), "us_no"));

            // 찜목록 받아오기
            Call<List<MyWish>> wishCall = wishService.getWishListForApp(us_no);

            wishCall.enqueue(new Callback<List<MyWish>>() {
                @Override
                public void onResponse(Call<List<MyWish>> call, Response<List<MyWish>> response) {
                    List<MyWish> list = response.body();

                    if (list != null) {
                        // 어댑터 데이터 세팅
                        wishAdapter.setWishList(list);

                        // RecyclerView에 어댑터 세팅
                        binding.wishListView.setAdapter(wishAdapter);
                    }
                }

                @Override
                public void onFailure(Call<List<MyWish>> call, Throwable t) {
                    t.printStackTrace();
                }
            });

            // 찜목록 전체 개수 받아오기
            Call<Integer> cntCall = wishService.getWishCntForApp(us_no);

            cntCall.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    int wishCnt = response.body();
                    binding.wishTotal.setText(String.valueOf(wishCnt));
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    t.printStackTrace();
                }
            });

            // 이미지 선택 시 상품상세로 이동
            wishAdapter.setOnItemClickListener(new WishAdapter.OnItemClickListener() {
                @Override
                public void onPgImgClick(View itemView, int position) {
                    MyWish myWish = wishAdapter.getItem(position);
                    Bundle args = new Bundle();
                    args.putInt("pg_no", myWish.getPg_no());

                    navController.navigate(R.id.dest_prod_detail, args);
                }

                @Override
                public void onDeleteBtnClick(View itemView, int position) {
                    MyWish myWish = wishAdapter.getItem(position);
                    Call<WishResult> deleteCall = wishService.removeWish(myWish);
                    deleteCall.enqueue(new Callback<WishResult>() {
                        @Override
                        public void onResponse(Call<WishResult> call, Response<WishResult> response) {
                            wishAdapter.removeItem(position);
                            wishAdapter.notifyDataSetChanged();

                            Snackbar snackbar = Snackbar.make(getView(), "상품을 장바구니에서 삭제했어요!", Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }

                        @Override
                        public void onFailure(Call<WishResult> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }

                @Override
                public void onCartBtnClick(View itemView, int position) {
                    MyWish myWish = wishAdapter.getItem(position);
                    Call<Void> addCartCall = wishService.addCartForApp(myWish.getPrd_no(), us_no);
                    addCartCall.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Snackbar snackbar = Snackbar.make(getView(), "상품을 장바구니에 담았어요!", Snackbar.LENGTH_SHORT);
                            snackbar.setAction("바로가기", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // 바로가기 클릭 시 찜목록 프래그먼트로 이동
                                    NavController navController = NavHostFragment.findNavController(WishListFragment.this);
                                    navController.navigate(R.id.dest_cart);
                                }
                            });
                            snackbar.show();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
            });
        }
    }

    private void initBtnProdDetail() {
        binding.wishQty.setOnClickListener(v -> {
            navController.navigate(R.id.action_dest_wish_list_to_dest_prod_detail);
        });
    }
}