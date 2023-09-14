package com.appteam4.postella.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentCategoryBinding;
import com.appteam4.postella.databinding.FragmentCategoryTabPageBinding;
import com.appteam4.postella.databinding.FragmentPageBinding;

public class CategoryTabPageFragment extends Fragment {
    private static final String TAG = "CategoryTabPageFragment";
    private FragmentCategoryTabPageBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryTabPageBinding.inflate(inflater);

        return binding.getRoot();
    }
}