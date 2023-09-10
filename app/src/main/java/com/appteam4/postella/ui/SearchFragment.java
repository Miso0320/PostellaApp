package com.appteam4.postella.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentSearchBinding;

public class SearchFragment extends Fragment {

    private static final String TAG = "SearchFragment";
    private FragmentSearchBinding binding;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);

        // AppBar 숨기기
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        // 검색어 입력 부분을 가장 위에 배치
        View searchView = binding.textInputLayout.getRootView();
        ViewGroup parent = (ViewGroup) searchView.getParent();
        if (parent != null) {
            parent.removeView(searchView);
            parent.addView(searchView, 0);
        }

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // AppBar 표시
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }
}