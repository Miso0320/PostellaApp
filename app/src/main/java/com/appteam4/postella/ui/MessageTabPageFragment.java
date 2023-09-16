package com.appteam4.postella.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentMessageTabPageBinding;


public class MessageTabPageFragment extends Fragment {
    private static final String TAG = "MessageTabPageFragment";
    private FragmentMessageTabPageBinding binding;
    private NavController navController;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMessageTabPageBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);

        //이미지 클릭이벤트 초기화
        initImgClick();
        return binding.getRoot();
    }

    //클릭한 이미지에 해당하는 브랜드를 번들에 담아 상품목록프레그먼트로 이동
    public void initImgClick(){
        Bundle bundle = new Bundle();
        binding.linearMessageCele.setOnClickListener(v->{
            bundle.putString("message", "CEL");
            navController.navigate(R.id.action_dest_category_to_dest_prod_list, bundle);
        });
        binding.linearMessageLove.setOnClickListener(v->{
            bundle.putString("message", "LOV");
            navController.navigate(R.id.action_dest_category_to_dest_prod_list, bundle);
        });
        binding.linearMessageThank.setOnClickListener(v->{
            bundle.putString("message", "THA");
            navController.navigate(R.id.action_dest_category_to_dest_prod_list, bundle);
        });
        binding.linearMessageHealing.setOnClickListener(v->{
            bundle.putString("message", "HEA");
            navController.navigate(R.id.action_dest_category_to_dest_prod_list, bundle);
        });
        binding.linearMessageTake.setOnClickListener(v->{
            bundle.putString("message", "APO");
            navController.navigate(R.id.action_dest_category_to_dest_prod_list, bundle);
        });
    }
}