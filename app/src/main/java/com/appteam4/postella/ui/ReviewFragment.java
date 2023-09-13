package com.appteam4.postella.ui;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentReviewBinding;
import com.appteam4.postella.dto.Review;

public class ReviewFragment extends Fragment {
    private static final String TAG = "ReviewFragment";
    private FragmentReviewBinding binding;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReviewBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);

        // RecyclerView 초기화
        initRecyclerView();

        // 탭 메뉴 이동
        initTabPage();

        return binding.getRoot();
    }

    private void initRecyclerView() {
        // RecyclerView에서 항목을 수직으로 배치하도록 설정
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false
        );
        binding.reviewListView.setLayoutManager(linearLayoutManager);

        // 구분선 추가하기
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                binding.reviewListView.getContext(),
                linearLayoutManager.getOrientation()
        );

        binding.reviewListView.addItemDecoration(dividerItemDecoration);

        // 어댑터 생성
        ReviewAdapter reviewAdapter = new ReviewAdapter();

        for (int i = 1; i <= 5; i++) {
            Review review = new Review();
            review.setRevNo(i);
            review.setUsName("유저" + i);
            review.setPrdName("상품" + i);
            review.setRevContent("엽서가 넘 귀엽고 예뻐서 다음에 또 사고싶어요!");
            review.setRevDate("2023. 09. 10");
            review.setPrdNo(i);
            review.setOdNo(i);
            review.setOdDetailNo(i);
            review.setRating(i);

            reviewAdapter.addReview(review);
        }

        binding.reviewListView.setAdapter(reviewAdapter);
    }

    private void initTabPage() {
        binding.tabProductDetail.setOnClickListener(v -> {
            navController.navigate(R.id.action_dest_review_to_dest_prod_detail);
        });

        binding.tabProductInquiries.setOnClickListener(v -> {
            navController.navigate(R.id.action_dest_review_to_dest_inquiry);
        });
    }

}