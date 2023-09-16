package com.appteam4.postella.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentCategoryTabPageBinding;

public class CategoryTabPageFragment extends Fragment {
    private static final String TAG = "CategoryTabPageFragment";
    private FragmentCategoryTabPageBinding binding;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryTabPageBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);
        initImgClick();
        return binding.getRoot();
    }

    //클릭한 이미지에 해당하는 카테고리를 번들에 담아 상품목록프레그먼트로 이동
    public void initImgClick(){
        Bundle bundle = new Bundle();
        binding.linearCatePhoto.setOnClickListener(v->{
            bundle.putString("prd_category", "PHO");
            navController.navigate(R.id.action_dest_category_to_dest_prod_list, bundle);
        });
        binding.linearCatePhoto2.setOnClickListener(v->{
            bundle.putString("prd_category", "PHO");
            navController.navigate(R.id.action_dest_category_to_dest_prod_list, bundle);
        });
        binding.linearCateDesign.setOnClickListener(v->{
            bundle.putString("prd_category", "DES");
            navController.navigate(R.id.action_dest_category_to_dest_prod_list, bundle);
        });
        binding.linearCateDesign2.setOnClickListener(v->{
            bundle.putString("prd_category", "DES");
            navController.navigate(R.id.action_dest_category_to_dest_prod_list, bundle);
        });
        binding.linearCateIllust.setOnClickListener(v->{
            bundle.putString("prd_category", "ILU");
            navController.navigate(R.id.action_dest_category_to_dest_prod_list, bundle);
        });
        binding.linearCateIllust2.setOnClickListener(v->{
            bundle.putString("prd_category", "ILU");
            navController.navigate(R.id.action_dest_category_to_dest_prod_list, bundle);
        });
        binding.linearCateCalli.setOnClickListener(v->{
            bundle.putString("prd_category", "CAL");
            navController.navigate(R.id.action_dest_category_to_dest_prod_list, bundle);
        });
        binding.linearCateCalli2.setOnClickListener(v->{
            bundle.putString("prd_category", "CAL");
            navController.navigate(R.id.action_dest_category_to_dest_prod_list, bundle);
        });
    }
}