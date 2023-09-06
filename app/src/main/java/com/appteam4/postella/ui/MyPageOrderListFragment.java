package com.appteam4.postella.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentMyOrderListBinding;

public class MyPageOrderListFragment extends Fragment {
    private static final String TAG = "MyPageOrderListFragment";
    private FragmentMyOrderListBinding binding;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = FragmentMyOrderListBinding.inflate(inflater);
       navController = NavHostFragment.findNavController(this);

       initBtnGoBack();
       initBtnProdDtail();

       return binding.getRoot();
    }

    private void initBtnGoBack(){
        binding.btnGoBack.setOnClickListener(v->{
            navController.popBackStack();
        });
    }

    private void initBtnProdDtail(){
        binding.btnProdDetail.setOnClickListener(v->{
            navController.navigate(R.id.action_dest_mypage_order_list_to_dest_prod_detail);
        });
    }
}